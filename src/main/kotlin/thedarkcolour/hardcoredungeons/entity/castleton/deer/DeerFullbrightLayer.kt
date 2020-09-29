package thedarkcolour.hardcoredungeons.entity.castleton.deer

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.entity.layers.LayerRenderer
import net.minecraft.client.renderer.texture.OverlayTexture

class DeerFullbrightLayer(private val renderer: DeerRenderer) : LayerRenderer<CastletonDeerEntity, DoeModel>(renderer) {
    override fun render(
        stack: MatrixStack, buffer: IRenderTypeBuffer, light: Int, entity: CastletonDeerEntity, limbSwing: Float,
        limbSwingAmount: Float, partialTicks: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {
        entityModel.render(stack, buffer.getBuffer(renderer.getOverlayTexture(entity)), 15728640, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)
    }
}