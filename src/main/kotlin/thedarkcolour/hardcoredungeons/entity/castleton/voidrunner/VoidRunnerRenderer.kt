package thedarkcolour.hardcoredungeons.entity.castleton.voidrunner

import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.FullbrightLayer
import thedarkcolour.hardcoredungeons.util.modLoc

class VoidRunnerRenderer(manager: EntityRendererManager) : MobRenderer<VoidRunnerEntity, VoidRunnerModel>(manager, VoidRunnerModel(), 0.4f) {
    init {
        addLayer(FullbrightLayer(this, modLoc("textures/entity/void_runner/void_runner_overlay.png")))
    }

    override fun getEntityTexture(entity: VoidRunnerEntity) = TEXTURE

    companion object {
        private val TEXTURE = modLoc("textures/entity/void_runner/void_runner.png")
    }
}