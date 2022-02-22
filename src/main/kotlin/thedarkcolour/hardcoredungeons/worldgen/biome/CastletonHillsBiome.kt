package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.entity.EntityClassification
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer
import net.minecraft.world.gen.placement.DepthAverageConfig
import net.minecraft.world.gen.placement.NoPlacementConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.worldgen.feature.HConfiguredFeatures
import thedarkcolour.hardcoredungeons.worldgen.surfacebuilder.HConfiguredSurfaceBuilders

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

        DefaultBiomeFeatures.addDefaultCarvers(generation)
        HConfiguredFeatures.withPurpleLumshrooms(generation)

        val blueLumshrooms = Feature.RANDOM_PATCH.configured(BlockClusterFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.BLUE_LUMSHROOM.plant.defaultBlockState()), SimpleBlockPlacer()).tries(64).build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(8)
        val ore = Feature.ORE.configured(OreFeatureConfig(BlockMatchRuleTest(HBlocks.CASTLETON_STONE.stone.block), HBlocks.RUNED_CASTLETON_STONE.defaultBlockState(), 7)).decorated(Placement.DEPTH_AVERAGE.configured(
            DepthAverageConfig(24, 12)
        ))

        generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, blueLumshrooms)
        generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore)
        generation.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER)
        generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.TREE.configured(
                BaseTreeFeatureConfig.Builder(
                    SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.log.defaultBlockState()),
                    SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.leaves.defaultBlockState()),
                    FancyFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(3), 4),
                    FancyTrunkPlacer(9, 5, 0),
                    TwoLayerFeature(0, 3, 0)
                ).build()).decorated(Placement.HEIGHTMAP.configured(NoPlacementConfig.NONE)).squared()
        )

        spawns.addSpawn(EntityClassification.CREATURE, HEntities.CASTLETON_DEER, 10, 3, 8)
    }
}