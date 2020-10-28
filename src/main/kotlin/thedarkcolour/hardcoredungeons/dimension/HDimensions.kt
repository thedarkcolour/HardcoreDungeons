package thedarkcolour.hardcoredungeons.dimension

import net.minecraft.world.gen.settings.NoiseSettings
import net.minecraft.world.gen.settings.ScalingSettings
import net.minecraft.world.gen.settings.SlideSettings
import thedarkcolour.hardcoredungeons.util.modLoc

object HDimensions {
    val CASTLETON_LOCATION = modLoc("castleton")
    val AUBRUM_LOCATION = modLoc("aubrum")
    val RAINBOWLAND_LOCATION = modLoc("rainbowland")
/*
    fun setupDimensionEffects() {
        val renderInfoMap = DimensionRenderInfo.field_239208_a_
        val nether = DimensionRenderInfo.Nether()

        renderInfoMap[CASTLETON_LOCATION] = nether
        renderInfoMap[AUBRUM_LOCATION] = nether
    }*/

    /*
    val CASTLETON_NOISE_SETTINGS = noiseSettings(256,)

    fun dimensionSettings(
        structures: DimensionStructuresSettings
    ) {
        return DimensionSettings()
    }

    fun structureSettings(

    )
*/
    fun noiseSettings(
        height: Int,
        sampling: ScalingSettings,
        topSlide: SlideSettings,
        bottomSlide: SlideSettings,
        horizontalSize: Int,
        verticalSize: Int,
        densityFactor: Double,
        densityOffset: Double,
        simplexSurfaceNoise: Boolean,
        randomDensityOffset: Boolean,
        islandNoiseOverride: Boolean,
        amplified: Boolean,
    ): NoiseSettings {
        return NoiseSettings(height, sampling, topSlide, bottomSlide, horizontalSize, verticalSize, densityFactor, densityOffset, simplexSurfaceNoise, randomDensityOffset, islandNoiseOverride, amplified)
    }

    fun samplingSettings(
        horizontalScale: Double,
        verticalScale: Double,
        horizontalFactor: Double,
        verticalFactor: Double,
    ): ScalingSettings {
        return ScalingSettings(horizontalScale, verticalScale, horizontalFactor, verticalFactor)
    }

    fun slideSettings(
        target: Int,
        side: Int,
        offset: Int,
    ): SlideSettings {
        return SlideSettings(target, side, offset)
    }
}