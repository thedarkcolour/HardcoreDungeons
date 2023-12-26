package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.world.level.levelgen.GenerationStep
import thedarkcolour.hardcoredungeons.data.WorldGenProvider
import thedarkcolour.hardcoredungeons.worldgen.HFeatures

object GumdropFieldsBiome : ModBiome() {
    override fun configure(info: WorldGenProvider.BiomeConfiguration) {
        info.climateSettings.apply {
            setHasPrecipitation(false)
            temperature = 1.5f
            downfall = 0.4f
        }
        info.specialEffects.apply {
            waterColor(0xd19bdc)
            waterFogColor(0x50533)
            skyColor(getSkyForTemp(0.9f))
            fogColor(12638463)
        }
        info.generationSettings.apply {
            addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, HFeatures.SPARSE_CANDY_CANES)
            addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, HFeatures.SPARSE_CHOCOLATE_BARS)
        }
    }
}
