package thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul

import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.ai.goal.LookAtGoal
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HSounds

class FrayedSoulEntity(type: EntityType<FrayedSoulEntity>, worldIn: World) : CreatureEntity(type, worldIn) {
    override fun registerGoals() {
        goalSelector.addGoal(0, LookAtGoal(this, PlayerEntity::class.java, 40f, 1f))
        goalSelector.addGoal(0, FindBlueLumshroomGoal(this))
        goalSelector.addGoal(2, WanderGoal(this))
    }

    override fun registerAttributes() {
        super.registerAttributes()
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = 0.24
    }

    override fun getAmbientSound(): SoundEvent {
        return HSounds.ENTITY_FRAYED_SOUL_IDLE
    }

    class FindBlueLumshroomGoal(private val entity: FrayedSoulEntity) : Goal() {
        override fun shouldExecute(): Boolean {
            return entity.world.getBlockState(entity.position).block != HBlocks.BLUE_LUMSHROOM
                    && entity.positionVec.x != 0.5 && entity.positionVec.z != 0.5
        }

        override fun startExecuting() {
            if (entity.ticksExisted % 60 == 0) {
                val destination = entity.findBlockInRange(5) { pos, world ->
                    world.getBlockState(pos).block == HBlocks.BLUE_LUMSHROOM
                }

                if (destination != null) {
                    entity.navigator.tryMoveToXYZ(destination.x + 0.5, destination.y.toDouble(), destination.z + 0.5, 0.4)
                }
            }
        }

        private fun FrayedSoulEntity.findBlockInRange(range: Int, predicate: (BlockPos, World) -> Boolean): BlockPos? {
            val pos = position.add(-range, -range, -range)

            for (x in 0 until (range shl 1) + 1) {
                for (y in 0 until (range shl 1) + 1) {
                    for (z in 0 until (range shl 1) + 1) {
                        if (predicate(pos.add(x, y, z), world)) {
                            return pos.add(x, y, z)
                        }
                    }
                }
            }
            return null
        }
    }

    class WanderGoal(entity: FrayedSoulEntity) : WaterAvoidingRandomWalkingGoal(entity, 0.5) {
        override fun shouldExecute(): Boolean {
            return super.shouldExecute() && creature.world.getBlockState(creature.position).block != HBlocks.BLUE_LUMSHROOM
        }

        override fun shouldContinueExecuting(): Boolean {
            return super.shouldContinueExecuting() && creature.world.getBlockState(creature.position).block != HBlocks.BLUE_LUMSHROOM
        }
    }
}