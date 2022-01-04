package thedarkcolour.hardcoredungeons.client.renderer.entity

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.util.ResourceLocation
import thedarkcolour.hardcoredungeons.client.model.entity.MagicBoltModel
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.util.modLoc

class MagicBoltRenderer(manager: EntityRendererManager) : EntityRenderer<MagicBoltEntity>(manager) {
    val model = MagicBoltModel()

    override fun getTextureLocation(entity: MagicBoltEntity): ResourceLocation {
        return TEXTURE
    }

    override fun render(
        entity: MagicBoltEntity,
        entityYaw: Float,
        partialTicks: Float,
        stack: MatrixStack,
        buffer: IRenderTypeBuffer,
        light: Int
    ) {
        stack.pushPose()

        model.renderToBuffer(stack, buffer.getBuffer(model.renderType(getTextureLocation(entity))), light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)

        stack.popPose()
    }

    companion object {
        private val TEXTURE = modLoc("textures/entity/magic_bolt.png")
    }
}