package thedarkcolour.hardcoredungeons.biome.rainbowland

/*
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.placement.IPlacementConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import thedarkcolour.hardcoredungeons.biome.HBiome
import thedarkcolour.hardcoredungeons.registry.HFeatures
import thedarkcolour.hardcoredungeons.registry.HStructures
import thedarkcolour.hardcoredungeons.registry.HSurfaceBuilders

class RainbowPlainsBiome : HBiome(
    Properties()
        .category(Category.PLAINS)
        .surfaceBuilder(SurfaceBuilder.DEFAULT, HSurfaceBuilders.RAINBOW_GRASS_SOIL_LOAM)
        .depth(0.1F)
        .precipitation(RainType.NONE)
        .downfall(0.0F)
        .scale(0.2F)
        .waterColor(5334527)
        .waterFogColor(2045938)
        .temperature(1.5F)
) {
    init {
        // rainbow factory
        addStructure(HStructures.RAINBOW_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG))
        addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, HStructures.RAINBOW_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
            .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)))

        // rainbowstone ore
        HFeatures.addRainbowlandOres(this)
    }

    override fun getWaterColor() = 0x5008b2
    override fun getWaterFogColor() = 0x621ff2
}*/