package thedarkcolour.hardcoredungeons.entity.castleton.voidrunner

import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.util.DamageSource
import net.minecraft.util.SoundEvent
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.registry.HSounds

class VoidRunnerEntity(type: EntityType<VoidRunnerEntity>, worldIn: World) : CreatureEntity(type, worldIn) {
    override fun getHurtSound(source: DamageSource): SoundEvent {
        return HSounds.ENTITY_VOID_RUNNER_HIT
    }

    class ChargeGoal(private val entity: VoidRunnerEntity) : Goal() {
        override fun canUse(): Boolean {
            TODO("not implemented")
        }
    }

    companion object {
        val ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.builder()
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
}