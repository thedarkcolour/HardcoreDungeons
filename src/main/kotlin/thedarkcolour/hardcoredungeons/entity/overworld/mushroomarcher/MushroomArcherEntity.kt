package thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher

import net.minecraft.entity.EntityType
import net.minecraft.entity.monster.AbstractSkeletonEntity
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.world.World

class MushroomArcherEntity(type: EntityType<out AbstractSkeletonEntity>, worldIn: World) : AbstractSkeletonEntity(type, worldIn) {
    override fun getStepSound(): SoundEvent {
        return SoundEvents.ENTITY_STRAY_STEP
    }
}