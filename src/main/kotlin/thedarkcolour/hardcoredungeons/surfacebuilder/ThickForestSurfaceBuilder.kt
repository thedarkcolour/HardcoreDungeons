package thedarkcolour.hardcoredungeons.surfacebuilder

import net.minecraft.block.BlockState
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig
import java.util.*

class ThickForestSurfaceBuilder : SurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilderConfig.field_237203_a_) {
    override fun buildSurface(rand: Random, chunk: IChunk, biomeIn: Biome, x: Int, z: Int, startHeight: Int, noise: Double, defaultBlock: BlockState, defaultFluid: BlockState, seaLevel: Int, seed: Long, config: SurfaceBuilderConfig) {
        if (noise in -1.0..2.0) {
            DEFAULT.buildSurface(rand, chunk, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, GRASS_DIRT_GRAVEL_CONFIG)
        } else {
            DEFAULT.buildSurface(rand, chunk, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, PODZOL_DIRT_GRAVEL_CONFIG)
        }
    }
}