package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.*
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.ListNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.*
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.server.ServerWorld
import thedarkcolour.hardcoredungeons.registry.HTileEntities

class DungeonSpawnerTileEntity(tileEntityTypeIn: TileEntityType<*> = HTileEntities.DUNGEON_SPAWNER) : TileEntity(tileEntityTypeIn), ITickableTileEntity {
    private val potentialSpawns = ArrayList<WeightedSpawnerEntity>()
    var remainingKills = 0
    var spawnDelay = 20
    var minSpawnDelay = 200
    var maxSpawnDelay = 800
    var spawnCount = 4
    var cachedEntity: Entity? = null
    var maxNearbyEntities = 6
    var activatingRangeFromPlayer = 16
    var spawnRange = 4
    var spawnData = WeightedSpawnerEntity()

    override fun read(state: BlockState, nbt: CompoundNBT) {
        super.read(state, nbt)
        spawnDelay = nbt.getShort("Delay").toInt()
        potentialSpawns.clear()
        if (nbt.contains("SpawnPotentials", 9)) {
            val list = nbt.getList("SpawnPotentials", 10)

            for (i in list.indices) {
                potentialSpawns.add(WeightedSpawnerEntity(list.getCompound(i)))
            }
        }

        if (nbt.contains("SpawnData", 10)) {
            setNextSpawnData(WeightedSpawnerEntity(1, nbt.getCompound("SpawnData")))
        } else if (potentialSpawns.isNotEmpty()) {
            setNextSpawnData(WeightedRandom.getRandomItem(world!!.rand, potentialSpawns))
        }

        if (nbt.contains("MinSpawnDelay", 99)) {
            minSpawnDelay = nbt.getShort("MinSpawnDelay").toInt()
            maxSpawnDelay = nbt.getShort("MaxSpawnDelay").toInt()
            spawnCount = nbt.getShort("SpawnCount").toInt()
        }

        if (nbt.contains("MaxNearbyEntities", 99)) {
            maxNearbyEntities = nbt.getShort("MaxNearbyEntities").toInt()
            activatingRangeFromPlayer = nbt.getShort("RequiredPlayerRange").toInt()
        }

        if (nbt.contains("SpawnRange", 99)) {
            spawnRange = nbt.getShort("SpawnRange").toInt()
        }

        if (getWorld() != null) {
            cachedEntity = null
        }
    }

    override fun write(compound: CompoundNBT): CompoundNBT {
        getEntityId() ?: super.write(compound)

        compound.putShort("Delay", spawnDelay.toShort())
        compound.putShort("MinSpawnDelay", minSpawnDelay.toShort())
        compound.putShort("MaxSpawnDelay", maxSpawnDelay.toShort())
        compound.putShort("SpawnCount", spawnCount.toShort())
        compound.putShort("MaxNearbyEntities", maxNearbyEntities.toShort())
        compound.putShort("RequiredPlayerRange", activatingRangeFromPlayer.toShort())
        compound.putShort("SpawnRange", spawnRange.toShort())
        compound.put("SpawnData", spawnData.nbt.copy())

        val nbtList = ListNBT()

        if (potentialSpawns.isEmpty()) {
            nbtList.add(spawnData.toCompoundTag())
        } else {
            for (spawn in potentialSpawns) {
                nbtList.add(spawn.toCompoundTag())
            }
        }

        compound.put("SpawnPotentials", nbtList)
        return super.write(compound)
    }

    private fun getEntityId(): ResourceLocation? {
        val s = spawnData.nbt.getString("id")

        return try {
            if (StringUtils.isNullOrEmpty(s)) null else ResourceLocation(s)
        } catch (loc: ResourceLocationException) {
            null
        }
    }

    val isActive: Boolean get() {
        return world!!.isPlayerWithin(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, activatingRangeFromPlayer.toDouble())
    }

    override fun tick() {
        if (isActive) {
            val world = world!!

            if (world !is ServerWorld) {
                if (spawnDelay > 0) {
                    --spawnDelay
                }
            } else {
                if (spawnDelay == -1) {
                    resetTimer()
                }
                if (spawnDelay > 0) {
                    --spawnDelay
                }

                // Whether the spawner actually spawned something
                var successful = false

                // Try to spawn the max number of mobs in a round
                for (i in 0 until spawnCount) {
                    // Read the mob to spawn
                    val nbt = spawnData.nbt
                    val optional = EntityType.readEntityType(nbt)

                    // If we have nothing to spawn reset
                    if (!optional.isPresent) {
                        resetTimer()
                        return
                    }

                    // Handle a fixed mob spawn
                    val positions = nbt.getList("Pos", 6)
                    val j = positions.size

                    val posX = if (j >= 1) positions.getDouble(0)  else pos.x + (world.rand.nextDouble() - world.rand.nextDouble()) * spawnRange + 0.5
                    val posY = if (j >= 2) positions.getDouble(1)  else pos.y - world.rand.nextInt(3) - 1.0
                    val posZ = if (j >= 3) positions.getDouble(2)  else pos.z + (world.rand.nextDouble() - world.rand.nextDouble()) * spawnRange + 0.5

                    if (world.hasNoCollisions(optional.get().getBoundingBoxWithSizeApplied(posX, posY, posZ))) {
                        if (EntitySpawnPlacementRegistry.canSpawnEntity(optional.get(), world, SpawnReason.SPAWNER, BlockPos(posX, posY, posZ), world.rand)) {
                            val entity = EntityType.loadEntityAndExecute(nbt, world) { e ->
                                e.setLocationAndAngles(posX, posY, posZ, e.rotationYaw, e.rotationPitch)
                                e
                            }
                            if (entity == null) {
                                resetTimer()
                                return
                            }

                            // get number of entities already spawned
                            // and if it exceeds the max spawning limit
                            // cancel the next spawning round
                            val k = world.getEntitiesWithinAABB(entity.javaClass, AxisAlignedBB(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), pos.x + 1.0, pos.y + 1.0, pos.z + 1.0).grow(spawnRange.toDouble())).size
                            if (k >= maxNearbyEntities) {
                                resetTimer()
                                return
                            }

                            entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, world.rand.nextFloat() * 360.0f, 0.0f)
                            if (entity is MobEntity) {
                                if (spawnData.nbt.size() == 1 && spawnData.nbt.contains("id", 8)) {
                                    entity.onInitialSpawn(world, world.getDifficultyForLocation(entity.position), SpawnReason.SPAWNER, null, null)
                                }
                            }

                            // Attempt to add entities
                            if (!world.func_242106_g(entity)) {
                                resetTimer()
                                return
                            }

                            // Alert clients of a mob spawn
                            world.playEvent(2004, pos, 0)

                            // Spawn particles
                            if (entity is MobEntity) {
                                entity.spawnExplosionParticle()
                            }

                            successful = true
                        }
                    }
                }

                if (successful) {
                    resetTimer()
                }
            }
        }
    }

    private fun resetTimer() {
        spawnDelay = if (maxSpawnDelay <= minSpawnDelay) {
            minSpawnDelay
        } else {
            val i = maxSpawnDelay - minSpawnDelay
            minSpawnDelay + getWorld()!!.rand.nextInt(i)
        }

        if (!potentialSpawns.isEmpty()) {
            this.setNextSpawnData(WeightedRandom.getRandomItem(getWorld()!!.rand, potentialSpawns))
        }

        broadcastEvent(1)
    }

    private fun setNextSpawnData(nextSpawnData: WeightedSpawnerEntity?) {
        spawnData = nextSpawnData!!

        if (world != null) {
            val state = world!!.getBlockState(pos)
            world!!.notifyBlockUpdate(pos, state, state, 4)
        }
    }

    override fun getUpdatePacket(): SUpdateTileEntityPacket {
        return SUpdateTileEntityPacket(pos, 1, updateTag)
    }

    override fun getUpdateTag(): CompoundNBT {
        val nbt = write(CompoundNBT())
        nbt.remove("SpawnPotentials")
        return nbt
    }

    override fun onDataPacket(net: NetworkManager, pkt: SUpdateTileEntityPacket) {
        read(world!!.getBlockState(pkt.pos), pkt.nbtCompound)
    }

    fun broadcastEvent(id: Int) {
        world!!.addBlockEvent(pos, Blocks.SPAWNER, id, 0)
    }

    override fun receiveClientEvent(id: Int, type: Int): Boolean {
        return setSpawnDelayToMin(id) || super.receiveClientEvent(id, type)
    }

    private fun setSpawnDelayToMin(delay: Int): Boolean {
        val successful = delay == 1 && world!!.isRemote

        if (successful) {
            spawnDelay = minSpawnDelay
        }

        return successful
    }

    override fun onlyOpsCanSetNbt(): Boolean = true
}