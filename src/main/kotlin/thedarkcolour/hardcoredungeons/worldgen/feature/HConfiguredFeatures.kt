package thedarkcolour.hardcoredungeons.worldgen.feature

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.biome.BiomeGenerationSettings
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.Heightmap
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig
import net.minecraft.world.gen.placement.DepthAverageConfig
import net.minecraft.world.gen.placement.NoPlacementConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import thedarkcolour.hardcoredungeons.block.combo.WoodCombo
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HFeatures
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.hardcoredungeons.worldgen.structure.HConfiguredStructures

@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
object HConfiguredFeatures {
    // Overworld Biomes
    val SHROOMY_BOULDER = Feature.FOREST_ROCK.configured(BlockStateFeatureConfig(HBlocks.SHROOMY_COBBLESTONE.block.defaultBlockState())).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.countRandom(5))
    val OAK_SHRUBS = Feature.TREE.configured(BaseTreeFeatureConfig.Builder(SimpleBlockStateProvider(Blocks.OAK_LOG.defaultBlockState()), SimpleBlockStateProvider(Blocks.OAK_LEAVES.defaultBlockState()), BushFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1), 2), StraightTrunkPlacer(1, 0, 0), TwoLayerFeature(0, 0, 0)).heightmap(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(AtSurfaceWithExtraConfig(6, 0.3f, 2)))
    val MALACHITE_CRYSTAL = HFeatures.CRYSTAL_FEATURE.configured(CrystalFeatureConfig(HBlocks.MALACHITE_CRYSTAL.crystal.defaultBlockState(), 0.1f)).squared()

    // Castleton
    val LUMLIGHT_TREE = oakStyleTree(HBlocks.LUMLIGHT_WOOD)
    val BIG_LUMLIGHT_TREE = Feature.TREE.configured(BaseTreeFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.log.defaultBlockState()), SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.leaves.defaultBlockState()), FancyFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(3), 4), FancyTrunkPlacer(9, 5, 0), TwoLayerFeature(0, 3, 0)).build()).decorated(Placement.HEIGHTMAP.configured(NoPlacementConfig.NONE)).squared()

    val LUMLIGHT_SHRUBS = Feature.TREE.configured(BaseTreeFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.log.defaultBlockState()), SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.leaves.defaultBlockState()), BushFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1), 2), StraightTrunkPlacer(1, 0, 0), TwoLayerFeature(0, 0, 0)).heightmap(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(AtSurfaceWithExtraConfig(6, 0.3f, 2)))
    val SPARSE_LUMLIGHT_TREES = Feature.TREE.configured(BaseTreeFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.log.defaultBlockState()), SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.leaves.defaultBlockState()), FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(4), 4), StraightTrunkPlacer(4, 4, 7), TwoLayerFeature(0, 0, 0)).build()).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(AtSurfaceWithExtraConfig(6, 0.3f, 2)))
    val PATCH_PURPLE_LUMSHROOM = Feature.RANDOM_PATCH.configured(flowerPatchConfig(HBlocks.PURPLE_LUMSHROOM.plant)).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(12)

    // Rainbowland
    val RAINBOWSTONE_ORE = Feature.ORE.configured(OreFeatureConfig(BlockMatchRuleTest(HBlocks.RAINBOW_ROCK.stone.block), HBlocks.RAINBOWSTONE_ORE.defaultBlockState(), 3)).decorated(Placement.DEPTH_AVERAGE.configured(DepthAverageConfig(16, 12)))

    // Aubrum
    val PATCH_GOLDEN_TULIP = Feature.FLOWER.configured(BlockClusterFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.GOLDEN_TULIP.plant.defaultBlockState()), SimpleBlockPlacer()).tries(64).build()).decorated(Features.Placements.HEIGHTMAP_SQUARE)
    val AURI_TREE = oakStyleTree(HBlocks.AURI_WOOD)

    // Candyland
    val SPARSE_CANDY_CANES = HFeatures.CANDY_CANE_FEATURE.configured(IFeatureConfig.NONE).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(AtSurfaceWithExtraConfig(0, 0.15f, 1)))
    val SPARSE_CHOCOLATE_BARS = HFeatures.CHOCOLATE_BAR_FEATURE.configured(IFeatureConfig.NONE).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(AtSurfaceWithExtraConfig(0, 0.05f, 1)))

    fun registerConfiguredFeatures() {
        registerConfiguredFeature("shroomy_boulder_feature", SHROOMY_BOULDER)
        registerConfiguredFeature("oak_shrubs", OAK_SHRUBS)
        registerConfiguredFeature("malachite_crystal", MALACHITE_CRYSTAL)
        registerConfiguredFeature("lumlight_tree", LUMLIGHT_TREE)
        registerConfiguredFeature("big_lumlight_tree", BIG_LUMLIGHT_TREE)
        registerConfiguredFeature("lumlight_shrubs", LUMLIGHT_SHRUBS)
        registerConfiguredFeature("sparse_lumlight_trees", SPARSE_LUMLIGHT_TREES)
        registerConfiguredFeature("patch_purple_lumshroom", PATCH_PURPLE_LUMSHROOM)
        registerConfiguredFeature("rainbowstone_ore", RAINBOWSTONE_ORE)
        registerConfiguredFeature("patch_golden_tulip", PATCH_GOLDEN_TULIP)
        registerConfiguredFeature("sparse_candy_canes", SPARSE_CANDY_CANES)
        registerConfiguredFeature("sparse_chocolate_bars", SPARSE_CHOCOLATE_BARS)
    }

    fun oakStyleTree(wood: WoodCombo): ConfiguredFeature<BaseTreeFeatureConfig, *> {
        return Feature.TREE.configured(
            BaseTreeFeatureConfig.Builder(
                SimpleBlockStateProvider(wood.log.defaultBlockState()),
                SimpleBlockStateProvider(wood.leaves.defaultBlockState()),
                // Radius, offset, height
                FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(4), 3),
                // BaseHeight, HeightRandA, HeightRandB
                StraightTrunkPlacer(3, 4, 7),
                // Minimum Size (limit, lower, upper)
                TwoLayerFeature(0, 0, 0)
            ).heightmap(Heightmap.Type.MOTION_BLOCKING).build()
        )
    }

    /*fun a() {
        val a = Feature.TREE.configured(
            BaseTreeFeatureConfig.Builder(
                SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.log.defaultBlockState()),
                SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.leaves.defaultBlockState()),
                FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(4), 4),
                StraightTrunkPlacer(4, 4, 7),
                TwoLayerFeature(0, 0, 0)
            ).heightmap(Heightmap.Type.MOTION_BLOCKING).build()
        )
    }*/

    fun addShroomyBoulders(biome: BiomeGenerationSettings.Builder) {
        biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, SHROOMY_BOULDER)
    }

    fun withAubrumFlowers(biome: BiomeGenerationSettings.Builder) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, PATCH_GOLDEN_TULIP)
    }

    fun withPurpleLumshrooms(biome: BiomeGenerationSettings.Builder) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, PATCH_PURPLE_LUMSHROOM)
    }
/*
    fun addLumlightTrees(biome: Biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LUMLIGHT_TREE_FEATURE.func_225566_b_(LUMLIGHT_TREE_CONFIG).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_225566_b_(AtSurfaceWithExtraConfig(5, 0.1f, 1))))
    }*/

    fun withSparseLumlightTrees(biome: BiomeGenerationSettings.Builder) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, SPARSE_LUMLIGHT_TREES)
    }
    /*
    fun addTallLumlightTrees(biome: BiomeGenerationSettings.Builder) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, TALL_LUMLIGHT_TREE_FEATURE.configured(LUMLIGHT_LOG_LEAVES_CONFIG).decorated(Placement.COUNT_EXTRA_HEIGHTMAP.configure(AtSurfaceWithExtraConfig(0, 0.01f, 1))))//Feature.RANDOM_SELECTOR.func_225566_b_(MultipleRandomFeatureConfig(listOf(LUMLIGHT_TREE_FEATURE.func_225566_b_(LUMLIGHT_TREE_CONFIG).withChance(0.33333334f)), Feature.NORMAL_TREE.func_225566_b_(LUMLIGHT_TREE_CONFIG))).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_225566_b_(AtSurfaceWithExtraConfig(0, 0.05f, 1))))
    }*/

    fun withLumlightShrubs(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LUMLIGHT_SHRUBS)
    }

    fun withOakShrubs(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, OAK_SHRUBS)
    }

    fun withMalachiteCrystals(generation: BiomeGenerationSettingsBuilder) {
        generation.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, MALACHITE_CRYSTAL)
    }

    fun withRainbowlandOres(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, RAINBOWSTONE_ORE)
    }

    fun withRainbowlandStructures(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.addStructureStart(HConfiguredStructures.RAINBOW_FACTORY_FEATURE)
    }

    fun withLumlightCabin(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.addStructureStart(HConfiguredStructures.LUMLIGHT_CABIN_FEATURE)
    }

    fun withSparseCandyCanes(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, SPARSE_CANDY_CANES)
    }

    fun withSparseChocolateBars(genSettings: BiomeGenerationSettings.Builder) {
        genSettings.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, SPARSE_CHOCOLATE_BARS)
    }

    private fun flowerPatchConfig(flower: Block): BlockClusterFeatureConfig {
        return BlockClusterFeatureConfig.Builder(SimpleBlockStateProvider(flower.defaultBlockState()), SimpleBlockPlacer()).tries(64).build()
    }

    fun <FC : IFeatureConfig, F : Feature<FC>> registerConfiguredFeature(name: String, feature: ConfiguredFeature<FC, F>): ConfiguredFeature<FC, F> {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, modLoc(name), feature)
    }

    fun withMushroomHut(generation: BiomeGenerationSettingsBuilder) {
        generation.addStructureStart(HConfiguredStructures.MUSHROOM_HUT_FEATURE)
    }
}