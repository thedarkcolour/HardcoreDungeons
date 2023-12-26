package thedarkcolour.hardcoredungeons.data

import com.mojang.datafixers.util.Pair
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformFloat
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Climate
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.dimension.BuiltinDimensionTypes
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight
import net.minecraft.world.level.levelgen.placement.*
import thedarkcolour.hardcoredungeons.registry.HBiomes
import thedarkcolour.hardcoredungeons.registry.HDimensions
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.tags.HBlockTags
import thedarkcolour.hardcoredungeons.worldgen.HFeatures
import thedarkcolour.hardcoredungeons.worldgen.biome.*
import java.util.*

fun addFeatures(provider: WorldGenProvider) {
    provider.apply {
        configuredFeature(HFeatures.MALACHITE_CRYSTAL, Feature.SIMPLE_BLOCK, SimpleBlockConfiguration(BlockStateProvider.simple(HBlocks.MALACHITE_CRYSTAL.crystal.defaultBlockState())))
        configuredFeature(HFeatures.FANCY_LUMLIGHT_TREE, Feature.TREE, TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(HBlocks.LUMLIGHT_WOOD.log),
            FancyTrunkPlacer(4, 11, 0),
            BlockStateProvider.simple(HBlocks.LUMLIGHT_WOOD.leaves),
            FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5))
        ).build())
        configuredFeature(HFeatures.CANDY_CANE_CONFIGURED, HFeatures.CANDY_CANE.get(), NoneFeatureConfiguration.NONE)
        configuredFeature(HFeatures.CHOCOLATE_BAR_CONFIGURED, HFeatures.CHOCOLATE_BAR.get(), NoneFeatureConfiguration.NONE)

        placedFeature(HFeatures.UNDERGROUND_MALACHITE_CRYSTALS, HFeatures.MALACHITE_CRYSTAL, listOf(
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
            RarityFilter.onAverageOnceEvery(10),
            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(5), VerticalAnchor.aboveBottom(60)),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        ))
        placedFeature(HFeatures.SPARSE_CANDY_CANES, HFeatures.CANDY_CANE_CONFIGURED, listOf(InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), PlacementUtils.countExtra(0, 0.125f, 1)))
        placedFeature(HFeatures.SPARSE_CHOCOLATE_BARS, HFeatures.CHOCOLATE_BAR_CONFIGURED, listOf(InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), PlacementUtils.countExtra(0, 0.05f, 1)))

        configuredCarver(HFeatures.CASTLETON_CAVES, HFeatures.CASTLETON_CAVE_CARVER.get(), CaveCarverConfiguration(
            0.15f,
            UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)),
            UniformFloat.of(0.1f, 0.9f),
            VerticalAnchor.aboveBottom(8),
            CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()),
            blockTag(HBlockTags.CASTLETON_CARVER_REPLACEABLES),
            UniformFloat.of(0.7f, 1.4f),
            UniformFloat.of(0.8f, 1.3f),
            UniformFloat.of(-1.0f, -0.4f)
        ))

        biome(HBiomes.MUSHROOM_CLIFFS, MushroomCliffsBiome)
        biome(HBiomes.AUBRUM_WASTELAND, AubrumWastelandBiome)
        biome(HBiomes.GOLDEN_FOREST, GoldenForestBiome)
        biome(HBiomes.AUBRUM_MOUNTAINS, AubrumMountainsBiome)
        biome(HBiomes.AURI_PLAINS, AuriPlainsBiome)
        biome(HBiomes.CASTLETON_HILLS, CastletonHillsBiome)
        biome(HBiomes.KNIGHTLY_SHRUBLAND, KnightlyShrublandBiome)
        biome(HBiomes.RAINBOWLAND, RainbowPlainsBiome)
        biome(HBiomes.GUMDROP_FIELDS, GumdropFieldsBiome)
        biome(HBiomes.CANDY_PLAINS, CandyPlainsBiome)
    }
}

fun addDimensionsAndBiomes(provider: DimensionProvider) {
    provider.apply {
        dimensionType(HDimensions.CANDYLAND_ID, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.04f, hasSkyLight = true)
        dimension(HDimensions.CANDYLAND_ID, listOf(
            biome(HBiomes.GUMDROP_FIELDS, 0.7f, 0.3f, 0.3f, 0.2f, 0.1f, 0.2f, 0.0f),
            biome(HBiomes.CANDY_PLAINS, 0.4f, 0.6f, 0.1f, 0.2f, 0.3f, 0.2f, 0.0f)
        ))

        dimensionType(HDimensions.AUBRUM_ID, HDimensions.AUBRUM_ID, 0.04f, fixedTime = 6000L)
        dimension(HDimensions.AUBRUM_ID, listOf(
            biome(HBiomes.AUBRUM_MOUNTAINS, 0.0f, 0.2f, 0.3f, 0.2f, 0.3f, 0.5f, 0.0f),
            biome(HBiomes.AUBRUM_WASTELAND, 0.0f, 1.0f, 0.1f, 0.2f, 0.7f, 0.5f, 0.0f),
            biome(HBiomes.AURI_PLAINS, 0.1f, 0.6f, 0.2f, 0.1f, 0.3f, 0.2f, 0.0f),
            biome(HBiomes.GOLDEN_FOREST, 0.4f, 1.5f, 0.5f, 0.4f, 0.3f, 0.8f, 0.2f),
        ))

        dimensionType(HDimensions.CASTLETON_ID, HDimensions.CASTLETON_ID, 0.04f)
        dimension(HDimensions.CASTLETON_ID, listOf(
            biome(HBiomes.KNIGHTLY_SHRUBLAND, 0.0f, 0.6f, 0.1f, 0.2f, 0.3f, 0.5f, 0.0f),
            biome(HBiomes.CASTLETON_HILLS, 0.0f, 0.6f, 0.1f, 0.3f, 0.6f, 0.2f, 0.0f),
        ))

        dimensionType(HDimensions.RAINBOWLAND_ID, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.04f, hasSkyLight = true)
        dimension(HDimensions.RAINBOWLAND_ID, HBiomes.RAINBOWLAND)
    }
}

private fun DimensionProvider.dimensionType(id: ResourceLocation, effectsId: ResourceLocation, ambientLight: Float, fixedTime: Long? = null, hasSkyLight: Boolean = false, hasCeiling: Boolean = false, ultraWarm: Boolean = false) {
    val optionalFixedTime = if (fixedTime == null) {
        OptionalLong.empty()
    } else {
        OptionalLong.of(fixedTime)
    }
    dimensionType(id, DimensionType(optionalFixedTime, hasSkyLight, hasCeiling, ultraWarm, false, 1.0, false, true, 0, 256, 256, BlockTags.INFINIBURN_OVERWORLD, effectsId, ambientLight, DimensionType.MonsterSettings(false, false, ConstantInt.of(7), 0)))
}

@Suppress("DEPRECATION")
private fun blockTag(tag: TagKey<Block>): HolderSet<Block> {
    return BuiltInRegistries.BLOCK.getOrCreateTag(tag)
}

private fun DimensionProvider.biome(id: ResourceKey<Biome>, temperature: Float, humidity: Float, continentalness: Float, erosion: Float, depth: Float, weirdness: Float, offset: Float): Pair<Climate.ParameterPoint, Holder<Biome>> {
    return Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, offset), Holder.Reference.createStandAlone(holderLookup.lookupOrThrow(Registries.BIOME), id))
}