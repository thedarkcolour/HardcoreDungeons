package thedarkcolour.hardcoredungeons.dimension.rainbowland

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.Dimension
import net.minecraft.world.DimensionType
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HBiomes
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HChunkGenerators
import thedarkcolour.hardcoredungeons.registry.HDimensions

class RainbowlandDimension(world: World, type: DimensionType) : Dimension(world, type, 0.0f) {
    override fun findSpawn(chunkPosIn: ChunkPos, checkValid: Boolean): BlockPos? = null
    override fun findSpawn(posX: Int, posZ: Int, checkValid: Boolean): BlockPos? = null
    override fun createChunkGenerator() = HChunkGenerators.fixedChunkGen(world, HBiomes.RAINBOW_PLAINS, HBlocks.RAINBOW_ROCK.defaultState)
    override fun calculateCelestialAngle(worldTime: Long, partialTicks: Float) = 0.0f
    override fun getFogColor(celestialAngle: Float, partialTicks: Float) = FOG_COLOR
    override fun doesXZShowFog(x: Int, z: Int) = false
    override fun isSurfaceWorld() = false
    override fun canRespawnHere() = false

    override fun isSkyColored() = true

    companion object {
        private val FOG_COLOR = HDimensions.color(color = 0xcfb3ff)
    }
}