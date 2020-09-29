package thedarkcolour.hardcoredungeons.entity.castleton.voidrunner

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer

class VoidRunnerModel : EntityModel<VoidRunnerEntity>() {
    private val head: ModelRenderer
    private val legLeft: ModelRenderer
    private val legRight: ModelRenderer

    init {
        textureWidth = 32
        textureHeight = 32

        head = ModelRenderer(this)
        head.setRotationPoint(-0.75f, 15.0f, 0.0f)
        head.addBox(-2.75f, -6.0f, -3.0f, 6.0f, 6.0f, 6.0f, 0.0F, false)
        head.setTextureOffset(24, 0)
        head.addBox(-0.75f, -3.0f, -5.0f, 2.0f, 2.0f, 2.0f, 0.0f, false)

        val featherMiddle = ModelRenderer(this)
        featherMiddle.setRotationPoint(0.25f, -6.0f, 3.0f)
        featherMiddle.setTextureOffset(5, 12)
        featherMiddle.rotateAngleX = -0.7854f
        featherMiddle.addBox(-0.5f, -4.3232f, -0.4697f, 1.0f, 5.0f, 1.0f, 0.0f, false)
        head.addChild(featherMiddle)

        val featherRight = ModelRenderer(this)
        featherRight.setRotationPoint(-1.25f, -6.5f, 3.5f)
        featherRight.setTextureOffset(5, 12)
        featherRight.rotateAngleX = -0.7845f
        featherRight.rotateAngleY = -0.5236f
        featherRight.addBox(-0.5f, -3.75f, -0.5f, 1.0f, 5.0f, 1.0f, 0.0f, false)
        head.addChild(featherRight)

        val featherLeft = ModelRenderer(this)
        featherLeft.setRotationPoint(1.75f, -6.5f, 3.5f)
        featherLeft.setTextureOffset(5, 12)
        featherLeft.rotateAngleX = -0.7845f
        featherLeft.rotateAngleY = 0.5236f
        featherLeft.addBox(-0.5f, -3.75f, -0.5f, 1.0f, 5.0f, 1.0f, 0.0f, false)
        head.addChild(featherLeft)

        legRight = ModelRenderer(this)
        legRight.setRotationPoint(-2.0f, 15.0f, 0.5f)
        legRight.setTextureOffset(0, 12)
        legRight.addBox(-0.5f, 0.0f, -0.5f, 1.0f, 8.0f, 1.0f, 0.0f, false)
        legRight.setTextureOffset(0, 21)
        legRight.addBox(-1.0f, 8.0f, -2.5f, 2.0f, 1.0f, 3.0f, 0.0f, false)

        legLeft = ModelRenderer(this)
        legLeft.setRotationPoint(1.0f, 15.0f, 0.5f)
        legLeft.setTextureOffset(0, 12)
        legLeft.addBox(-0.5f, 0.0f, -0.5f, 1.0f, 8.0f, 1.0f, 0.0f, true)
        legLeft.setTextureOffset(0, 21)
        legLeft.addBox(-1.0f, 8.0f, -2.5f, 2.0f, 1.0f, 3.0f, 0.0f, true)
    }

    override fun setRotationAngles(
        entityIn: VoidRunnerEntity, limbSwing: Float, limbSwingAmount: Float,
        ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {}

    override fun render(
        matrixStackIn: MatrixStack, bufferIn: IVertexBuilder, packedLightIn: Int,
        packedOverlayIn: Int, red: Float, green: Float, blue: Float, alpha: Float
    ) {
        head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
        legLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
        legRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
    }
}