package thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.FullbrightLayer
import thedarkcolour.hardcoredungeons.util.modLoc
import kotlin.math.sin

class FrayedSoulRenderer(manager: EntityRendererManager) : MobRenderer<FrayedSoulEntity, FrayedSoulModel>(manager, FrayedSoulModel(), 0.4f) {
    init {
        addLayer(FullbrightLayer(this, modLoc("textures/entity/frayed_soul/frayed_soul_eyes.png")))
    }

    override fun getEntityTexture(entity: FrayedSoulEntity) = TEXTURE

    override fun applyRotations(entity: FrayedSoulEntity, stack: MatrixStack, ageInTicks: Float, rotationYaw: Float, partialTicks: Float) {
        super.applyRotations(entity, stack, ageInTicks, rotationYaw, partialTicks)
        stack.translate(0.0, sin((entity.ticksExisted.toDouble() + partialTicks) / 12) / 6, 0.0)
    }

    companion object {
        private val TEXTURE = modLoc("textures/entity/frayed_soul/frayed_soul.png")
    }
}