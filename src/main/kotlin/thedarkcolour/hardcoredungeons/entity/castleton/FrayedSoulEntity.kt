package thedarkcolour.hardcoredungeons.entity.castleton

import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.behavior.BehaviorUtils
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HSounds
import thedarkcolour.hardcoredungeons.registry.block.HBlocks

class FrayedSoulEntity(type: EntityType<FrayedSoulEntity>, level: Level) : PathfinderMob(type, level) {
    override fun registerGoals() {
        goalSelector.addGoal(0, FindBlueLumshroomGoal(this))
        goalSelector.addGoal(1, LookAtPlayerGoal(this, Player::class.java, 40f, 1f))
        goalSelector.addGoal(2, WanderGoal(this))
        targetSelector.addGoal(1, HurtByTargetGoal(this))
        targetSelector.addGoal(3, ShootGoal(this))
    }

    override fun getAmbientSound(): SoundEvent {
        return HSounds.ENTITY_FRAYED_SOUL_IDLE
    }

    override fun canBeLeashed(player: Player): Boolean {
        return false
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
            .add(Attributes.ATTACK_KNOCKBACK)
    }

    class FindBlueLumshroomGoal(private val entity: FrayedSoulEntity) : Goal() {
        override fun canUse(): Boolean {
            return entity.level.getBlockState(entity.blockPosition()).block != HBlocks.BLUE_LUMSHROOM.plant
        }

        override fun start() {
            val destination = entity.findBlockInRange(5) { pos, world ->
                world.getBlockState(pos).block == HBlocks.BLUE_LUMSHROOM.plant
            }

            if (destination != null) {
                entity.navigation.moveTo(destination.x + 0.5, destination.y.toDouble(), destination.z + 0.5, 0.4)
            }
        }

        // Note: three for loops is faster than BlockPos.betweenClosed
        private inline fun FrayedSoulEntity.findBlockInRange(range: Int, predicate: (BlockPos, Level) -> Boolean): BlockPos? {
            val pos = blockPosition().offset(-range, -range, -range)

            for (x in 0 until (range shl 1) + 1) {
                for (y in 0 until (range shl 1) + 1) {
                    for (z in 0 until (range shl 1) + 1) {
                        if (predicate(pos.offset(x, y, z), level)) {
                            return pos.offset(x, y, z)
                        }
                    }
                }
            }

            return null
        }
    }

    class ShootGoal(private val entity: FrayedSoulEntity) : Goal() {
        private var seeTime = 0
        //private var cooldown = 100

        override fun canUse(): Boolean {
            return entity.target != null
        }

        override fun canContinueToUse(): Boolean {
            return canUse() /*|| !entity.navigation.isDone*/
        }

        override fun start() {
            entity.isAggressive = true
        }

        override fun stop() {
            entity.isAggressive = false
            seeTime = 0
        }

        override fun tick() {
            val target = entity.target

            if (target != null) {
                //val distance = entity.distanceToSqr(target)
                val canSee = BehaviorUtils.canSee(entity, target)
                val hasSeen = seeTime > 0

                if (hasSeen != canSee) {
                    seeTime = 0
                }

                if (canSee) {
                    ++seeTime
                } else {
                    --seeTime
                }

                /*if (!(distance > radiusSqr) && seeTime >= 20) {
                    // normally the mob would strafe here
                    entity.navigation.stop()
                } else {
                    entity.navigation.moveTo(target, followSpeed)
                    // stop strafing
                }*/

                // strafing stuff

                if (entity.tickCount % 20 == 0) {
                    val vec = entity.getViewVector(1.0f)
                    val magic = MagicBoltEntity(HEntities.MAGIC_BOLT, entity.level)
                    magic.shoot(entity, entity.x, entity.eyeY - 0.1, entity.z, vec.x, vec.y, vec.z)
                }
            }
        }
    }

    class WanderGoal(entity: FrayedSoulEntity) : WaterAvoidingRandomStrollGoal(entity, 0.5) {
        override fun canUse(): Boolean {
            return super.canUse() && mob.level.getBlockState(mob.blockPosition()).block != HBlocks.BLUE_LUMSHROOM.plant
        }

        override fun canContinueToUse(): Boolean {
            return super.canContinueToUse() && mob.level.getBlockState(mob.blockPosition()).block != HBlocks.BLUE_LUMSHROOM.plant
        }
    }
}