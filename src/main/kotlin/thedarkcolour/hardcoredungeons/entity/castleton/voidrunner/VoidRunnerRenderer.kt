package thedarkcolour.hardcoredungeons.entity.castleton.voidrunner

import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.util.ResourceLocation
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.entity.castleton.FullbrightLayer

class VoidRunnerRenderer(manager: EntityRendererManager) : MobRenderer<VoidRunnerEntity, VoidRunnerModel>(manager, VoidRunnerModel(), 0.4f) {
    init {
        addLayer(FullbrightLayer(this, ResourceLocation(HardcoreDungeons.ID, "textures/entity/void_runner/void_runner_overlay.png")))
    }

    override fun getEntityTexture(entity: VoidRunnerEntity) = TEXTURE

    companion object {
        private val TEXTURE = ResourceLocation(HardcoreDungeons.ID, "textures/entity/void_runner/void_runner.png")
    }
}