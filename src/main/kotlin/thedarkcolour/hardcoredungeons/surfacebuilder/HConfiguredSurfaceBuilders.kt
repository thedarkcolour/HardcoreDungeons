package thedarkcolour.hardcoredungeons.surfacebuilder

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HBlocks

object HConfiguredSurfaceBuilders {
    val RAINBOWLAND_SURFACE = defaultCfg(HBlocks.RAINBOW_SOIL.grass.defaultBlockState(), HBlocks.RAINBOW_SOIL.soil.defaultBlockState(), HBlocks.RAINBOW_SOIL.loam.defaultBlockState())
    val CASTLETON_SURFACE = defaultCfg(HBlocks.CASTLETON_SOIL.grass.defaultBlockState(), HBlocks.CASTLETON_SOIL.soil.defaultBlockState(), HBlocks.CASTLETON_SOIL.loam.defaultBlockState())
    val AUBRUM_WASTELAND_SURFACE = defaultCfg(HBlocks.AURISOIL.grass.defaultBlockState(), HBlocks.AURISOIL.soil.defaultBlockState(), HBlocks.AURISOIL.loam.defaultBlockState())
    val SUGARY_SURFACE = defaultCfg(HBlocks.SUGARY_SOIL.grass.defaultBlockState(), HBlocks.SUGARY_SOIL.soil.defaultBlockState(), HBlocks.SUGARY_SOIL.loam.defaultBlockState())
    //val GOLDEN_FOREST_SURFACE = register("golden_forest_surface", defaultCfg(GOLDEN_AURIGRASS_BLOCK, GOLDEN_AURISOIL, AURILOAM))
    //val RUNED_FLATS_STONE_SURFACE = register("runed_flats_stone_surface", defaultCfg(CASTLETON_STONE, CASTLETON_STONE, CASTLETON_LOAM))
    
    fun registerConfiguredSurfaceBuilders() {
        register("rainbowland_surface", RAINBOWLAND_SURFACE)
        register("castleton_surface", CASTLETON_SURFACE)
        register("aubrum_wasteland", AUBRUM_WASTELAND_SURFACE)
        register("sugary_surface", SUGARY_SURFACE)
    }

    private fun defaultCfg(top: BlockState, soil: BlockState, underwater: BlockState): ConfiguredSurfaceBuilder<SurfaceBuilderConfig> {
        return SurfaceBuilder.DEFAULT.configured(SurfaceBuilderConfig(top, soil, underwater))
    }

    private fun <C : ISurfaceBuilderConfig> register(name: String, config: ConfiguredSurfaceBuilder<C>): ConfiguredSurfaceBuilder<C> {
        return Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, ResourceLocation(HardcoreDungeons.ID, name), config)
    }
}