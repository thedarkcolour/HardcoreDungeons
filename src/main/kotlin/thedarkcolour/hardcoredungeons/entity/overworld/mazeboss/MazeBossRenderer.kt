package thedarkcolour.hardcoredungeons.entity.overworld.mazeboss

import net.minecraft.client.model.SkeletonModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.renderer.entity.*
import net.minecraft.client.renderer.entity.model.SkeletonModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.ResourceLocation
import thedarkcolour.hardcoredungeons.util.modLoc

class MazeBossRenderer(manager: EntityRendererProvider.Context) : HumanoidMobRenderer<MazeBossEntity, SkeletonModel<MazeBossEntity>>(manager, SkeletonModel(manager.bakeLayer(LAYER)), 0.4f) {
    override fun getTextureLocation(entity: MazeBossEntity): ResourceLocation {
        return SKELETON_TEXTURES
    }

    companion object {
        private val SKELETON_TEXTURES = ResourceLocation("textures/entity/skeleton/skeleton.png")
        private val MAZE_BOSS_TEXTURE = modLoc("textures/entity/maze_boss/")
        private val LAYER = ModelLayerLocation(, )
    }
}