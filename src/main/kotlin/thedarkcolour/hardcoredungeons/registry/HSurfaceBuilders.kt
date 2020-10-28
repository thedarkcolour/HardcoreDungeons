package thedarkcolour.hardcoredungeons.registry

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.surfacebuilder.ThickForestSurfaceBuilder

@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
object HSurfaceBuilders {
    val RAINBOW_GRASS_BLOCK = HBlocks.RAINBOW_GRASS_BLOCK.defaultState
    val RAINBOW_SOIL = HBlocks.RAINBOW_SOIL.defaultState

    val CASTLETON_GRASS_BLOCK = HBlocks.CASTLETON_GRASS_BLOCK.defaultState
    val CASTLETON_SOIL = HBlocks.CASTLETON_SOIL.defaultState
    val CASTLETON_LOAM = HBlocks.CASTLETON_LOAM.defaultState
    val CASTLETON_STONE = HBlocks.CASTLETON_STONE.defaultState

    val GRAVEL = Blocks.GRAVEL.defaultState

    val AURIGRASS_BLOCK = HBlocks.AURIGRASS_BLOCK.defaultState
    val AURISOIL = HBlocks.AURISOIL.defaultState
    val AURILOAM = HBlocks.AURILOAM.defaultState
    //val GOLDEN_AURIGRASS_BLOCK = HBlocks.GOLDEN_AURIGRASS_BLOCK.defaultState
    //val GOLDEN_AURISOIL = HBlocks.GOLDEN_AURISOIL.defaultState

    val RAINBOWLAND_SURFACE = register("rainbowland_surface", defaultCfg(RAINBOW_GRASS_BLOCK, RAINBOW_SOIL, GRAVEL))
    val CASTLETON_SURFACE = register("castleton_surface", defaultCfg(CASTLETON_GRASS_BLOCK, CASTLETON_SOIL, CASTLETON_LOAM))
    val AUBRUM_WASTELAND_SURFACE = register("aubrum_wasteland", defaultCfg(AURIGRASS_BLOCK, AURISOIL, AURILOAM))
    //val GOLDEN_FOREST_SURFACE = register("golden_forest_surface", defaultCfg(GOLDEN_AURIGRASS_BLOCK, GOLDEN_AURISOIL, AURILOAM))
    //val RUNED_FLATS_STONE_SURFACE = register("runed_flats_stone_surface", defaultCfg(CASTLETON_STONE, CASTLETON_STONE, CASTLETON_LOAM))

    val THICK_FOREST_SURFACE = ThickForestSurfaceBuilder().setRegistryKey("forest_of_lakes")

    fun registerSurfaceBuilders(surfaces: IForgeRegistry<SurfaceBuilder<*>>) {
        surfaces.register(THICK_FOREST_SURFACE)
    }

    private fun defaultCfg(a: BlockState, b: BlockState, c: BlockState): ConfiguredSurfaceBuilder<SurfaceBuilderConfig> {
        return SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilderConfig(a, b, c))
    }

    private fun <C : ISurfaceBuilderConfig> register(name: String, config: ConfiguredSurfaceBuilder<C>): ConfiguredSurfaceBuilder<C> {
        return Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, ResourceLocation(HardcoreDungeons.ID, name), config)
    }
}