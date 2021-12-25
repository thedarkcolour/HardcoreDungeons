package thedarkcolour.hardcoredungeons.biome

import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.carver.ConfiguredCarvers
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.feature.HConfiguredFeatures
import thedarkcolour.hardcoredungeons.surfacebuilder.HConfiguredSurfaceBuilders

object CastletonHillsBiome : AbstractBiome() {
    init {
        precipitation = Biome.RainType.NONE
        category = Biome.Category.PLAINS
        depth = 1.5f
        scale = 0.07f
        temperature = 1.5f
        downfall = 0.0f
        effects = HBiomeMaker.DEFAULT_CASTLETON_EFFECTS.build()
    }

    override fun configure(generation: BiomeGenerationSettingsBuilder, spawns: MobSpawnInfo.Builder) {
        generation.surfaceBuilder(HConfiguredSurfaceBuilders.CASTLETON_SURFACE)

        generation.addCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.CAVE)

        HConfiguredFeatures.withSparseLumlightTrees(generation)
        HConfiguredFeatures.withPurpleLumshrooms(generation)
        DefaultBiomeFeatures.addBlueIce(generation) // why is this here?
    }
}