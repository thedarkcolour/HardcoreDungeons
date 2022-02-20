package thedarkcolour.hardcoredungeons.biome

import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.feature.HConfiguredFeatures
import thedarkcolour.hardcoredungeons.surfacebuilder.HConfiguredSurfaceBuilders

object CandyPlainsBiome : AbstractBiome() {
    init {
        precipitation = Biome.RainType.NONE
        category = Biome.Category.PLAINS
        depth = 0.04f
        scale = 0.2f
        temperature = 1.5f
        downfall = 0.4f
        effects = biomeFx(0xd19bdc, 0x50533, getSkyForTemp(0.9f), 12638463).build()
    }

    override fun configure(generation: BiomeGenerationSettingsBuilder, spawns: MobSpawnInfo.Builder) {
        generation.surfaceBuilder(HConfiguredSurfaceBuilders.SUGARY_SURFACE)

        HConfiguredFeatures.withSparseCandyCanes(generation)
        HConfiguredFeatures.withSparseChocolateBars(generation)
    }
}