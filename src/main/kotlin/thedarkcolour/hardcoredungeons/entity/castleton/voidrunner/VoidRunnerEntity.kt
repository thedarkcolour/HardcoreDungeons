package thedarkcolour.hardcoredungeons.entity.castleton.voidrunner

import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.util.DamageSource
import net.minecraft.util.SoundEvent
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HSounds

class VoidRunnerEntity(type: EntityType<VoidRunnerEntity>, worldIn: World) : CreatureEntity(type, worldIn) {
    override fun getHurtSound(source: DamageSource): SoundEvent {
        return HSounds.ENTITY_VOID_RUNNER_HIT
    }

    class ChargeGoal(private val entity: VoidRunnerEntity) : Goal() {
        override fun shouldExecute(): Boolean {
            TODO("not implemented")
        }
    }
}