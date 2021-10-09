package thedarkcolour.hardcoredungeons.client.model.entity

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.util.toRadians

class FrayedSoulModel : EntityModel<FrayedSoulEntity>() {
    private val frayedSoulHead: ModelRenderer
    //private val ninetyDegrees = toRadians()

    init {
        texHeight = 32
        texWidth = 32

        frayedSoulHead = ModelRenderer(this)
        frayedSoulHead.setPos(0.0f, 24.0f, 0.0f)
        frayedSoulHead.addBox(-3.55f, -25.0f, -4.0f, 8.0f, 6.0f, 8.0f, 0.0f, false)
        frayedSoulHead.texOffs(0, 14)
        frayedSoulHead.addBox(-3.55f, -19.0f, -2.0f, 6.0f, 2.0f, 4.0f, 0.0f, false)
    }

    override fun renderToBuffer(stack: MatrixStack, builder: IVertexBuilder, packedLight: Int, overlay: Int, r: Float, g: Float, b: Float, a: Float) {
        frayedSoulHead.render(stack, builder, packedLight, overlay, r, g, b, a)
    }

    override fun setupAnim(
        entityIn: FrayedSoulEntity, limbSwing: Float, limbSwingAmount: Float,
        ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {
        frayedSoulHead.setPos(0.0f, 24.0f, 0.0f)
        frayedSoulHead.zRot = toRadians(headPitch)// + ninetyDegrees
        frayedSoulHead.yRot = toRadians(netHeadYaw - 90.0f)// + ninetyDegrees
    }
}