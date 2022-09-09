package thedarkcolour.hardcoredungeons.client.model.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.AgeableListModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.builders.LayerDefinition
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.legacy.modelConfigure
import thedarkcolour.hardcoredungeons.util.PI
import thedarkcolour.hardcoredungeons.util.toRadians
import kotlin.math.cos

/**
 * Made with help from BlockBench
 */
@Suppress("LeakingThis")
open class DoeModel(root: ModelPart) : AgeableListModel<DeerEntity>(false, 24.0f, 4.0f) {
    private val legFrontRight = root.getChild("leg_front_right")
    private val legFrontLeft = root.getChild("leg_front_left")
    private val body = root.getChild("body")
    private val legBackRight = root.getChild("leg_back_right")
    private val legBackLeft = root.getChild("leg_back_left")
    private val head = root.getChild("head")
    protected val face = root.getChild("face")

    override fun setupAnim(
        entity: DeerEntity, limbSwing: Float, limbSwingAmount: Float,
        ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {
        head.xRot = toRadians(headPitch)
        head.yRot = toRadians(netHeadYaw)
        legBackRight.xRot =  cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount
        legBackLeft.xRot =   cos(limbSwing * 0.6662f + PI) * 1.4f * limbSwingAmount
        legFrontRight.xRot = cos(limbSwing * 0.6662f + PI) * 1.4f * limbSwingAmount
        legFrontLeft.xRot =  cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount
    }

    private val headParts = listOf(head)
    private val bodyParts = listOf(legFrontRight, legFrontLeft, legBackRight, legBackLeft, body)

    override fun headParts(): Iterable<ModelPart> {
        return headParts
    }


    override fun bodyParts(): Iterable<ModelPart> {
        return bodyParts
    }

    override fun renderToBuffer(
        stack: PoseStack, buffer: VertexConsumer, light: Int,
        overlay: Int, r: Float, g: Float, b: Float, a: Float
    ) {
        if (young) {
            stack.pushPose()
            stack.scale(0.5f, 0.5f, 0.5f)
            stack.translate(0.0, 24.0 / 16.0, 0.0)
            bodyParts().forEach { renderer ->
                renderer.render(stack, buffer, light, overlay, r, g, b, a)
            }
            head.render(stack, buffer, light, overlay, r, g, b, a)
            stack.popPose()
        } else {
            headParts().forEach { renderer ->
                renderer.render(stack, buffer, light, overlay, r, g, b, a)
            }
            bodyParts().forEach { renderer ->
                renderer.render(stack, buffer, light, overlay, r, g, b, a)
            }
        }
    }

    companion object {
        fun doeModel() = modelConfigure {
            texWidth = 64
            texHeight = 64

            val legFrontRight by this
            legFrontRight.setPos(4.0f, 12.0f, -6.5f)
            legFrontRight.addBox(-1.0f, 0.0f, -1.5f, 2.0f, 12.0f, 3.0f, 0.0f, false)

            val legFrontLeft by this
            legFrontLeft.setPos(4.0f, 12.0f, -6.5f)
            legFrontLeft.addBox(-9.0f, 0.0f, -1.5f, 2.0f, 12.0f, 3.0f, 0.0f, true)

            val body by this
            body.setPos(0.0f, 24.0f, 0.0f)
            body.addBox(-5.0f, -20.0f, -8.0f, 10.0f, 8.0f, 17.0f, 0.0f, false)

            val tail by this
            tail.setPos(0.0f, -17.0f, 8.25f)
            tail.texOffs(54, 13)
            tail.xRot = 0.6109f
            tail.addBox(-1.0f, 0.0f, -1.0f, 2.0f, 5.0f, 2.0f, 0.0f, false)
            body.addChild(tail)

            val legBackRight by this
            legBackRight.setPos(4.0f, 12.0f, 7.5f)
            legBackRight.addBox(-1.0f, 0.0f, -1.5f, 2.0f, 12.0f, 3.0f, 0.0f, false)

            val legBackLeft by this
            legBackLeft.setPos(-4.0f, 12.0f, 7.5f)
            legBackLeft.addBox(-1.0f, 0.0f, -1.5f, 2.0f, 12.0f, 3.0f, 0.0f, false)

            val head by this
            head.setPos(0.0f, 8.0f, -6.0f)

            val neck1 by this
            neck1.setPos(0.0f, -2.25f, 0.5f)
            neck1.texOffs(37, 0).addBox(-2.5f, -8.0f, -2.5f, 5.0f, 8.0f, 5.0f, 0.0f, false)
            neck1.xRot = 0.6981f
            head.addChild(neck1) // head

            val neck2 by this
            neck2.setPos(0.0f, 1.0f, -0.5f)
            neck2.xRot = 0.4363f
            neck2.texOffs(8, 25).addBox(-2.0f, -8.2113f, -1.9532f, 4.0f, 9.0f, 3.0f, 0.0f, false)
            head.addChild(neck2) // head

            val face by this
            face.setPos(0.0f, -7.0f, -5.0f)
            face.texOffs(22, 25).addBox(-3.0f, -5.0f, -3.0f, 6.0f, 5.0f, 6.0f, 0.0f, false)
            face.texOffs(40, 25).addBox(-2.0f, -3.5f, -6.0f, 4.0f, 3.0f, 3.0f, 0.0f, false)
            head.addChild(face)

            val ear1 by this
            ear1.setPos(-1.5f, -3.0f, 1.5f)
            ear1.texOffs(0, 25)
            ear1.zRot = -0.8727f
            ear1.addBox(-1.5f, -6.0f, -0.5f, 3.0f, 6.0f, 1.0f, 0.0f, false)
            face.addChild(ear1)

            val ear2 by this
            ear2.setPos(1.25f, -3.0f, 1.5f)
            ear2.texOffs(0, 25)
            ear2.zRot = 0.8727f
            ear2.addBox(-1.25F, -6.0F, -0.5F, 3.0f, 6.0f, 1.0f, 0.0f, false)
            face.addChild(ear2)
        }
    }
}