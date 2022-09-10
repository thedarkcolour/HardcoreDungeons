package thedarkcolour.hardcoredungeons.client.model.armor

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.LivingEntity
import thedarkcolour.hardcoredungeons.legacy.modelConfigure

// Made in BlockBench
class PendantModel(val root: ModelPart) : EntityModel<LivingEntity>() {
    override fun setupAnim(
        entity: LivingEntity, limbSwing: Float, limbSwingAmount: Float,
        ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) = Unit

    override fun renderToBuffer(
        stack: PoseStack, buffer: VertexConsumer, light: Int,
        overlay: Int, r: Float, g: Float, b: Float, a: Float
    ) {
        root.render(stack, buffer, light, overlay, r, g, b, a)
    }

    companion object {
        fun pendantModel() = modelConfigure {
            texWidth = 32
            texHeight = 16

            val aurigoldPiece by this
            aurigoldPiece.setPos(0.0f, 0.0f, -0.5f)
            aurigoldPiece.texOffs(0, 2).addBox(-2.0F, 1.0F, -3.0F, 4.0F, 4.0F, 1.0F, 0.0F, false)
            aurigoldPiece.texOffs(7, 0).addBox(-1.0F, 5.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false)
            aurigoldPiece.texOffs(1, 0).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false)

            val chain by this
            chain.texOffs(5, 2).addBox(4.0F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false)
            chain.texOffs(5, 2).addBox(-5.0F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false)
            chain.texOffs(0, 8).addBox(-5.0F, -1.0F, 2.5F, 10.0F, 1.0F, 1.0F, 0.0F, false)

            val chainLeft by this
            chainLeft.setRotationAngle(0.0456f, 0.1685f, 0.2657f)
            chainLeft.texOffs(0, 10).addBox(-4.0F, 0.0F, -3.25F, 4.0F, 1.0F, 1.0F, 0.0F, true)


            val chainRight by this
            chainRight.setRotationAngle(0.0456f, -0.1685f, -0.2657f)
            chainRight.texOffs(0, 10).addBox(0.0f, 0.0f, -3.25f, 4.0f, 1.0f, 1.0f, 0.0f, false)

            chain.addChild(chainLeft)
            chain.addChild(chainRight)
        }
    }
}