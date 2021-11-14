package thedarkcolour.hardcoredungeons.entity.overworld.mazeboss

import com.google.common.base.Preconditions
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.AttributeMap
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.monster.RangedAttackMob
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraftforge.common.ForgeMod
import net.minecraftforge.common.util.Constants
import thedarkcolour.hardcoredungeons.registry.HEntities

class MazeBossEntity(type: EntityType<MazeBossEntity>, LevelIn: Level) : Monster(type, LevelIn), RangedAttackMob {
    private var timeSinceLastEarthquake = 0
    private var isAgro = true // agro by default unless in a maze

    private var spawnLocation: BlockPos? = null
    private var mazeCenter: BlockPos? = null
    private var bounds: AABB? = null

    constructor(LevelIn: Level) : this(HEntities.MAZE_BOSS, LevelIn)

    override fun registerGoals() {
        goalSelector.addGoal(0, WalkToCenterGoal())
        //goalSelector.addGoal(1, MoveGoal())
    }

    // Make the model stuff work cause i'm lazy
    override fun performRangedAttack(target: LivingEntity, distanceFactor: Float) {}

    fun setSpawnLocation(pos: BlockPos, bounds: AABB, direction: Direction) {
        Preconditions.checkArgument(pos !is BlockPos.Mutable)

        this.spawnLocation = pos
        this.mazeCenter = pos.relative(direction, 11).relative(Direction.DOWN)
        this.bounds = bounds
        this.isAgro = false
    }

    override fun readAdditionalSaveData(compound: CompoundNBT) {
        super.readAdditionalSaveData(compound)

        if (compound.contains("SpawnLocation", Constants.NBT.TAG_COMPOUND)) {
            spawnLocation = NBTUtil.readBlockPos(compound.getCompound("SpawnLocation"))
        }

        timeSinceLastEarthquake = compound.getInt("TimeSinceLastEarthquake").coerceIn(0..200)
    }

    override fun addAdditionalSaveData(compound: CompoundNBT) {
        super.addAdditionalSaveData(compound)

        val spawnLocation = spawnLocation

        if (spawnLocation != null) {
            compound.put("SpawnLocation", NBTUtil.writeBlockPos(spawnLocation))
        }

        compound.putInt("TimeSinceLastEarthquake", timeSinceLastEarthquake.coerceIn(0..200))
    }

    override fun aiStep() {
        super.aiStep()

        // only attack if boss is agro
        if (isAgro) {
            // attack bounds
            val bounds = bounds ?: boundingBox.inflate(7.0, 3.0, 7.0)

            if (!level.isClientSide) {
                // earthquake every 10 seconds
                if (timeSinceLastEarthquake++ >= 200) {
                    timeSinceLastEarthquake = 0

                    earthquake()
                    // blindness 1.5s every 5 seconds
                } else if (timeSinceLastEarthquake % 100 == 0) {
                    val players = level.getEntitiesOfClass(PlayerEntity::class.java, bounds)

                    for (player in players) {
                        player.addEffect(EffectInstance(Effects.BLINDNESS, 50, 1, false, false))
                    }
                }
            }
        }
    }

    // shatter shield cool downs and fling the player a little bit
    private fun earthquake() {
        val bounds = bounds ?: boundingBox.inflate(7.0, 3.0, 7.0)

        val players = level.getEntitiesOfClass(PlayerEntity::class.java, bounds, EntityPredicates.NO_SPECTATORS)

        for (player in players) {
            player.deltaMovement = player.deltaMovement.add(0.0, 1.4, 0.0)
            player.hurtMarked = true

            for (armorItem in player.armorSlots) {
                if (!armorItem.isEmpty) {
                    armorItem.hurt(1, level.random, player as ServerPlayerEntity)
                    doHurtTarget(player)
                }
            }
        }
    }

    override fun canRide(entityIn: Entity): Boolean {
        return false
    }

    // Begin the fight by walking to the center of the maze
    private inner class WalkToCenterGoal : Goal() {
        override fun canUse(): Boolean {
            return !isAgro && mazeCenter != null
        }

        override fun start() {
            val pos = mazeCenter!!
            navigation.moveTo(pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, 0.5)
        }

        override fun canContinueToUse(): Boolean {
            return !navigation.isDone
        }

        override fun stop() {
            if (position() != mazeCenter) {
                isAgro = true
            }
        }
    }

    companion object {
        val ATTRIBUTES: AttributeSupplier.Builder = AttributeSupplier.builder()
            .add(Attributes.MAX_HEALTH, 20.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
            .add(Attributes.MOVEMENT_SPEED)
            .add(Attributes.ARMOR)
            .add(Attributes.ARMOR_TOUGHNESS)
            .add(ForgeMod.SWIM_SPEED.get())
            .add(ForgeMod.NAMETAG_DISTANCE.get())
            .add(ForgeMod.ENTITY_GRAVITY.get())
            .add(Attributes.FOLLOW_RANGE, 16.0)
            .add(Attributes.ATTACK_KNOCKBACK, 2.0)
            .add(Attributes.ATTACK_DAMAGE, 2.0)
    }
}