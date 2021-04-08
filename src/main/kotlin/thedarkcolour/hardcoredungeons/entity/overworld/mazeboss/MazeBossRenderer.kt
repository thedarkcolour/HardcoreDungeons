package thedarkcolour.hardcoredungeons.entity.overworld.mazeboss

import net.minecraft.client.renderer.entity.BipedRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.model.SkeletonModel
import net.minecraft.util.ResourceLocation
import thedarkcolour.hardcoredungeons.util.modLoc

class MazeBossRenderer(manager: EntityRendererManager) : BipedRenderer<MazeBossEntity, SkeletonModel<MazeBossEntity>>(manager, SkeletonModel(), 0.4f) {
    override fun getEntityTexture(entity: MazeBossEntity): ResourceLocation {
        return SKELETON_TEXTURES
    }

    companion object {
        private val SKELETON_TEXTURES = ResourceLocation("textures/entity/skeleton/skeleton.png")
        private val MAZE_BOSS_TEXTURE = modLoc("textures/entity/maze_boss/")
    }
}