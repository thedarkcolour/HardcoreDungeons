package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.levelgen.GenerationStep.Carving
import thedarkcolour.hardcoredungeons.data.WorldGenProvider
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.worldgen.HFeatures

object CastletonHillsBiome : ModBiome() {
    override fun configure(info: WorldGenProvider.BiomeConfiguration) {
        info.climateSettings.apply {
            setHasPrecipitation(false)
            temperature = 1.5f
            downfall = 0.0f
        }
        info.specialEffects.apply {
            defaultCastletonEffects(this)
        }
        info.generationSettings.apply {
            addCarver(Carving.AIR, HFeatures.CASTLETON_CAVES)
            //HWorldGen.withPurpleLumshrooms(this)
        }
        info.mobSpawnSettings.apply {
            addSpawn(MobCategory.CREATURE, HEntities.CASTLETON_DEER, 10, 3, 8)
        }

        //FeatureUtils.simpleRandomPatchConfiguration()
        //val blueLumshrooms = Feature.RANDOM_PATCH.configured(BlockClusterFeatureConfig.Builder(SimpleBlockStateProvider(HBlocks.BLUE_LUMSHROOM.plant.defaultBlockState()), SimpleBlockPlacer()).tries(64).build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(8)
        //val ore = Feature.ORE.configured(OreFeatureConfig(BlockMatchRuleTest(HBlocks.CASTLETON_STONE.stone.block), HBlocks.RUNED_CASTLETON_STONE.defaultBlockState(), 7)).decorated(Placement.DEPTH_AVERAGE.configured(
        //    DepthAverageConfig(24, 12)
        //))

        //generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, blueLumshrooms)
        //generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore)
        //generation.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER)
        //generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.TREE.configured(
        //    BaseTreeFeatureConfig.Builder(
        //        SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.log.defaultBlockState()),
        //        SimpleBlockStateProvider(HBlocks.LUMLIGHT_WOOD.leaves.defaultBlockState()),
        //        FancyFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(3), 4),
        //        FancyTrunkPlacer(9, 5, 0),
        //        TwoLayerFeature(0, 3, 0)
        //    ).build()).decorated(Placement.HEIGHTMAP.configured(NoPlacementConfig.NONE)).squared()
        //)
    }
}
