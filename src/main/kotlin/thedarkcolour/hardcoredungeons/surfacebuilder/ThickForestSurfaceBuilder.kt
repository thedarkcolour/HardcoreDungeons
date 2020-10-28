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
        /*

    "noise": {
        "random_density_offset": true,
        "density_factor": 0.001,
        "density_offset": -0.46875,
        "simplex_surface_noise": true,
        "amplified": true,
        "bottom_slide": {
            "target": -30,
            "size": 0,
            "offset": 0
        },
        "size_horizontal": 4,
        "size_vertical": 1,
        "height": 256,
        "sampling": {
            "xz_scale": 1,
            "y_scale": 1,
            "xz_factor": 80.0,
            "y_factor": 160.0
        },
        "top_slide": {
            "target": -10,
            "size": 3,
            "offset": 0
        }
    },
         */
    }
}