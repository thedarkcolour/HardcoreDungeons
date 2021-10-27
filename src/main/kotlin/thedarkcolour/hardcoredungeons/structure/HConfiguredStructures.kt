package thedarkcolour.hardcoredungeons.structure

import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.StructureFeature
import net.minecraft.world.gen.feature.structure.Structure
import thedarkcolour.hardcoredungeons.registry.HStructures
import thedarkcolour.hardcoredungeons.util.modLoc

@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
object HConfiguredStructures {
    val MUSHROOM_HUT_FEATURE = HStructures.MUSHROOM_HUT.configured(IFeatureConfig.NONE)
    val LUMLIGHT_CABIN_FEATURE = HStructures.LUMLIGHT_CABIN.configured(IFeatureConfig.NONE)
    val RAINBOW_FACTORY_FEATURE = HStructures.RAINBOW_FACTORY.configured(IFeatureConfig.NONE)

    fun registerConfiguredStructures() {
        register("mushroom_hut_feature", MUSHROOM_HUT_FEATURE)
        register("lumlight_cabin_feature", LUMLIGHT_CABIN_FEATURE)
        register("rainbow_factory_feature", RAINBOW_FACTORY_FEATURE)
    }

    fun <FC : IFeatureConfig, F : Structure<FC>> register(name: String, feature: StructureFeature<FC, F>): StructureFeature<FC, F> {
        //FlatGenerationSettings.STRUCTURE_FEATURES[feature.feature] = feature
        return Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, modLoc(name), feature)
    }
}