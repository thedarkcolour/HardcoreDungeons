package thedarkcolour.hardcoredungeons.entity.castleton

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.registry.HEntities

class KnightlyJuggernautEntity(type: EntityType<KnightlyJuggernautEntity>, level: Level) : Animal(type, level) {
    override fun getBreedOffspring(level: ServerLevel, ageable: AgeableMob): KnightlyJuggernautEntity {
        return HEntities.KNIGHTLY_JUGGERNAUT.create(level)!!
    }

    override fun registerGoals() {
        goalSelector.addGoal(0, WaterAvoidingRandomStrollGoal(this, 0.2))
        goalSelector.addGoal(2, LookAtPlayerGoal(this, Player::class.java, 8.0F))
        goalSelector.addGoal(3, RandomLookAroundGoal(this))

        targetSelector.addGoal(0, HurtByTargetGoal(this))
        targetSelector.addGoal(1, object : MeleeAttackGoal(this, 0.4, true) {
            override fun getAttackReachSqr(living: LivingEntity): Double {
                return 2.0f + super.getAttackReachSqr(living)
            }
        })
        targetSelector.addGoal(1, NearestAttackableTargetGoal(this, Player::class.java, true))
    }

    companion object {
        val ATTRIBUTES: AttributeSupplier.Builder = AttributeSupplier.builder()
            .add(Attributes.MAX_HEALTH, 80.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
            .add(Attributes.MOVEMENT_SPEED)
            .add(Attributes.ARMOR, 6.0)
            .add(Attributes.ARMOR_TOUGHNESS)
            .add(ForgeMod.SWIM_SPEED.get())
            .add(ForgeMod.NAMETAG_DISTANCE.get())
            .add(ForgeMod.ENTITY_GRAVITY.get())
            .add(Attributes.FOLLOW_RANGE, 16.0)
            .add(Attributes.ATTACK_DAMAGE, 3.0)
            .add(Attributes.ATTACK_KNOCKBACK)
    }
}