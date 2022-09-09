package thedarkcolour.hardcoredungeons.client.dimension

import com.mojang.math.Vector3f
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.DimensionSpecialEffects
import net.minecraft.client.renderer.LightTexture
import net.minecraft.world.phys.Vec3

object CastletonEffects : DimensionSpecialEffects(Float.NaN, true, SkyType.NONE, false, true) {
    override fun getBrightnessDependentFogColor(fogColor: Vec3, brightness: Float): Vec3 {
        return fogColor
    }

    // Render thick fog?
    override fun isFoggyAt(x: Int, z: Int): Boolean {
        return false
    }

    override fun adjustLightmapColors(level: ClientLevel, partialTicks: Float, skyDarken: Float, skyLight: Float, blockLight: Float, pixelX: Int, pixelY: Int, colors: Vector3f) {
        val f9 = LightTexture.getBrightness(level.dimensionType(), pixelX) * skyLight * 0.7f
        val f10 = f9 * ((f9 * 0.6f + 0.4f) * 0.6f + 0.4f)
        val f11 = f9 * (f9 * f9 * 0.6f + 0.4f)

        colors.set(f10, f11, f9)
    }

    override fun getSunriseColor(p_230492_1_: Float, p_230492_2_: Float): FloatArray? {
        return null
    }
}