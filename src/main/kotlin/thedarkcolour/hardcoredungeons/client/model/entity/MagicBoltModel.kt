package thedarkcolour.hardcoredungeons.client.model.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelPart
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.legacy.modelConfigure

class MagicBoltModel(root: ModelPart) : EntityModel<MagicBoltEntity>() {
    private val part = root.getChild("part")

    override fun renderToBuffer(stack: PoseStack, buffer: VertexConsumer, light: Int, overlay: Int, r: Float, g: Float, b: Float, a: Float) {
        part.render(stack, buffer, light, overlay)
    }

    override fun setupAnim(
        stack: MagicBoltEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {}

    companion object {
        fun magicBoltModel() = modelConfigure {
            val part by this
            part.setPos(0.0f, 24.0f, 0.0f)
            part.addBox(-2.0f, -4.0f, -2.0f, 4.0f, 4.0f, 4.0f, 0.0f, false)
        }
    }
}