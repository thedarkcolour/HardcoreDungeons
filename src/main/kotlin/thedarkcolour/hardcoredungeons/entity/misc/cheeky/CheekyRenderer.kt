package thedarkcolour.hardcoredungeons.entity.misc.cheeky

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.entity.EntityRendererManager
import thedarkcolour.hardcoredungeons.entity.ReloadableRenderer
import thedarkcolour.hardcoredungeons.util.modLoc

class CheekyRenderer(renderManagerIn: EntityRendererManager) : ReloadableRenderer<CheekyEntity, CheekyModel>(renderManagerIn, ::CheekyModel, 1.4f) {
    override fun getEntityTexture(entity: CheekyEntity) = TEXTURE

    override fun render(
        cheeky: CheekyEntity,
        entityYaw: Float,
        partialTicks: Float,
        matrixStackIn: MatrixStack,
        bufferIn: IRenderTypeBuffer,
        packedLightIn: Int
    ) {
        super.render(cheeky, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn)

        if (cheeky.isSpeaking) {
            drawDialogue(cheeky, cheeky.speech, matrixStackIn, bufferIn, packedLightIn)
        }
    }

    private fun drawDialogue(cheeky: CheekyEntity, text: String, stack: MatrixStack, bufferIn: IRenderTypeBuffer, packedLightIn: Int) {
        val distance = renderManager.squareDistanceTo(cheeky)
        var displayNameIn = text

        if (distance <= 4096.0) {
            if (distance > 256.0) {
                displayNameIn = "..."
            }

            val height = cheeky.height + 0.5
            stack.push()
            stack.translate(0.0, height, 0.0)
            stack.rotate(renderManager.cameraOrientation)
            stack.scale(-0.025f, -0.025f, 0.025f)
            val matrix = stack.last.matrix
            val font = fontRendererFromRenderManager
            val center = -font.getStringWidth(displayNameIn) / 2.0f

            if (distance <= 256.0) {
                font.renderString(displayNameIn, center, 0.0f, -1, false, matrix, bufferIn, false, 0, packedLightIn)
            } else {
                font.renderString("...", center, 0.0f, -1, false, matrix, bufferIn, false, 0, packedLightIn)
            }

            stack.pop()
        }
    }

    companion object {
        private val TEXTURE = modLoc("textures/entity/cheeky.png")
    }
}