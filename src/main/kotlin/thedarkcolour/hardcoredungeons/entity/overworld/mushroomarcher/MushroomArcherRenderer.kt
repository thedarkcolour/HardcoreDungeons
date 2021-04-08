package thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher

import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.SkeletonRenderer
import net.minecraft.entity.monster.AbstractSkeletonEntity
import net.minecraft.util.ResourceLocation
import thedarkcolour.hardcoredungeons.util.modLoc

class MushroomArcherRenderer(manager: EntityRendererManager) : SkeletonRenderer(manager) {
    override fun getEntityTexture(entity: AbstractSkeletonEntity): ResourceLocation {
        return MUSHROOM_ARCHER_TEXTURES
    }

    companion object {
        private val MUSHROOM_ARCHER_TEXTURES = modLoc("textures/entity/mushroom_archer")
    }
}