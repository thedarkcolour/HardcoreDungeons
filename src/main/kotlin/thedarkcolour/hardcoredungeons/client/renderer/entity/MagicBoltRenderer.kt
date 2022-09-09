package thedarkcolour.hardcoredungeons.client.renderer.entity

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import thedarkcolour.hardcoredungeons.client.model.entity.MagicBoltModel
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.util.modLoc

class MagicBoltRenderer(ctx: EntityRendererProvider.Context) : EntityRenderer<MagicBoltEntity>(ctx) {
    val model = MagicBoltModel()

    override fun getTextureLocation(entity: MagicBoltEntity): ResourceLocation {
        return TEXTURE
    }

    override fun render(
        entity: MagicBoltEntity,
        entityYaw: Float,
        partialTicks: Float,
        stack: PoseStack,
        buffer: MultiBufferSource,
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