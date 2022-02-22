package thedarkcolour.hardcoredungeons.client.model.armor

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.LivingEntity
import thedarkcolour.hardcoredungeons.client.model.entity.setRotationAngle

// Made in BlockBench
class PendantModel : EntityModel<LivingEntity>() {
    private val pendant: ModelRenderer

    init {
        texWidth = 32
        texHeight = 16

        this.pendant = ModelRenderer(this)

        val aurigoldPiece = ModelRenderer(this)
        aurigoldPiece.setPos(0.0f, 0.0f, -0.5f)
        aurigoldPiece.texOffs(0, 2).addBox(-2.0F, 1.0F, -3.0F, 4.0F, 4.0F, 1.0F, 0.0F, false)
        aurigoldPiece.texOffs(7, 0).addBox(-1.0F, 5.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false)
        aurigoldPiece.texOffs(1, 0).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false)

        val chain = ModelRenderer(this)
        chain.texOffs(5, 2).addBox(4.0F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false)
        chain.texOffs(5, 2).addBox(-5.0F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false)
        chain.texOffs(0, 8).addBox(-5.0F, -1.0F, 2.5F, 10.0F, 1.0F, 1.0F, 0.0F, false)

        val chainLeft = ModelRenderer(this)
        chainLeft.setRotationAngle(0.0456f, 0.1685f, 0.2657f)
        chainLeft.texOffs(0, 10).addBox(-4.0F, 0.0F, -3.25F, 4.0F, 1.0F, 1.0F, 0.0F, true)


        val chainRight = ModelRenderer(this)
        chainRight.setRotationAngle(0.0456f, -0.1685f, -0.2657f)
        chainRight.texOffs(0, 10).addBox(0.0f, 0.0f, -3.25f, 4.0f, 1.0f, 1.0f, 0.0f, false)

        chain.addChild(chainLeft)
        chain.addChild(chainRight)

        this.pendant.addChild(aurigoldPiece)
        this.pendant.addChild(chain)
    }

    override fun setupAnim(
        entity: LivingEntity, limbSwing: Float, limbSwingAmount: Float,
        ageInTicks: Float, netHeadYaw: Float, headPitch: Float
    ) = Unit

    override fun renderToBuffer(
        stack: MatrixStack, buffer: IVertexBuilder, light: Int,
        overlay: Int, r: Float, g: Float, b: Float, a: Float
    ) {
        pendant.render(stack, buffer, light, overlay, r, g, b, a)
    }
}