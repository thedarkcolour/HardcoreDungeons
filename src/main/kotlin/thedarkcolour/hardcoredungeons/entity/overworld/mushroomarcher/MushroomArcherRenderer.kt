package thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher

import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.SkeletonRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.monster.AbstractSkeleton
import thedarkcolour.hardcoredungeons.util.modLoc

class MushroomArcherRenderer(ctx: EntityRendererProvider.Context) : SkeletonRenderer(ctx) {
    override fun getTextureLocation(entity: AbstractSkeleton): ResourceLocation {
        return MUSHROOM_ARCHER_TEXTURES
    }

    companion object {
        private val MUSHROOM_ARCHER_TEXTURES = modLoc("textures/entity/mushroom_archer.png")
    }
}