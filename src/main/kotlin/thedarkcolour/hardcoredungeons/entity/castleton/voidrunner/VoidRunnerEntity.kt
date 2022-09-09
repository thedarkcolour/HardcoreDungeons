package thedarkcolour.hardcoredungeons.entity.castleton.voidrunner

import net.minecraft.world.entity.ambient.AmbientCreature
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.registry.HSounds

/*
class VoidRunnerEntity(type: EntityType<VoidRunnerEntity>, worldIn: World) : AmbientCreature(type, worldIn) {
    override fun getHurtSound(source: DamageSource): SoundEvent {
        return HSounds.ENTITY_VOID_RUNNER_HIT
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, SwimGoal(this))
        goalSelector.addGoal(3, LeapAtTargetGoal(this, 0.4f))
        goalSelector.addGoal(4, MeleeAttackGoal(this, 0.7, false))
        goalSelector.addGoal(5, WaterAvoidingRandomWalkingGoal(this, 0.8))
        goalSelector.addGoal(6, LookAtGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.addGoal(6, LookRandomlyGoal(this))
        targetSelector.addGoal(1, HurtByTargetGoal(this))
        targetSelector.addGoal(2, NearestAttackableTargetGoal(this, PlayerEntity::class.java, true))
        targetSelector.addGoal(3, NearestAttackableTargetGoal(this, IronGolemEntity::class.java, true))
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
}*/