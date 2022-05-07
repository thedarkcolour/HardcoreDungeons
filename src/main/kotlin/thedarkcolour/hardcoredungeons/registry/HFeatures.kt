package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.gen.feature.BlockStateFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.worldgen.feature.*

// @formatter:off
@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType")
object HFeatures : HRegistry<Feature<*>>(ForgeRegistries.FEATURES) {
    //
    // FEATURES
    //

    // Overworld
    val CRYSTAL_FEATURE by register("crystal_feature") { CrystalFeature(CrystalFeatureConfig.CODEC) }
    val HANGING_LANTERN_FEATURE by register("hanging_lantern_feature") { HangingLanternFeature(BlockStateFeatureConfig.CODEC) }

    // Candyland
    val CANDY_CANE_FEATURE by register("candy_cane_feature") { CandyCaneFeature(NoFeatureConfig.CODEC) }
    val CHOCOLATE_BAR_FEATURE by register("chocolate_bar_feature") { ChocolateBarFeature(NoFeatureConfig.CODEC) }
}