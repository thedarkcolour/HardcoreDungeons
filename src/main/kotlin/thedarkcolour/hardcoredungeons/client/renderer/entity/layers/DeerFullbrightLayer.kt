package thedarkcolour.hardcoredungeons.client.renderer.entity.layers

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.OverlayTexture
import thedarkcolour.hardcoredungeons.client.model.entity.DeerModel
import thedarkcolour.hardcoredungeons.client.renderer.entity.DeerRenderer
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity

/**
 * Fullbright entity renderer that renders a different texture based on the type of deer.
 *
 * @author thedarkcolour
 */
class DeerFullbrightLayer(private val renderer: DeerRenderer) : RenderLayer<DeerEntity, DeerModel>(renderer) {
    override fun render(
        stack: PoseStack, buffer: MultiBufferSource, light: Int, entity: DeerEntity, limbSwing: Float,
        limbSwingAmount: Float, partialTicks: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {
        val texture = renderer.getOverlayTexture(entity)

        if (texture != null) {
            parentModel.renderToBuffer(stack, buffer.getBuffer(texture), 15728640, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)
        }
    }
}