package thedarkcolour.hardcoredungeons.client.model.entity

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.AgeableModel
import net.minecraft.client.renderer.model.ModelRenderer
import thedarkcolour.hardcoredungeons.entity.deer.DeerEntity
import thedarkcolour.hardcoredungeons.util.PI
import thedarkcolour.hardcoredungeons.util.toRadians
import kotlin.math.cos

/**
 * Made with help from BlockBench
 */
@Suppress("LeakingThis")
open class DoeModel : AgeableModel<DeerEntity>(false, 24.0f, 4.0f) {
    private val legFrontRight: ModelRenderer
    private val legFrontLeft: ModelRenderer
    private val body: ModelRenderer
    private val tail: ModelRenderer
    private val legBackRight: ModelRenderer
    private val legBackLeft: ModelRenderer
    private val head: ModelRenderer
    private val neck1: ModelRenderer
    private val neck2: ModelRenderer
    protected val face: ModelRenderer
    private val ear1: ModelRenderer
    private val ear2: ModelRenderer

    init {
        textureWidth = 64
        textureHeight = 64

        legFrontRight = ModelRenderer(this)
        legFrontRight.setRotationPoint(4.0f, 12.0f, -6.5f)
        legFrontRight.addBox(-1.0f, 0.0f, -1.5f, 2.0f, 12.0f, 3.0f, 0.0f, false)

        legFrontLeft = ModelRenderer(this)
        legFrontLeft.setRotationPoint(4.0f, 12.0f, -6.5f)
        legFrontLeft.addBox(-9.0f, 0.0f, -1.5f, 2.0f, 12.0f, 3.0f, 0.0f, true)

        body = ModelRenderer(this)
        body.setRotationPoint(0.0f, 24.0f, 0.0f)
        body.addBox(-5.0f, -20.0f, -8.0f, 10.0f, 8.0f, 17.0f, 0.0f, false)

        tail = ModelRenderer(this)
        tail.setRotationPoint(0.0f, -17.0f, 8.25f)
        tail.setTextureOffset(54, 13)
        tail.rotateAngleX = 0.6109f
        tail.addBox(-1.0f, 0.0f, -1.0f, 2.0f, 5.0f, 2.0f, 0.0f,false)
        body.addChild(tail)

        legBackRight = ModelRenderer(this)
        legBackRight.setRotationPoint(4.0f, 12.0f, 7.5f)
        legBackRight.addBox(-1.0f, 0.0f, -1.5f, 2.0f, 12.0f, 3.0f, 0.0f, false)

        legBackLeft = ModelRenderer(this)
        legBackLeft.setRotationPoint(-4.0f, 12.0f, 7.5f)
        legBackLeft.addBox(-1.0f, 0.0f, -1.5f, 2.0f, 12.0f, 3.0f, 0.0f, false)

        head = ModelRenderer(this)
        head.setRotationPoint(0.0f, 8.0f, -6.0f)

        neck1 = ModelRenderer(this)
        neck1.setRotationPoint(0.0f, -2.25f, 0.5f)
        neck1.setTextureOffset(37, 0).addBox(-2.5f, -8.0f, -2.5f, 5.0f, 8.0f, 5.0f, 0.0f, false)
        neck1.rotateAngleX = 0.6981f
        head.addChild(neck1) // head

        neck2 = ModelRenderer(this)
        neck2.setRotationPoint(0.0f, 1.0f, -0.5f)
        neck2.rotateAngleX = 0.4363f
        neck2.setTextureOffset(8, 25).addBox(-2.0f, -8.2113f, -1.9532f, 4.0f, 9.0f, 3.0f, 0.0f, false)
        head.addChild(neck2) // head

        face = ModelRenderer(this)
        face.setRotationPoint(0.0f, -7.0f, -5.0f)
        face.setTextureOffset(22, 25).addBox(-3.0f, -5.0f, -3.0f, 6.0f, 5.0f, 6.0f, 0.0f, false)
        face.setTextureOffset(40, 25).addBox(-2.0f, -3.5f, -6.0f, 4.0f, 3.0f, 3.0f, 0.0f, false)
        head.addChild(face)

        ear1 = ModelRenderer(this)
        ear1.setRotationPoint(-1.5f, -3.0f, 1.5f)
        ear1.setTextureOffset(0, 25)
        ear1.rotateAngleZ = -0.8727f
        ear1.addBox(-1.5f, -6.0f, -0.5f, 3.0f, 6.0f, 1.0f, 0.0f, false)
        face.addChild(ear1)

        ear2 = ModelRenderer(this)
        ear2.setRotationPoint(1.25f, -3.0f, 1.5f)
        ear2.setTextureOffset(0, 25)
        ear2.rotateAngleZ = 0.8727f
        ear2.addBox(-1.25F, -6.0F, -0.5F, 3.0f, 6.0f, 1.0f, 0.0f, false)
        face.addChild(ear2)
    }

    override fun setRotationAngles(
        entity: DeerEntity, limbSwing: Float, limbSwingAmount: Float,
        ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {
        head.rotateAngleX = toRadians(headPitch)
        head.rotateAngleY = toRadians(netHeadYaw)
        legBackRight.rotateAngleX =  cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount
        legBackLeft.rotateAngleX =   cos(limbSwing * 0.6662f + PI) * 1.4f * limbSwingAmount
        legFrontRight.rotateAngleX = cos(limbSwing * 0.6662f + PI) * 1.4f * limbSwingAmount
        legFrontLeft.rotateAngleX =  cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount
    }

    private val headParts = listOf(head)
    private val bodyParts = listOf(legFrontRight, legFrontLeft, legBackRight, legBackLeft, body)

    override fun getHeadParts(): Iterable<ModelRenderer> {
        return headParts
    }


    override fun getBodyParts(): Iterable<ModelRenderer> {
        return bodyParts
    }

    override fun render(
        stack: MatrixStack, buffer: IVertexBuilder, light: Int,
        overlay: Int, r: Float, g: Float, b: Float, a: Float
    ) {
        if (isChild) {
            stack.push()
            stack.scale(0.5f, 0.5f, 0.5f)
            stack.translate(0.0, 24.0 / 16.0, 0.0)
            getBodyParts().forEach { renderer ->
                renderer.render(stack, buffer, light, overlay, r, g, b, a)
            }
            head.render(stack, buffer, light, overlay, r, g, b, a)
            stack.pop()
        } else {
            getHeadParts().forEach { renderer ->
                renderer.render(stack, buffer, light, overlay, r, g, b, a)
            }
            getBodyParts().forEach { renderer ->
                renderer.render(stack, buffer, light, overlay, r, g, b, a)
            }
        }
    }
}