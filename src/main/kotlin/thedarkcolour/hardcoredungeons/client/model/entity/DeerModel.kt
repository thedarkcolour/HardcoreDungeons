package thedarkcolour.hardcoredungeons.client.model.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.AgeableListModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.util.Mth
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.legacy.modelConfigure
import thedarkcolour.hardcoredungeons.util.toRadians
import kotlin.math.cos

/**
 * Made with help from BlockBench
 */
@Suppress("LeakingThis")
open class DeerModel(root: ModelPart) : AgeableListModel<DeerEntity>(false, 24.0f, 4.0f) {
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
        legBackLeft.xRot =   cos(limbSwing * 0.6662f + Mth.PI) * 1.4f * limbSwingAmount
        legFrontRight.xRot = cos(limbSwing * 0.6662f + Mth.PI) * 1.4f * limbSwingAmount
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

        fun stagModel() = doeModel().then {
            texHeight = 64
            texWidth = 64

            val antlerLeft1 by this
            antlerLeft1.setPos(-2.0f, -4.5f, 0.5f)
            antlerLeft1.texOffs(0, 32).addBox(-0.5f, -4.6303f, -0.2732f, 1.0f, 5.0f, 1.0f, 0.0f, false)
            antlerLeft1.xRot = -0.7854f

            val antlerLeft2 by this
            antlerLeft2.setPos(-2.0f, -7.5f, 3.0f)
            antlerLeft2.setRotationAngle(-0.9599f, -0.6981f, -0.1745f)
            antlerLeft2.texOffs(8, 38).addBox(-0.0781f, -4.2138f, -0.2729f, 1.0f, 4.0f, 1.0f, 0.0F, false)

            val antlerLeft3 by this
            antlerLeft3.setPos(-4.0f, -9.25f, 5.5f)
            antlerLeft3.setRotationAngle(-0.6109f, -0.7854f, 0.0f)
            antlerLeft3.texOffs(0, 38).addBox(-0.2934f, -6.25f, -0.0483f, 1.0f, 6.0f, 1.0f, 0.0f, false)

            val antlerLeft4 by this
            antlerLeft4.setPos(-2.0f, -7.5f, 3.0f)
            antlerLeft4.setRotationAngle( -0.9599f, -1.3963f, -0.1745f)
            antlerLeft4.texOffs(8, 38).addBox(-0.0781f, -4.2138f, -0.2729f, 1.0f, 4.0f, 1.0f, 0.0f, false)

            val antlerLeft5 by this
            antlerLeft5.setPos(-5.5f, -9.0F, 4.0F)
            antlerLeft5.setRotationAngle(0.1745f, 0.0873f, -0.6981f)
            antlerLeft5.texOffs(4, 38).addBox(-0.5807f, -5.1f, -0.4515f, 1.0f, 5.0f, 1.0f, 0.0F, false)

            val antlerLeft6 by this
            antlerLeft6.setPos(-2.5f, -7.75f, 3.75f)
            antlerLeft6.setRotationAngle(-0.9599f, 0.0873f, 0.0873f)
            antlerLeft6.texOffs(8, 38).addBox(-0.0781f, -4.2138f, -0.2729f, 1.0f, 4.0f, 1.0f, 0.0F, false)

            val antlerLeft7 by this
            antlerLeft7.setPos(-2.0F, -9.75f, 6.75f)
            antlerLeft7.setRotationAngle(-0.6109f, -0.0873f, 0.0F)
            antlerLeft7.texOffs(8, 38).addBox(-0.0444f, -4.3335f, -0.2096f, 1.0f, 4.0f, 1.0f, 0.0F, false)

            val antlerRight1 by this
            antlerRight1.setPos(-2.0f, -4.5f, 0.5f)
            antlerRight1.xRot = -0.7854f
            antlerRight1.texOffs(0, 32).addBox(3.5f, -4.6303f, -0.2732f, 1.0f, 5.0f, 1.0f, 0.0f, true)

            val antlerRight2 by this
            antlerRight2.setPos(2.0F, -7.5F, 3.0F)
            antlerRight2.setRotationAngle(-0.9599F, 0.6981F, 0.1745F)
            antlerRight2.texOffs(8, 38).addBox(-0.9219F, -4.2138F, -0.2729F, 1.0f, 4.0f, 1.0f, 0.0F, true)

            val antlerRight3 by this
            antlerRight3.setPos(4.0F, -9.25F, 5.5F)
            antlerRight3.setRotationAngle(-0.6109F, 0.7854F, 0.0F)
            antlerRight3.texOffs(0, 38).addBox(-0.7066F, -6.25F, -0.0483F, 1.0f, 6.0f, 1.0f, 0.0f, true)

            val antlerRight4 by this
            antlerRight4.setPos(2.0F, -7.5F, 3.0F)
            antlerRight4.setRotationAngle( -0.9599F, 1.3963F, 0.1745F)
            antlerRight4.texOffs(8, 38).addBox(-0.9219F, -4.2138F, -0.2729F, 1.0f, 4.0f, 1.0f, 0.0f, true)

            val antlerRight5 by this
            antlerRight5.setPos(5.5F, -9.0F, 4.0F)
            antlerRight5.setRotationAngle(0.1745F, -0.0873F, 0.6981F)
            antlerRight5.texOffs(4, 38).addBox(-0.4193F, -5.1F, -0.4515F, 1.0f, 5.0f, 1.0f, 0.0F, true)

            val antlerRight6 by this
            antlerRight6.setPos(2.5F, -7.75F, 3.75F)
            antlerRight6.setRotationAngle(-0.9599F, -0.0873F, -0.0873F)
            antlerRight6.texOffs(8, 38).addBox(-0.9219F, -4.2138F, -0.2729F, 1.0f, 4.0f, 1.0f, 0.0F, true)

            val antlerRight7 by this
            antlerRight7.setPos(2.0F, -9.75F, 6.75F)
            antlerRight7.setRotationAngle(-0.6109F, 0.0873F, 0.0F)
            antlerRight7.texOffs(8, 38).addBox(-0.9556F, -4.3335F, -0.2096F, 1.0f, 4.0f, 1.0f, 0.0F, true)

            "face".addChild(antlerLeft1)
            "face".addChild(antlerLeft2)
            "face".addChild(antlerLeft3)
            "face".addChild(antlerLeft4)
            "face".addChild(antlerLeft5)
            "face".addChild(antlerLeft6)
            "face".addChild(antlerLeft7)
            "face".addChild(antlerRight1)
            "face".addChild(antlerRight2)
            "face".addChild(antlerRight3)
            "face".addChild(antlerRight4)
            "face".addChild(antlerRight5)
            "face".addChild(antlerRight6)
            "face".addChild(antlerRight7)
        }
    }
}