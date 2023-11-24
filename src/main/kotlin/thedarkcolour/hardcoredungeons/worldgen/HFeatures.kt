package thedarkcolour.hardcoredungeons.worldgen

import net.minecraft.core.Holder
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer
import net.minecraft.world.level.levelgen.placement.*
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import team.rusty.util.feature.FeatureRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.worldgen.feature.CandyCaneFeature
import thedarkcolour.hardcoredungeons.worldgen.feature.ChocolateBarFeature
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import java.util.*

// @formatter:off
@Suppress("MemberVisibilityCanBePrivate")
object HFeatures {
    // HRegistry stuff
    private val registry = FeatureRegistry(HardcoreDungeons.ID)

    fun init() {
        registry.register(MOD_BUS)

        registerGlobalModifier()
    }

    // global biome modifier stuff for malachite crystals etc.
    private fun registerGlobalModifier() {
        val serializer = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, HardcoreDungeons.ID)
        val modifier = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIERS, HardcoreDungeons.ID)
        serializer.register(MOD_BUS)
        modifier.register(MOD_BUS)
        serializer.register("global") { GlobalBiomeModifier.codec }
        modifier.register("global") { GlobalBiomeModifier }
    }

    private fun <F : Feature<FC>, FC : FeatureConfiguration> feature(name: String, supplier: () -> F): ObjectHolderDelegate<F> {
        val obj = registry.feature(name, supplier)
        return ObjectHolderDelegate<F>(obj)
    }

    private fun <F : Feature<FC>, FC : FeatureConfiguration> configured(name: String, supplier: () -> ConfiguredFeature<FC, F>): Holder<ConfiguredFeature<*, *>> {
        return registry.configuredFeature(name, supplier)
    }

    private inline fun <F : Feature<FC>, FC : FeatureConfiguration> configured(name: String, crossinline feature: () -> F, crossinline config: () -> FC): Holder<ConfiguredFeature<*, *>> {
        return configured(name) {
            ConfiguredFeature(feature(), config())
        }
    }

    private fun placed(name: String, supplier: () -> PlacedFeature): Holder<PlacedFeature> {
        return registry.placedFeature(name, supplier)
    }

    private fun placed(name: String, configuredFeature: Holder<ConfiguredFeature<*, *>>, placements: List<PlacementModifier>): Holder<PlacedFeature> {
        return placed(name) {
            PlacedFeature(configuredFeature, placements)
        }
    }

    //
    // FEATURES
    //

    //val CRYSTAL_FEATURE by feature("crystal_feature") { CrystalFeature(CrystalFeatureConfig.CODEC) }
    val CANDY_CANE_FEATURE by feature("candy_cane_feature", ::CandyCaneFeature)
    val CHOCOLATE_BAR_FEATURE by feature("chocolate_bar_feature", ::ChocolateBarFeature)

    //
    // CONFIGURED FEATURES
    //

    val MALACHITE_CRYSTAL = configured("malachite_crystal", {Feature.SIMPLE_BLOCK}) {
        SimpleBlockConfiguration(BlockStateProvider.simple(HBlocks.MALACHITE_CRYSTAL.crystal.defaultBlockState()))
    }
    val FANCY_LUMLIGHT_TREE = configured("fancy_lumlight_tree", {Feature.TREE}) {
        TreeConfigurationBuilder(
            BlockStateProvider.simple(HBlocks.LUMLIGHT_WOOD.log),
            FancyTrunkPlacer(4, 11, 0),
            BlockStateProvider.simple(HBlocks.LUMLIGHT_WOOD.leaves),
            FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(5))
        ).build()
    }
    val CANDY_CANE = configured("candy_cane", {CANDY_CANE_FEATURE}, NoneFeatureConfiguration::NONE)
    val CHOCOLATE_BAR = configured("chocolate_bar", {CHOCOLATE_BAR_FEATURE}, NoneFeatureConfiguration::NONE)

    //
    // PLACED FEATURES
    //

    val UNDERGROUND_MALACHITE_CRYSTALS = placed("underground_malachite_crystals", MALACHITE_CRYSTAL, listOf(
        InSquarePlacement.spread(),
        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
        RarityFilter.onAverageOnceEvery(10),
        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(5), VerticalAnchor.aboveBottom(60)),
        BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
    ))
    val SPARSE_CANDY_CANES = placed("sparse_candy_canes", CANDY_CANE, listOf(InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), PlacementUtils.countExtra(0, 0.125f, 1)))
    val SPARSE_CHOCOLATE_BARS = placed("sparse_chocolate_bars", CHOCOLATE_BAR, listOf(InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), PlacementUtils.countExtra(0, 0.05f, 1)))
}