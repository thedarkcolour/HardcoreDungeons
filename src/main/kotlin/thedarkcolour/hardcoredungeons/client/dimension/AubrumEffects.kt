package thedarkcolour.hardcoredungeons.client.dimension

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.world.DimensionRenderInfo
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.math.vector.Vector3f
import net.minecraftforge.client.ISkyRenderHandler
import org.lwjgl.opengl.GL11
import thedarkcolour.hardcoredungeons.util.modLoc

object AubrumEffects : DimensionRenderInfo(Float.NaN, true, FogType.NONE, false, true) {
    private val SKY_TEXTURES_AUBRUM = modLoc("textures/environment/aubrum_sky")

    init {
        skyRenderHandler = ISkyRenderHandler { _, _, stack, _, mc ->
            renderCustomSky(stack, mc)
        }
    }

    @Suppress("DEPRECATION")
    private fun renderCustomSky(stack: MatrixStack, mc: Minecraft) {
        RenderSystem.disableAlphaTest()
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.depthMask(false)
        mc.textureManager.bindTexture(SKY_TEXTURES_AUBRUM)
        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.buffer
        for (i in 0..5) {
            stack.push()
            when (i) {
                1 -> stack.rotate(Vector3f.XP.rotationDegrees(90.0f))
                2 -> stack.rotate(Vector3f.XP.rotationDegrees(-90.0f))
                3 -> stack.rotate(Vector3f.XP.rotationDegrees(180.0f))
                4 -> stack.rotate(Vector3f.ZP.rotationDegrees(90.0f))
                5 -> stack.rotate(Vector3f.ZP.rotationDegrees(-90.0f))
            }
            val matrix4f = stack.last.matrix
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
            buffer.pos(matrix4f, -100.0f, -100.0f, -100.0f).tex(0.0f, 0.0f).color(40, 40, 40, 255).endVertex()
            buffer.pos(matrix4f, -100.0f, -100.0f, 100.0f).tex(0.0f, 16.0f).color(40, 40, 40, 255).endVertex()
            buffer.pos(matrix4f, 100.0f, -100.0f, 100.0f).tex(16.0f, 16.0f).color(40, 40, 40, 255).endVertex()
            buffer.pos(matrix4f, 100.0f, -100.0f, -100.0f).tex(16.0f, 0.0f).color(40, 40, 40, 255).endVertex()
            tessellator.draw()
            stack.pop()
        }
        RenderSystem.depthMask(true)
        RenderSystem.enableTexture()
        RenderSystem.disableBlend()
        RenderSystem.enableAlphaTest()
    }

    override fun func_230494_a_(p_230494_1_: Vector3d, p_230494_2_: Float): Vector3d {
        return p_230494_1_
    }

    override fun func_230493_a_(x: Int, z: Int): Boolean {
        return false
    }

    override fun func_230492_a_(p_230492_1_: Float, p_230492_2_: Float): FloatArray? {
        return null
    }
}