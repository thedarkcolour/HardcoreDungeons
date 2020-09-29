package thedarkcolour.hardcoredungeons.dimension.castleton

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.ChunkGenerator
import thedarkcolour.hardcoredungeons.dimension.HDimension
import thedarkcolour.hardcoredungeons.registry.*

class CastletonDimension(world: World, dimensionType: DimensionType) : HDimension(world, dimensionType, 0.04f) {
    override fun findSpawn(chunkPosIn: ChunkPos, checkValid: Boolean): BlockPos? = null
    override fun findSpawn(posX: Int, posZ: Int, checkValid: Boolean): BlockPos? = null
    override fun calculateCelestialAngle(worldTime: Long, partialTicks: Float) = 0.0f
    override fun getFogColor(celestialAngle: Float, partialTicks: Float) = FOG_COLOR
    override fun isSurfaceWorld() = false
    override fun canRespawnHere() = true
    override fun doesXZShowFog(x: Int, z: Int) = false
    override fun hasSkyLight() = false
    override fun getSeaLevel() = 31

    override fun createChunkGenerator(): ChunkGenerator<*> {
        return HChunkGenerators.CASTLETON.create(world,
            HBiomeProviders.noiseProvider(world.worldInfo) { settings ->
                settings.biomes.add(HBiomes.KNIGHTLY_SHRUBLAND)
                settings.biomes.add(HBiomes.CASTLETON_HILLS)
                //settings.biomes.add(HBiomes.RUNED_FLATS)
            },
            HChunkGenerators.chunkGenSettings(HChunkGenerators.CASTLETON) { settings ->
                settings.defaultBlock = HBlocks.CASTLETON_STONE.defaultState
            }
        )
    }

    companion object {
        private val FOG_COLOR = HDimensions.color(17, 61, 81)
        // 0x113d51
    }
}