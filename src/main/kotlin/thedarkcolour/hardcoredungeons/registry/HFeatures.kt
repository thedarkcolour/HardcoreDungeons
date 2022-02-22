package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.worldgen.feature.CandyCaneFeature
import thedarkcolour.hardcoredungeons.worldgen.feature.ChocolateBarFeature
import thedarkcolour.hardcoredungeons.worldgen.feature.CrystalFeature
import thedarkcolour.hardcoredungeons.worldgen.feature.CrystalFeatureConfig

// @formatter:off
@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType")
object HFeatures {
    //
    // FEATURES
    //

    // Overworld
    val CRYSTAL_FEATURE = CrystalFeature(CrystalFeatureConfig.CODEC).setRegistryKey("crystal_feature")

    // Candyland
    val CANDY_CANE_FEATURE = CandyCaneFeature(NoFeatureConfig.CODEC).setRegistryKey("candy_cane_feature")
    val CHOCOLATE_BAR_FEATURE = ChocolateBarFeature(NoFeatureConfig.CODEC).setRegistryKey("chocolate_bar_feature")

    fun registerFeatures(registry: IForgeRegistry<Feature<*>>) {
        registry.register(CANDY_CANE_FEATURE)
        registry.register(CHOCOLATE_BAR_FEATURE)
        registry.register(CRYSTAL_FEATURE)
    }
}