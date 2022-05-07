package thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut

import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.registry.HEntities

class KnightlyJuggernautEntity(type: EntityType<KnightlyJuggernautEntity>, worldIn: World) : AnimalEntity(type, worldIn) {
    override fun getBreedOffspring(worldIn: ServerWorld, ageable: AgeableEntity): KnightlyJuggernautEntity {
        return HEntities.KNIGHTLY_JUGGERNAUT.create(level)!!
    }

    override fun registerGoals() {
        goalSelector.addGoal(0, WaterAvoidingRandomWalkingGoal(this, 0.2))
        goalSelector.addGoal(2, LookAtGoal(this, PlayerEntity::class.java, 8.0F))
        goalSelector.addGoal(3, LookRandomlyGoal(this))

        targetSelector.addGoal(1, object : MeleeAttackGoal(this, 0.4, true) {
            override fun getAttackReachSqr(living: LivingEntity): Double {
                return 2.0f + super.getAttackReachSqr(living)
            }
        })
        targetSelector.addGoal(0, NearestAttackableTargetGoal(this, PlayerEntity::class.java, true))

        targetSelector.addGoal(1, HurtByTargetGoal(this))
    }

    companion object {
        val ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.builder()
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