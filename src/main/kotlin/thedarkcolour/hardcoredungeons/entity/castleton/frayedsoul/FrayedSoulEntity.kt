package thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul

import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HSounds

class FrayedSoulEntity(type: EntityType<FrayedSoulEntity>, worldIn: Level) : PathfinderMob(type, worldIn) {
    override fun registerGoals() {
        goalSelector.addGoal(0, LookAtPlayerGoal(this, Player::class.java, 40f, 1f))
        goalSelector.addGoal(0, FindBlueLumshroomGoal(this))
        goalSelector.addGoal(2, WanderGoal(this))
        goalSelector.addGoal(3, ShootGoal(this))
    }

    // todo GlobalEntityTypeAttributes.put
    /*override fun registerAttributes() {
        super.registerAttributes()
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = 0.24
    }*/

    override fun defineSynchedData() {
        super.defineSynchedData()
    }

    override fun getAmbientSound(): SoundEvent {
        return HSounds.ENTITY_FRAYED_SOUL_IDLE
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
                    && entity.position().x != 0.5 && entity.position().z != 0.5
        }

        override fun start() {
            if (entity.tickCount % 60 == 0) {
                val destination = entity.findBlockInRange(5) { pos, world ->
                    world.getBlockState(pos).block == HBlocks.BLUE_LUMSHROOM.plant
                }

                if (destination != null) {
                    entity.navigation.moveTo(destination.x + 0.5, destination.y.toDouble(), destination.z + 0.5, 0.4)
                }
            }
        }

        private inline fun FrayedSoulEntity.findBlockInRange(range: Int, predicate: (BlockPos, Level) -> Boolean): BlockPos? {
            val pos = blockPosition()

            for (cursor in BlockPos.betweenClosed(pos.x - range, pos.y - range, pos.z - range, pos.x + range, pos.y + range, pos.z + range)) {
                if (predicate(cursor, level)) {
                    return cursor.immutable()
                }
            }
            return null
        }
    }

    class ShootGoal(private val entity: FrayedSoulEntity) : Goal() {
        private var seeTime = 0
        private var cooldown = 100

        override fun canUse(): Boolean {
            return entity.target != null
        }

        override fun canContinueToUse(): Boolean {
            return canUse() || !entity.navigation.isDone
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
                val distance = entity.distanceToSqr(target)
                val canSee = entity.sensing.hasLineOfSight(target)
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
                    //entity.shoot(target)
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