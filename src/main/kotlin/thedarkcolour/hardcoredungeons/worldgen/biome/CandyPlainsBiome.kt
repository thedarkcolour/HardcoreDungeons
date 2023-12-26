package thedarkcolour.hardcoredungeons.worldgen.biome

import thedarkcolour.hardcoredungeons.data.WorldGenProvider

object CandyPlainsBiome : ModBiome() {
    override fun configure(info: WorldGenProvider.BiomeConfiguration) {
        info.specialEffects.apply {
            defaultCandylandEffects(this)
        }
    }
}