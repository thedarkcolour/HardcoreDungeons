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
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer
import thedarkcolour.hardcoredungeons.HardcoreDungeons

@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType")
object HFeatures {
    //val LUMLIGHT_TREE_FEATURE = LumlightTreeFeature { TreeFeatureConfig.deserializeFoliage(it) }.setRegistryKey("lumlight_tree")
    //val TALL_LUMLIGHT_TREE_FEATURE = TallTreeFeature { TreeFeatureConfig.deserializeFoliage(it) }.setRegistryKey("tall_lumlight_tree")

    val SHROOMY_BOULDER = register("shroomy_boulder", Feature.FOREST_ROCK.withConfiguration(BlockStateFeatureConfig(HBlocks.SHROOMY_COBBLESTONE.defaultState)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT.func_242732_c(5)))
    val PATCH_PURPLE_LUMSHROOM = register("patch_purple_lumshroom", Feature.RANDOM_PATCH.withConfiguration(flowerPatchConfig(HBlocks.PURPLE_LUMSHROOM)))

    val LUMLIGHT_TREE_CONFIG = BaseTreeFeatureConfig.Builder(
        SimpleBlockStateProvider(HBlocks.LUMLIGHT_LOG.defaultState),
        SimpleBlockStateProvider(HBlocks.LUMLIGHT_LEAVES.defaultState),
        FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4),
        StraightTrunkPlacer(4, 4, 7),
        TwoLayerFeature(0, 0, 0)
    ).build()

    fun <FC : IFeatureConfig, F : Feature<FC>> register(name: String, feature: ConfiguredFeature<FC, F>): ConfiguredFeature<FC, F> {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, ResourceLocation(HardcoreDungeons.ID, name), feature)
    }

    fun withShroomyBoulders(biome: BiomeGenerationSettings.Builder) {
        biome.withFeature(Decoration.LOCAL_MODIFICATIONS, SHROOMY_BOULDER)
    }
/*
    fun withAubrumFlowers(biome: BiomeGenerationSettings.Builder) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(GOLDEN_TULIP_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(FrequencyConfig(1))))
    }
*/
    fun addPurpleLumshrooms(biome: BiomeGenerationSettings.Builder) {
        biome.withFeature(Decoration.VEGETAL_DECORATION, PATCH_PURPLE_LUMSHROOM)
    }
/*
    fun addLumlightTrees(biome: Biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LUMLIGHT_TREE_FEATURE.func_225566_b_(LUMLIGHT_TREE_CONFIG).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_225566_b_(AtSurfaceWithExtraConfig(5, 0.1f, 1))))
    }*/

    fun addSparseLumlightTrees(biome: BiomeGenerationSettings.Builder) {
        biome.withFeature(Decoration.VEGETAL_DECORATION, Feature.TREE.withConfiguration(LUMLIGHT_TREE_CONFIG).withPlacement(Placement.field_242902_f.configure(AtSurfaceWithExtraConfig(0, 0.01f, 1))))//Feature.RANDOM_SELECTOR.func_225566_b_(MultipleRandomFeatureConfig(listOf(LUMLIGHT_TREE_FEATURE.func_225566_b_(LUMLIGHT_TREE_CONFIG).withChance(0.33333334f)), Feature.NORMAL_TREE.func_225566_b_(LUMLIGHT_TREE_CONFIG))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_225566_b_(AtSurfaceWithExtraConfig(0, 0.05f, 1))))
    }
/*
    fun addTallLumlightTrees(biome: BiomeGenerationSettings.Builder) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, TALL_LUMLIGHT_TREE_FEATURE.withConfiguration(LUMLIGHT_LOG_LEAVES_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(AtSurfaceWithExtraConfig(0, 0.01f, 1))))//Feature.RANDOM_SELECTOR.func_225566_b_(MultipleRandomFeatureConfig(listOf(LUMLIGHT_TREE_FEATURE.func_225566_b_(LUMLIGHT_TREE_CONFIG).withChance(0.33333334f)), Feature.NORMAL_TREE.func_225566_b_(LUMLIGHT_TREE_CONFIG))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_225566_b_(AtSurfaceWithExtraConfig(0, 0.05f, 1))))
    }

    fun addLumlightShrubs(biome: BiomeGenerationSettings.Builder) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Feature.JUNGLE_GROUND_BUSH.withConfiguration(LUMLIGHT_LOG_LEAVES_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(AtSurfaceWithExtraConfig(6, 0.3f, 2))))
    }
*/
    fun addOakShrubs(biome: BiomeGenerationSettings.Builder) {
        biome.withFeature(Decoration.VEGETAL_DECORATION,
            Feature.TREE.withConfiguration(
                BaseTreeFeatureConfig.Builder(
                    SimpleBlockStateProvider(Blocks.OAK_LOG.defaultState),
                    SimpleBlockStateProvider(Blocks.OAK_LEAVES.defaultState),
                    BushFoliagePlacer(
                        FeatureSpread.func_242252_a(2),
                        FeatureSpread.func_242252_a(1),
                        2
                    ),
                    StraightTrunkPlacer(1, 0, 0),
                    TwoLayerFeature(0, 0, 0)
                ).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()
            )
        )
    }

    private fun flowerPatchConfig(flower: Block): BlockClusterFeatureConfig {
        return BlockClusterFeatureConfig.Builder(SimpleBlockStateProvider(flower.defaultState), SimpleBlockPlacer()).tries(64).build()
    }

    // todo
    fun addRainbowlandOres(biome: BiomeGenerationSettings.Builder) {
        //biome.addFeature(Decoration.UNDERGROUND_DECORATION, Feature.ORE.withConfiguration(OreFeatureConfig(BlockMatchRuleTest(HBlocks.RAINBOW_ROCK), HBlocks.RAINBOWSTONE_ORE.defaultState, 3)).withPlacement(Placement.field_242910_o.configure(DepthAverageConfig(16, 12))))
    }
}