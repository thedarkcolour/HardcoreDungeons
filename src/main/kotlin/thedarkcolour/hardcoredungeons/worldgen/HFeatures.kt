package thedarkcolour.hardcoredungeons.worldgen

import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement
import net.minecraft.world.level.levelgen.placement.InSquarePlacement
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraft.world.level.levelgen.placement.PlacementModifier
import net.minecraft.world.level.levelgen.placement.RarityFilter
import team.rusty.util.feature.FeatureRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.legacy.ObjectHolderDelegate
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.worldgen.feature.*
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import kotlin.reflect.KProperty0

// @formatter:off
@Suppress("MemberVisibilityCanBePrivate")
object HFeatures {
    // HRegistry stuff
    private val registry = FeatureRegistry(HardcoreDungeons.ID)

    fun init() {
        registry.register(MOD_BUS)
    }

    private fun <F : Feature<FC>, FC : FeatureConfiguration> feature(name: String, supplier: () -> F): ObjectHolderDelegate<F> {
        val obj = registry.feature(name, supplier)
        return ObjectHolderDelegate<F>(obj)
    }

    private fun <F : Feature<FC>, FC : FeatureConfiguration> configured(name: String, supplier: () -> ConfiguredFeature<FC, F>): ObjectHolderDelegate<ConfiguredFeature<FC, F>> {
        val obj = registry.configuredFeature(name, supplier)
        return ObjectHolderDelegate<ConfiguredFeature<FC, F>>(obj)
    }

    private inline fun <F : Feature<FC>, FC : FeatureConfiguration> configured(name: String, crossinline feature: () -> F, crossinline config: () -> FC): ObjectHolderDelegate<ConfiguredFeature<FC, F>> {
        return configured(name) {
            ConfiguredFeature(feature(), config())
        }
    }

    private fun placed(name: String, supplier: () -> PlacedFeature): ObjectHolderDelegate<PlacedFeature> {
        val obj = registry.placedFeature(name, supplier)
        return ObjectHolderDelegate(obj)
    }

    @Suppress("UNCHECKED_CAST")
    private fun placed(name: String, configuredFeature: KProperty0<ConfiguredFeature<*, *>>, placements: List<PlacementModifier>): ObjectHolderDelegate<PlacedFeature> {
        return placed(name) {
            PlacedFeature((configuredFeature.getDelegate() as ObjectHolderDelegate<ConfiguredFeature<*, *>>).registryObject.holder.get(), placements)
        }
    }

    //
    // FEATURES
    //

    //val CRYSTAL_FEATURE by feature("crystal_feature") { CrystalFeature(CrystalFeatureConfig.CODEC) }
    val CANDY_CANE_FEATURE by feature("candy_cane_feature") { CandyCaneFeature() }
    val CHOCOLATE_BAR_FEATURE by feature("chocolate_bar_feature") { ChocolateBarFeature() }

    //
    // CONFIGURED FEATURES
    //

    val MALACHITE_CRYSTAL by configured("malachite_crystal", {Feature.SIMPLE_BLOCK}) {
        SimpleBlockConfiguration(BlockStateProvider.simple(HBlocks.MALACHITE_CRYSTAL.crystal.defaultBlockState()))
    }
    val CANDY_CANE by configured("candy_cane", {CANDY_CANE_FEATURE}, NoneFeatureConfiguration::NONE)
    val CHOCOLATE_BAR by configured("chocolate_bar", {CHOCOLATE_BAR_FEATURE}, NoneFeatureConfiguration::NONE)

    //
    // PLACED FEATURES
    //

    val UNDERGROUND_MALACHITE_CRYSTALS by placed("underground_malachite_crystals", ::MALACHITE_CRYSTAL, listOf(
        InSquarePlacement.spread(),
        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
        RarityFilter.onAverageOnceEvery(10),
        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(5), VerticalAnchor.aboveBottom(60)),
        BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
    ))
    val SPARSE_CANDY_CANES by placed("sparse_candy_canes", ::CANDY_CANE, listOf(InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), PlacementUtils.countExtra(0, 0.15f, 1)))
    val SPARSE_CHOCOLATE_BARS by placed("sparse_chocolate_bars", ::CHOCOLATE_BAR, listOf(InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), PlacementUtils.countExtra(0, 0.05f, 1)))
}