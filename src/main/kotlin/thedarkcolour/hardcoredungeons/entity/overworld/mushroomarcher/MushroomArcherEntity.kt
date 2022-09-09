package thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher

import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.monster.AbstractSkeletonEntity
import net.minecraft.entity.projectile.ArrowEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.monster.AbstractSkeleton
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.entity.projectile.Arrow
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.registry.HEntities

class MushroomArcherEntity(type: EntityType<out AbstractSkeleton> = HEntities.MUSHROOM_ARCHER, level: Level) : AbstractSkeleton(type, level) {
    override fun getStepSound(): SoundEvent {
        return SoundEvents.STRAY_STEP
    }

    override fun getArrow(arrowStack: ItemStack, distanceFactor: Float): AbstractArrow {
        val arrow = super.getArrow(arrowStack, distanceFactor)

        if (arrow is Arrow) {
            arrow.addEffect(MobEffectInstance(MobEffects.POISON, 240))
        }

        return arrow
    }

    companion object {
        val ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.builder()
            .add(Attributes.MAX_HEALTH, 20.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
            .add(Attributes.MOVEMENT_SPEED, 0.45)
            .add(Attributes.ARMOR)
            .add(Attributes.ARMOR_TOUGHNESS)
            .add(ForgeMod.SWIM_SPEED.get())
            .add(ForgeMod.NAMETAG_DISTANCE.get())
            .add(ForgeMod.ENTITY_GRAVITY.get())
            .add(Attributes.FOLLOW_RANGE, 16.0)
            .add(Attributes.ATTACK_KNOCKBACK)
    }
}