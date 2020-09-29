package thedarkcolour.hardcoredungeons.registry

import net.minecraft.block.Blocks
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig
import net.minecraftforge.registries.IForgeRegistry
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
    val GOLDEN_AURIGRASS_BLOCK = HBlocks.GOLDEN_AURIGRASS_BLOCK.defaultState
    val GOLDEN_AURISOIL = HBlocks.GOLDEN_AURISOIL.defaultState

    val RAINBOW_GRASS_SOIL_LOAM = SurfaceBuilderConfig(RAINBOW_GRASS_BLOCK, RAINBOW_SOIL, GRAVEL)
    val CASTLETON_GRASS_SOIL_LOAM = SurfaceBuilderConfig(CASTLETON_GRASS_BLOCK, CASTLETON_SOIL, CASTLETON_LOAM)
    val AUBRUM_WASTELAND_SURFACE = SurfaceBuilderConfig(AURIGRASS_BLOCK, AURISOIL, AURILOAM)
    val GOLDEN_FOREST_SURFACE = SurfaceBuilderConfig(GOLDEN_AURIGRASS_BLOCK, GOLDEN_AURISOIL, AURILOAM)
    val RUNED_FLATS_STONE_SURFACE = SurfaceBuilderConfig(CASTLETON_STONE, CASTLETON_STONE, CASTLETON_LOAM)

    val THICK_FOREST_SURFACE = ThickForestSurfaceBuilder().setRegistryKey("forest_of_lakes")

    fun registerSurfaceBuilders(surfaces: IForgeRegistry<SurfaceBuilder<*>>) {
        surfaces.register(THICK_FOREST_SURFACE)
    }
}