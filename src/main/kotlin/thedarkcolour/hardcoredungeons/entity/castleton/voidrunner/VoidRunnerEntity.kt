package thedarkcolour.hardcoredungeons.entity.castleton.voidrunner

import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.util.DamageSource
import net.minecraft.util.SoundEvent
import net.minecraft.world.World
import net.minecraftforge.common.ForgeMod
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
            .createMutableAttribute(Attributes.ATTACK_KNOCKBACK)
    }
}