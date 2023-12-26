package thedarkcolour.hardcoredungeons.worldgen.biome

import thedarkcolour.hardcoredungeons.data.WorldGenProvider

object AubrumWastelandBiome : ModBiome() {
    override fun configure(info: WorldGenProvider.BiomeConfiguration) {
        info.climateSettings.apply {
            setHasPrecipitation(false)
            temperature = 1.5f
            downfall = 0.0f
        }
        info.specialEffects.apply {
            defaultAubrumEffects(this)
        }
    }
}