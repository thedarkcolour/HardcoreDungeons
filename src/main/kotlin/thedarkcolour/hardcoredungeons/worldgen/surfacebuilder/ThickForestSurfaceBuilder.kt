package thedarkcolour.hardcoredungeons.worldgen.surfacebuilder

import net.minecraft.block.BlockState
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig
import java.util.*

class ThickForestSurfaceBuilder : SurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilderConfig.CODEC) {
    override fun apply(rand: Random, chunk: IChunk, biomeIn: Biome, x: Int, z: Int, startHeight: Int, noise: Double, defaultBlock: BlockState, defaultFluid: BlockState, seaLevel: Int, seed: Long, config: SurfaceBuilderConfig) {
        if (noise in -1.0..2.0) {
            DEFAULT.apply(rand, chunk, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, CONFIG_GRASS)
        } else {
            DEFAULT.apply(rand, chunk, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, CONFIG_PODZOL)
        }
    }
}