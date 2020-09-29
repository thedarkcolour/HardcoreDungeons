package thedarkcolour.hardcoredungeons.entity.misc.cheeky

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.ai.goal.Goal

class CheekyModel : EntityModel<CheekyEntity>() {
    private val cheeky: ModelRenderer

    init {
        textureWidth = 128
        textureHeight = 64

        cheeky = ModelRenderer(this)
        cheeky.setRotationPoint(0.0f, 11.0f, 0.0f)
        cheeky.addBox(-16.0f, -19.0f, -16.0f, 32.0f, 32.0f, 32.0f, 0.0f, false)
    }

    /**
     * Sets this entity's model rotation angles
     */
    override fun setRotationAngles(entityIn: CheekyEntity, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {}

    override fun render(
        matrixStackIn: MatrixStack,
        bufferIn: IVertexBuilder,
        packedLightIn: Int,
        packedOverlayIn: Int,
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float
    ) {
        cheeky.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha)
    }

    class SpeakGoal(val entity: CheekyEntity) : Goal() {
        init {

        }

        override fun shouldExecute(): Boolean {
            return entity.isSpeaking
        }
    }
}