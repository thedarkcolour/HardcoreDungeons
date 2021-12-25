package thedarkcolour.hardcoredungeons.biome

import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.feature.HConfiguredFeatures
import thedarkcolour.hardcoredungeons.surfacebuilder.HConfiguredSurfaceBuilders

object RainbowPlainsBiome : AbstractBiome() {
    init {
        precipitation = Biome.RainType.NONE
        category = Biome.Category.PLAINS
        depth = 0.1f
        scale = 0.2f
        temperature = 1.5f
        downfall = 0.0f
        effects = biomeFx(0x5008b2, 0x621ff2, 0xcfb3ff, 0xe388cf).build()
    }

    override fun configure(generation: BiomeGenerationSettingsBuilder, spawns: MobSpawnInfo.Builder) {
        generation.surfaceBuilder(HConfiguredSurfaceBuilders.RAINBOWLAND_SURFACE)

        HConfiguredFeatures.withRainbowlandOres(generation)
        HConfiguredFeatures.withRainbowlandStructures(generation)
    }
}