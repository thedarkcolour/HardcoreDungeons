package thedarkcolour.hardcoredungeons.client.model.entity

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.voidrunner.VoidRunnerEntity

class VoidRunnerModel : EntityModel<VoidRunnerEntity>() {
    private val head: ModelRenderer
    private val legLeft: ModelRenderer
    private val legRight: ModelRenderer

    init {
        texWidth = 32
        texHeight = 32

        head = ModelRenderer(this)
        head.setPos(-0.75f, 15.0f, 0.0f)
        head.addBox(-2.75f, -6.0f, -3.0f, 6.0f, 6.0f, 6.0f, 0.0F, false)
        head.setTexSize(24, 0)
        head.addBox(-0.75f, -3.0f, -5.0f, 2.0f, 2.0f, 2.0f, 0.0f, false)

        val featherMiddle = ModelRenderer(this)
        featherMiddle.setPos(0.25f, -6.0f, 3.0f)
        featherMiddle.setTexSize(5, 12)
        featherMiddle.xRot = -0.7854f
        featherMiddle.addBox(-0.5f, -4.3232f, -0.4697f, 1.0f, 5.0f, 1.0f, 0.0f, false)
        head.addChild(featherMiddle)

        val featherRight = ModelRenderer(this)
        featherRight.setPos(-1.25f, -6.5f, 3.5f)
        featherRight.setTexSize(5, 12)
        featherRight.xRot = -0.7845f
        featherRight.yRot = -0.5236f
        featherRight.addBox(-0.5f, -3.75f, -0.5f, 1.0f, 5.0f, 1.0f, 0.0f, false)
        head.addChild(featherRight)

        val featherLeft = ModelRenderer(this)
        featherLeft.setPos(1.75f, -6.5f, 3.5f)
        featherLeft.setTexSize(5, 12)
        featherLeft.xRot = -0.7845f
        featherLeft.yRot = 0.5236f
        featherLeft.addBox(-0.5f, -3.75f, -0.5f, 1.0f, 5.0f, 1.0f, 0.0f, false)
        head.addChild(featherLeft)

        legRight = ModelRenderer(this)
        legRight.setPos(-2.0f, 15.0f, 0.5f)
        legRight.setTexSize(0, 12)
        legRight.addBox(-0.5f, 0.0f, -0.5f, 1.0f, 8.0f, 1.0f, 0.0f, false)
        legRight.setTexSize(0, 21)
        legRight.addBox(-1.0f, 8.0f, -2.5f, 2.0f, 1.0f, 3.0f, 0.0f, false)

        legLeft = ModelRenderer(this)
        legLeft.setPos(1.0f, 15.0f, 0.5f)
        legLeft.setTexSize(0, 12)
        legLeft.addBox(-0.5f, 0.0f, -0.5f, 1.0f, 8.0f, 1.0f, 0.0f, true)
        legLeft.setTexSize(0, 21)
        legLeft.addBox(-1.0f, 8.0f, -2.5f, 2.0f, 1.0f, 3.0f, 0.0f, true)
    }

    override fun setupAnim(
        entityIn: VoidRunnerEntity, limbSwing: Float, limbSwingAmount: Float,
        ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {}

    override fun renderToBuffer(
        matrixStackIn: MatrixStack, bufferIn: IVertexBuilder, packedLightIn: Int,
        packedOverlayIn: Int, red: Float, green: Float, blue: Float, alpha: Float
    ) {
        head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
        legLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
        legRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
    }
}