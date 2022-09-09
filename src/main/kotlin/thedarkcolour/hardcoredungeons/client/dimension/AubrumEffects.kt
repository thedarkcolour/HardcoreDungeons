package thedarkcolour.hardcoredungeons.client.dimension

/*

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
        mc.textureManager.bind(SKY_TEXTURES_AUBRUM)
        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.builder
        for (i in 0..5) {
            stack.pushPose()
            when (i) {
                1 -> stack.mulPose(Vector3f.XP.rotationDegrees(90.0f))
                2 -> stack.mulPose(Vector3f.XP.rotationDegrees(-90.0f))
                3 -> stack.mulPose(Vector3f.XP.rotationDegrees(180.0f))
                4 -> stack.mulPose(Vector3f.ZP.rotationDegrees(90.0f))
                5 -> stack.mulPose(Vector3f.ZP.rotationDegrees(-90.0f))
            }
            val matrix4f = stack.last().pose()
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
            buffer.vertex(matrix4f, -100.0f, -100.0f, -100.0f).uv(0.0f, 0.0f).color(40, 40, 40, 255).endVertex()
            buffer.vertex(matrix4f, -100.0f, -100.0f, 100.0f).uv(0.0f, 16.0f).color(40, 40, 40, 255).endVertex()
            buffer.vertex(matrix4f, 100.0f, -100.0f, 100.0f).uv(16.0f, 16.0f).color(40, 40, 40, 255).endVertex()
            buffer.vertex(matrix4f, 100.0f, -100.0f, -100.0f).uv(16.0f, 0.0f).color(40, 40, 40, 255).endVertex()
            tessellator.end()
            stack.popPose()
        }
        RenderSystem.depthMask(true)
        RenderSystem.enableTexture()
        RenderSystem.disableBlend()
        RenderSystem.enableAlphaTest()
    }

    override fun getBrightnessDependentFogColor(p_230494_1_: Vector3d, p_230494_2_: Float): Vector3d {
        return p_230494_1_
    }

    override fun isFoggyAt(x: Int, z: Int): Boolean {
        return false
    }

    override fun getSunriseColor(p_230492_1_: Float, p_230492_2_: Float): FloatArray? {
        return null
    }
}*/
