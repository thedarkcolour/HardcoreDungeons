package thedarkcolour.hardcoredungeons.client.model.entity

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.util.toRadians

class FrayedSoulModel : EntityModel<FrayedSoulEntity>() {
    private val frayedSoulHead: ModelRenderer

    init {
        texHeight = 32
        texWidth = 32

        frayedSoulHead = ModelRenderer(this)
        frayedSoulHead.setPos(0.0f, 20.0f, 0.0f)
        // Head
        frayedSoulHead.addBox(-4.0f, -4.0f, -4.0f, 8.0f, 6.0f, 8.0f, 0.0f, false)
        // Jaw
        frayedSoulHead.texOffs(0, 14)
        frayedSoulHead.addBox(-2.0f, 2.0f, -4.0f, 4.0f, 2.0f, 6.0f, 0.0f, false)
    }

    override fun renderToBuffer(stack: MatrixStack, builder: IVertexBuilder, packedLight: Int, overlay: Int, r: Float, g: Float, b: Float, a: Float) {
        frayedSoulHead.render(stack, builder, packedLight, overlay, r, g, b, a)
    }

    override fun setupAnim(
        entityIn: FrayedSoulEntity, limbSwing: Float, limbSwingAmount: Float,
        ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {
        frayedSoulHead.yRot = toRadians(netHeadYaw)
        frayedSoulHead.xRot = toRadians(headPitch)
    }
}