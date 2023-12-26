package thedarkcolour.hardcoredungeons.worldgen.biome

import thedarkcolour.hardcoredungeons.data.WorldGenProvider

object KnightlyShrublandBiome : ModBiome() {
    override fun configure(info: WorldGenProvider.BiomeConfiguration) {
        info.specialEffects.apply {
            defaultCastletonEffects(this)
        }
    }
}