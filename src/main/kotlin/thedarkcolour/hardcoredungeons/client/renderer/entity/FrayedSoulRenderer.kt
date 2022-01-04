package thedarkcolour.hardcoredungeons.client.renderer.entity

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.entity.EntityRendererManager
import thedarkcolour.hardcoredungeons.client.model.entity.FrayedSoulModel
import thedarkcolour.hardcoredungeons.client.renderer.entity.layers.FullbrightLayer
import thedarkcolour.hardcoredungeons.entity.ReloadableRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.util.modLoc
import kotlin.math.sin

class FrayedSoulRenderer(manager: EntityRendererManager) : ReloadableRenderer<FrayedSoulEntity, FrayedSoulModel>(manager, ::FrayedSoulModel, 0.4f) {
    init {
        addLayer(FullbrightLayer(this, modLoc("textures/entity/frayed_soul/frayed_soul_eyes.png")))
    }

    override fun getTextureLocation(entity: FrayedSoulEntity) = TEXTURE

    override fun setupRotations(entity: FrayedSoulEntity, stack: MatrixStack, ageInTicks: Float, rotationYaw: Float, partialTicks: Float) {
        super.setupRotations(entity, stack, ageInTicks, rotationYaw, partialTicks)
        stack.translate(0.0, sin((entity.tickCount.toDouble() + partialTicks) / 12) / 6, 0.0)
    }

    companion object {
        private val TEXTURE = modLoc("textures/entity/frayed_soul/frayed_soul.png")
    }
}