package thedarkcolour.hardcoredungeons.registry

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.biome.BiomeGenerationSettings
import net.minecraft.world.gen.GenerationStage.Decoration
import net.minecraft.world.gen.Heightmap
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig
import net.minecraft.world.gen.placement.DepthAverageConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import thedarkcolour.hardcoredungeons.HardcoreDungeons

@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType")
object HFeatures {
    // @formatter:off

    // Overworld Biomes
    val SHROOMY_BOULDER = register("shroomy_boulder_feature", Feature.FOREST_ROCK.withConfiguration(BlockStateFeatureConfig(HBlocks.SHROOMY_COBBLESTONE.defaultState)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT.func_242732_c(5)))
    val OAK_SHRUBS = register("oak_shrubs", Feature.TREE.withConfiguration(BaseTreeFeatureConfig.Builder(SimpleBlockStateProvider(Blocks.OAK_LOG.defaultState), SimpleBlockStateProvider(Blocks.OAK_LEAVES.defaultState), BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2), StraightTrunkPlacer(1, 0, 0), TwoLayerFeature(0, 0, 0)).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()))

    // Castleton
    val LUMLIGHT_TREE = register("lumlight_tree", Feature.TREE.withConfiguration(BaseTreeFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.LUMLIGHT_LOG.defaultState), SimpleBlockStateProvider(HBlocks.LUMLIGHT_LEAVES.defaultState), FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), StraightTrunkPlacer(4, 4, 7), TwoLayerFeature(0, 0, 0)).build()))
    val LUMLIGHT_SHRUBS = register("lumlight_shrubs", Feature.TREE.withConfiguration(BaseTreeFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.LUMLIGHT_LOG.defaultState), SimpleBlockStateProvider(HBlocks.LUMLIGHT_LEAVES.defaultState), BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2), StraightTrunkPlacer(1, 0, 0), TwoLayerFeature(0, 0, 0)).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()))
    val SPARSE_LUMLIGHT_TREES = register("sparse_lumlight_trees", Feature.TREE.withConfiguration(BaseTreeFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.LUMLIGHT_LOG.defaultState), SimpleBlockStateProvider(HBlocks.LUMLIGHT_LEAVES.defaultState), FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), StraightTrunkPlacer(4, 4, 7), TwoLayerFeature(0, 0, 0)).build()).withPlacement(Placement.field_242902_f.configure(AtSurfaceWithExtraConfig(0, 0.01f, 1))))
    val PATCH_PURPLE_LUMSHROOM = register("patch_purple_lumshroom", Feature.RANDOM_PATCH.withConfiguration(flowerPatchConfig(HBlocks.PURPLE_LUMSHROOM)))

    // Rainbowland
    val RAINBOWSTONE_ORE = register("rainbowstone_ore", Feature.ORE.withConfiguration(OreFeatureConfig(BlockMatchRuleTest(HBlocks.RAINBOW_ROCK), HBlocks.RAINBOWSTONE_ORE.defaultState, 3)).withPlacement(Placement.field_242910_o.configure(DepthAverageConfig(16, 12))))

    // Aubrum
    val PATCH_GOLDEN_TULIP = register("patch_golden_tulip", Feature.FLOWER.withConfiguration(BlockClusterFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.GOLDEN_TULIP.defaultState), SimpleBlockPlacer()).tries(64).build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT))
    // @formatter:on

    fun withShroomyBoulders(biome: BiomeGenerationSettings.Builder) {
        biome.withFeature(Decoration.LOCAL_MODIFICATIONS, SHROOMY_BOULDER)
    }

    fun withAubrumFlowers(biome: BiomeGenerationSettings.Builder) {
        biome.withFeature(Decoration.VEGETAL_DECORATION, PATCH_GOLDEN_TULIP)
    }

    fun addPurpleLumshrooms(biome: BiomeGenerationSettings.Builder) {
        biome.withFeature(Decoration.VEGETAL_DECORATION, PATCH_PURPLE_LUMSHROOM)
    }
/*
    fun addLumlightTrees(biome: Biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LUMLIGHT_TREE_FEATURE.func_225566_b_(LUMLIGHT_TREE_CONFIG).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_225566_b_(AtSurfaceWithExtraConfig(5, 0.1f, 1))))
    }*/

    fun addSparseLumlightTrees(biome: BiomeGenerationSettings.Builder) {
        biome.withFeature(Decoration.VEGETAL_DECORATION, SPARSE_LUMLIGHT_TREES)
    }
    /*
    fun addTallLumlightTrees(biome: BiomeGenerationSettings.Builder) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, TALL_LUMLIGHT_TREE_FEATURE.withConfiguration(LUMLIGHT_LOG_LEAVES_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(AtSurfaceWithExtraConfig(0, 0.01f, 1))))//Feature.RANDOM_SELECTOR.func_225566_b_(MultipleRandomFeatureConfig(listOf(LUMLIGHT_TREE_FEATURE.func_225566_b_(LUMLIGHT_TREE_CONFIG).withChance(0.33333334f)), Feature.NORMAL_TREE.func_225566_b_(LUMLIGHT_TREE_CONFIG))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_225566_b_(AtSurfaceWithExtraConfig(0, 0.05f, 1))))
    }*/

    fun withLumlightShrubs(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.withFeature(Decoration.VEGETAL_DECORATION, LUMLIGHT_SHRUBS)
    }

    fun withOakShrubs(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.withFeature(Decoration.VEGETAL_DECORATION, OAK_SHRUBS)
    }

    fun withRainbowlandOres(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.withFeature(Decoration.UNDERGROUND_DECORATION, RAINBOWSTONE_ORE)
    }

    fun withRainbowlandStructures(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.withStructure(HStructures.RAINBOW_FACTORY_FEATURE)
    }

    fun withLumlightCabin(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.withStructure(HStructures.LUMLIGHT_CABIN_FEATURE)
    }

    private fun flowerPatchConfig(flower: Block): BlockClusterFeatureConfig {
        return BlockClusterFeatureConfig.Builder(SimpleBlockStateProvider(flower.defaultState), SimpleBlockPlacer()).tries(
            64
        ).build()
    }

    fun <FC : IFeatureConfig, F : Feature<FC>> register(name: String, feature: ConfiguredFeature<FC, F>): ConfiguredFeature<FC, F> {
        return Registry.register(
            WorldGenRegistries.CONFIGURED_FEATURE,
            ResourceLocation(HardcoreDungeons.ID, name),
            feature
        )
    }

    fun withMushroomHut(generation: BiomeGenerationSettingsBuilder) {
        generation.withStructure(HStructures.MUSHROOM_HUT_FEATURE)
    }
}