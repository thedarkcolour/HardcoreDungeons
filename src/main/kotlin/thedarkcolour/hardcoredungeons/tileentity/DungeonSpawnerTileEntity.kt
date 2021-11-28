package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.ResourceLocationException
import net.minecraft.world.level.block.Blocks
import net.minecraft.entity.*
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.*
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.core.BlockPos
import net.minecraft.util.random.WeightedRandom
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.SpawnData
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import thedarkcolour.hardcoredungeons.registry.HBlockEntities

class DungeonSpawnerTileEntity(tileEntityTypeIn: BlockEntityType<*> = HBlockEntities.DUNGEON_SPAWNER) : BlockEntity(tileEntityTypeIn) {
    private val potentialSpawns = ArrayList<SpawnData>()
    var remainingKills = 0
    var spawnDelay = 20
    var minSpawnDelay = 200
    var maxSpawnDelay = 800
    var spawnCount = 4
    var cachedEntity: Entity? = null
    var maxNearbyEntities = 6
    var activatingRangeFromPlayer = 16
    var spawnRange = 4
    var spawnData = SpawnData()

    override fun load(nbt: CompoundTag) {
        super.load(nbt)
        spawnDelay = nbt.getShort("Delay").toInt()
        potentialSpawns.clear()
        if (nbt.contains("SpawnPotentials", 9)) {
            val list = nbt.getList("SpawnPotentials", 10)

            for (i in list.indices) {
                potentialSpawns.add(SpawnData(list.getCompound(i)))
            }
        }

        if (nbt.contains("SpawnData", 10)) {
            setNextSpawnData(SpawnData(1, nbt.getCompound("SpawnData")))
        } else if (potentialSpawns.isNotEmpty()) {
            setNextSpawnData(WeightedRandom.getRandomItem(level!!.random, potentialSpawns))
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

        if (level != null) {
            cachedEntity = null
        }
    }

    override fun save(compound: CompoundTag): CompoundTag {
        getEntityId() ?: super.save(compound)

        compound.putShort("Delay", spawnDelay.toShort())
        compound.putShort("MinSpawnDelay", minSpawnDelay.toShort())
        compound.putShort("MaxSpawnDelay", maxSpawnDelay.toShort())
        compound.putShort("SpawnCount", spawnCount.toShort())
        compound.putShort("MaxNearbyEntities", maxNearbyEntities.toShort())
        compound.putShort("RequiredPlayerRange", activatingRangeFromPlayer.toShort())
        compound.putShort("SpawnRange", spawnRange.toShort())
        compound.put("SpawnData", spawnData.tag.copy())

        val nbtList = ListTag()

        if (potentialSpawns.isEmpty()) {
            nbtList.add(spawnData.save())
        } else {
            for (spawn in potentialSpawns) {
                nbtList.add(spawn.save())
            }
        }

        compound.put("SpawnPotentials", nbtList)
        return super.save(compound)
    }

    private fun getEntityId(): ResourceLocation? {
        val s = spawnData.tag.getString("id")

        return try {
            if (s.isNullOrEmpty()) null else ResourceLocation(s)
        } catch (loc: ResourceLocationException) {
            null
        }
    }

    val isActive: Boolean get() {
        return level!!.hasNearbyAlivePlayer(blockPos.x + 0.5, blockPos.y + 0.5, blockPos.z + 0.5, activatingRangeFromPlayer.toDouble())
    }

    // todo move into ticker
    override fun tick() {
        if (isActive) {
            val level = level!!

            if (level !is ServerLevel) {
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
                    val nbt = spawnData.tag
                    val optional = EntityType.by(nbt)

                    // If we have nothing to spawn reset
                    if (!optional.isPresent) {
                        resetTimer()
                        return
                    }

                    // Handle a fixed mob spawn
                    val positions = nbt.getList("Pos", 6)
                    val pos = blockPos
                    val rand = level.random
                    val j = positions.size

                    val posX = if (j >= 1) positions.getDouble(0)  else pos.x + (rand.nextDouble() - rand.nextDouble()) * spawnRange + 0.5
                    val posY = if (j >= 2) positions.getDouble(1)  else pos.y - rand.nextInt(3) - 1.0
                    val posZ = if (j >= 3) positions.getDouble(2)  else pos.z + (rand.nextDouble() - rand.nextDouble()) * spawnRange + 0.5

                    if (level.noCollision(optional.get().getAABB(posX, posY, posZ))) {
                        if (EntitySpawnPlacementRegistry.checkSpawnRules(optional.get(), level, SpawnReason.SPAWNER, BlockPos(posX, posY, posZ), rand)) {
                            val entity = EntityType.loadEntityRecursive(nbt, level) { e ->
                                e.absMoveTo(posX, posY, posZ, e.yRot, e.xRot)
                                e
                            }
                            if (entity == null) {
                                resetTimer()
                                return
                            }

                            // get number of entities already spawned
                            // and if it exceeds the max spawning limit
                            // cancel the next spawning round
                            val k = level.getEntitiesOfClass(entity.javaClass, AxisAlignedBB(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), pos.x + 1.0, pos.y + 1.0, pos.z + 1.0).inflate(spawnRange.toDouble())).size
                            if (k >= maxNearbyEntities) {
                                resetTimer()
                                return
                            }

                            entity.absMoveTo(entity.x, entity.y, entity.z, rand.nextFloat() * 360.0f, 0.0f)
                            if (entity is MobEntity) {
                                if (spawnData.tag.size() == 1 && spawnData.tag.contains("id", 8)) {
                                    entity.finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.SPAWNER, null, null)
                                }
                            }

                            // Attempt to add entities
                            if (!level.tryAddFreshEntityWithPassengers(entity)) {
                                resetTimer()
                                return
                            }

                            // Alert clients of a mob spawn
                            level.levelEvent(2004, pos, 0)

                            // Spawn particles
                            if (entity is MobEntity) {
                                entity.spawnAnim()
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
            minSpawnDelay + level!!.random.nextInt(i)
        }

        if (potentialSpawns.isNotEmpty()) {
            this.setNextSpawnData(WeightedRandom.getRandomItem(level!!.random, potentialSpawns))
        }

        broadcastEvent(1)
    }

    private fun setNextSpawnData(nextSpawnData: SpawnData?) {
        spawnData = nextSpawnData!!

        if (level != null) {
            val state = level!!.getBlockState(blockPos)
            level!!.sendBlockUpdated(blockPos, state, state, 4)
        }
    }

    override fun getUpdatePacket(): SUpdateTileEntityPacket {
        return SUpdateTileEntityPacket(blockPos, 1, updateTag)
    }

    override fun getUpdateTag(): CompoundTag {
        val nbt = save(CompoundTag())
        nbt.remove("SpawnPotentials")
        return nbt
    }

    override fun onDataPacket(net: NetworkManager, pkt: SUpdateTileEntityPacket) {
        load(level!!.getBlockState(pkt.pos), pkt.tag)
    }

    fun broadcastEvent(id: Int) {
        level!!.blockEvent(blockPos, Blocks.SPAWNER, id, 0)
    }

    override fun triggerEvent(id: Int, type: Int): Boolean {
        return setSpawnDelayToMin(id) || super.triggerEvent(id, type)
    }

    private fun setSpawnDelayToMin(delay: Int): Boolean {
        val successful = delay == 1 && level!!.isClientSide

        if (successful) {
            spawnDelay = minSpawnDelay
        }

        return successful
    }

    override fun onlyOpCanSetNbt(): Boolean = true
}