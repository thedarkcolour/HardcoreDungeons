package thedarkcolour.hardcoredungeons.worldgen.biome

import thedarkcolour.hardcoredungeons.data.WorldGenProvider

object RainbowPlainsBiome : ModBiome() {
    override fun configure(info: WorldGenProvider.BiomeConfiguration) {
        info.climateSettings.apply {
            setHasPrecipitation(false)
            temperature = 1.5f
            downfall = 0.0f
        }
        info.specialEffects.apply {
            waterColor(0x5008b2)
            waterFogColor(0x621ff2)
            skyColor(0xcfb3ff)
            fogColor(0xe388cf)
        }
    }
}