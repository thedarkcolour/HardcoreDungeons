package thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher

import net.minecraft.entity.EntityType
import net.minecraft.entity.monster.AbstractSkeletonEntity
import net.minecraft.entity.projectile.AbstractArrowEntity
import net.minecraft.entity.projectile.ArrowEntity
import net.minecraft.item.ItemStack
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HEntities

class MushroomArcherEntity(type: EntityType<out AbstractSkeletonEntity> = HEntities.MUSHROOM_ARCHER, worldIn: World) : AbstractSkeletonEntity(type, worldIn) {
    override fun getStepSound(): SoundEvent {
        return SoundEvents.ENTITY_STRAY_STEP
    }

    override fun fireArrow(arrowStack: ItemStack, distanceFactor: Float): AbstractArrowEntity {
        val arrow = super.fireArrow(arrowStack, distanceFactor)

        if (arrow is ArrowEntity) {
            arrow.addEffect(EffectInstance(Effects.POISON, 240))
        }

        return arrow
    }
}