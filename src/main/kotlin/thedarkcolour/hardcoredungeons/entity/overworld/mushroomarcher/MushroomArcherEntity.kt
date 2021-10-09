package thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher

import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.monster.AbstractSkeletonEntity
import net.minecraft.entity.projectile.AbstractArrowEntity
import net.minecraft.entity.projectile.ArrowEntity
import net.minecraft.item.ItemStack
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.world.World
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.registry.HEntities

class MushroomArcherEntity(type: EntityType<out AbstractSkeletonEntity> = HEntities.MUSHROOM_ARCHER, worldIn: World) : AbstractSkeletonEntity(type, worldIn) {
    override fun getStepSound(): SoundEvent {
        return SoundEvents.STRAY_STEP
    }

    override fun getArrow(arrowStack: ItemStack, distanceFactor: Float): AbstractArrowEntity {
        val arrow = super.getArrow(arrowStack, distanceFactor)

        if (arrow is ArrowEntity) {
            arrow.addEffect(EffectInstance(Effects.POISON, 240))
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