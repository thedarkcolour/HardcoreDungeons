package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.data.worldgen.BiomeDefaultFeatures
import thedarkcolour.hardcoredungeons.data.WorldGenProvider

object MushroomCliffsBiome : ModBiome() {
    override fun configure(info: WorldGenProvider.BiomeConfiguration) {
        info.climateSettings.apply {
            setHasPrecipitation(false)
            temperature = 0.9f
            downfall = 1.0f
        }
        info.specialEffects.apply {
            waterColor(0x3f76e4)
            waterFogColor(0x50533)
            skyColor(getSkyForTemp(0.9f))
            fogColor(0xc0d8ff)
        }
        info.generationSettings.apply {
            BiomeDefaultFeatures.addDefaultCarversAndLakes(this)
            BiomeDefaultFeatures.addDefaultMonsterRoom(this)
            BiomeDefaultFeatures.addDefaultUndergroundVariety(this)
            BiomeDefaultFeatures.addDefaultOres(this)
            BiomeDefaultFeatures.addMushroomFieldVegetation(this)
            BiomeDefaultFeatures.addDefaultExtraVegetation(this)
            //HWorldGen.addShroomyBoulders(this)
        }
    }
}