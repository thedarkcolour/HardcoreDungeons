package thedarkcolour.hardcoredungeons.client.dimension

import net.minecraft.client.renderer.DimensionSpecialEffects
import net.minecraft.world.phys.Vec3
import thedarkcolour.hardcoredungeons.util.modLoc

object AubrumEffects : DimensionSpecialEffects(Float.NaN, true, SkyType.NONE, false, true) {
    private val SKY_TEXTURES_AUBRUM = modLoc("textures/environment/aubrum_sky")

    init {
        /*skyRenderHandler = ISkyRenderHandler { _, _, stack, _, mc ->
            renderCustomSky(stack, mc)
        }*/
    }

    override fun getBrightnessDependentFogColor(fogColor: Vec3, brightness: Float): Vec3 {
        return fogColor
    }

    override fun isFoggyAt(x: Int, z: Int): Boolean {
        return false
    }

    override fun getSunriseColor(timeOfDay: Float, partialTicks: Float): FloatArray? {
        return null
    }
}
