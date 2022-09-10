package thedarkcolour.hardcoredungeons.client.model.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelPart
import thedarkcolour.hardcoredungeons.entity.castleton.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.legacy.modelConfigure
import thedarkcolour.hardcoredungeons.util.toRadians

class FrayedSoulModel(root: ModelPart) : EntityModel<FrayedSoulEntity>() {
    private val head = root.getChild("head")

    override fun renderToBuffer(stack: PoseStack, builder: VertexConsumer, packedLight: Int, overlay: Int, r: Float, g: Float, b: Float, a: Float) {
        head.render(stack, builder, packedLight, overlay, r, g, b, a)
    }

    override fun setupAnim(
        entityIn: FrayedSoulEntity, limbSwing: Float, limbSwingAmount: Float,
        ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) {
        head.yRot = toRadians(netHeadYaw)
        head.xRot = toRadians(headPitch)
    }

    companion object {
        fun frayedSoulModel() = modelConfigure {
            texHeight = 32
            texWidth = 32

            val head by this
            head.setPos(0.0f, 20.0f, 0.0f)
            // Head
            head.addBox(-4.0f, -4.0f, -4.0f, 8.0f, 6.0f, 8.0f, 0.0f, false)
            // Jaw
            head.texOffs(0, 14)
            head.addBox(-2.0f, 2.0f, -4.0f, 4.0f, 2.0f, 6.0f, 0.0f, false)
        }
    }
}