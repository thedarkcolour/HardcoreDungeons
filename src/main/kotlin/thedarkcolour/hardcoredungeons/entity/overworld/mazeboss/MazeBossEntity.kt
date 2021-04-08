package thedarkcolour.hardcoredungeons.entity.overworld.mazeboss

import com.google.common.base.Preconditions
import net.minecraft.entity.*
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.NBTUtil
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.Direction
import net.minecraft.util.EntityPredicates
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.ForgeMod
import net.minecraftforge.common.util.Constants
import thedarkcolour.hardcoredungeons.registry.HEntities

class MazeBossEntity(type: EntityType<MazeBossEntity>, worldIn: World) : MobEntity(type, worldIn), IRangedAttackMob {
    private var timeSinceLastEarthquake = 0
    private var isAgro = true // agro by default unless in a maze

    private var spawnLocation: BlockPos? = null
    private var mazeCenter: BlockPos? = null
    private var bounds: AxisAlignedBB? = null

    constructor(worldIn: World) : this(HEntities.MAZE_BOSS, worldIn)

    override fun registerGoals() {
        goalSelector.addGoal(0, WalkToCenterGoal())
        //goalSelector.addGoal(1, MoveGoal())
    }

    // Make the model stuff work cause i'm lazy
    override fun attackEntityWithRangedAttack(target: LivingEntity, distanceFactor: Float) {}

    fun setSpawnLocation(pos: BlockPos, bounds: AxisAlignedBB, direction: Direction) {
        Preconditions.checkArgument(pos !is BlockPos.Mutable)

        this.spawnLocation = pos
        this.mazeCenter = pos.offset(direction, 11).offset(Direction.DOWN)
        this.bounds = bounds
        this.isAgro = false
    }

    override fun readAdditional(compound: CompoundNBT) {
        super.readAdditional(compound)

        if (compound.contains("SpawnLocation", Constants.NBT.TAG_COMPOUND)) {
            spawnLocation = NBTUtil.readBlockPos(compound.getCompound("SpawnLocation"))
        }

        timeSinceLastEarthquake = compound.getInt("TimeSinceLastEarthquake").coerceIn(0..200)
    }

    override fun writeAdditional(compound: CompoundNBT) {
        super.writeAdditional(compound)

        val spawnLocation = spawnLocation

        if (spawnLocation != null) {
            compound.put("SpawnLocation", NBTUtil.writeBlockPos(spawnLocation))
        }

        compound.putInt("TimeSinceLastEarthquake", timeSinceLastEarthquake.coerceIn(0..200))
    }

    override fun livingTick() {
        super.livingTick()

        // only attack if boss is agro
        if (isAgro) {
            // attack bounds
            val bounds = bounds ?: boundingBox.grow(7.0, 3.0, 7.0)

            if (!world.isRemote) {
                // earthquake every 10 seconds
                if (timeSinceLastEarthquake++ >= 200) {
                    timeSinceLastEarthquake = 0

                    earthquake()
                    // blindness 1.5s every 5 seconds
                } else if (timeSinceLastEarthquake % 100 == 0) {
                    val players = world.getEntitiesWithinAABB(PlayerEntity::class.java, bounds)

                    for (player in players) {
                        player.addPotionEffect(EffectInstance(Effects.BLINDNESS, 50, 1, false, false))
                    }
                }
            }
        }
    }

    // shatter shield cool downs and fling the player a little bit
    private fun earthquake() {
        val bounds = bounds ?: boundingBox.grow(7.0, 3.0, 7.0)

        val players = world.getEntitiesWithinAABB(PlayerEntity::class.java, bounds, EntityPredicates.NOT_SPECTATING)

        for (player in players) {
            player.motion = player.motion.add(0.0, 1.4, 0.0)
            player.velocityChanged = true

            for (armorItem in player.armorInventoryList) {
                if (!armorItem.isEmpty) {
                    armorItem.attemptDamageItem(1, world.rand, player as ServerPlayerEntity)
                    attackEntityAsMob(player)
                }
            }
        }
    }

    override fun canBeRidden(entityIn: Entity): Boolean {
        return false
    }

    // Begin the fight by walking to the center of the maze
    private inner class WalkToCenterGoal : Goal() {
        override fun shouldExecute(): Boolean {
            return !isAgro && mazeCenter != null
        }

        override fun startExecuting() {
            val pos = mazeCenter!!
            navigator.tryMoveToXYZ(pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, 0.5)
        }

        override fun shouldContinueExecuting(): Boolean {
            return !navigator.noPath()
        }

        override fun resetTask() {
            if (position != mazeCenter) {
                isAgro = true
            }
        }
    }

    companion object {
        val ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.createMutableAttribute()
            .createMutableAttribute(Attributes.MAX_HEALTH, 20.0)
            .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.2)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED)
            .createMutableAttribute(Attributes.ARMOR)
            .createMutableAttribute(Attributes.ARMOR_TOUGHNESS)
            .createMutableAttribute(ForgeMod.SWIM_SPEED.get())
            .createMutableAttribute(ForgeMod.NAMETAG_DISTANCE.get())
            .createMutableAttribute(ForgeMod.ENTITY_GRAVITY.get())
            .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0)
            .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 2.0)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0)
    }
}