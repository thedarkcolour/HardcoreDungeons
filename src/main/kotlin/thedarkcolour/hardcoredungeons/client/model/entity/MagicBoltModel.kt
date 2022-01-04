package thedarkcolour.hardcoredungeons.client.model.entity

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity

class MagicBoltModel : EntityModel<MagicBoltEntity>() {
    private val part = ModelRenderer(this)

    init {
        part.setPos(0.0f, 24.0f, 0.0f)
        part.addBox(-2.0f, -4.0f, -2.0f, 4.0f, 4.0f, 4.0f, 0.0f, false)
    }

    override fun renderToBuffer(stack: MatrixStack, buffer: IVertexBuilder, light: Int, overlay: Int, r: Float, g: Float, b: Float, a: Float) {
        part.render(stack, buffer, light, overlay)
    }

    override fun setupAnim(
        stack: MagicBoltEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
    }
}