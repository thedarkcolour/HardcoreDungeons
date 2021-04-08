package thedarkcolour.hardcoredungeons.client.renderer.entity.layers

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.entity.layers.LayerRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import thedarkcolour.hardcoredungeons.client.model.entity.DoeModel
import thedarkcolour.hardcoredungeons.client.renderer.entity.DeerRenderer
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity

/**
 * Fullbright entity renderer that renders a different texture based on the type of deer.
 *
 * @author TheDarkColour
 */
class DeerFullbrightLayer(private val renderer: DeerRenderer) : LayerRenderer<DeerEntity, DoeModel>(renderer) {
    override fun render(
        stack: MatrixStack, buffer: IRenderTypeBuffer, light: Int, entity: DeerEntity, limbSwing: Float,
        limbSwingAmount: Float, partialTicks: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {
        val texture = renderer.getOverlayTexture(entity)

        if (texture != null) {
            entityModel.render(stack, buffer.getBuffer(texture), 15728640, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)
        }
    }
}