package thedarkcolour.hardcoredungeons.dimension.aubrum

// possibly a gold themed dimension?
/*
class AubrumDimension(world: World, dimensionType: DimensionType) : HDimension(world, dimensionType, 0.04f) {
    override fun findSpawn(chunkPosIn: ChunkPos, checkValid: Boolean): BlockPos? = null
    override fun findSpawn(posX: Int, posZ: Int, checkValid: Boolean): BlockPos? = null
    override fun calculateCelestialAngle(worldTime: Long, partialTicks: Float) = 0.0f
    override fun getFogColor(celestialAngle: Float, partialTicks: Float) = FOG_COLOR
    override fun isSurfaceWorld() = false
    override fun canRespawnHere() = false
    override fun doesXZShowFog(x: Int, z: Int) = false
    override fun hasSkyLight() = false
    override fun getSeaLevel() = 31 // TODO Make custom fluid, maybe opaque one that applies poison but weaker?

    override fun createChunkGenerator(): ChunkGenerator<*> {
        return HChunkGenerators.AUBRUM.create(world,
            HBiomeProviders.noiseProvider(world.worldInfo) { settings ->
                settings.biomes.add(HBiomes.AUBRUM_WASTELAND)
                settings.biomes.add(HBiomes.GOLDEN_FOREST)
                settings.biomes.add(HBiomes.AUBRUM_MOUNTAINS)
                settings.biomes.add(HBiomes.AURI_PLAINS)
            },
            HChunkGenerators.chunkGenSettings(HChunkGenerators.AUBRUM) { settings ->
                settings.defaultBlock = HBlocks.AUBRUM_ROCK.defaultState
            }
        )
    }

    companion object {
        private val FOG_COLOR = HDimensions.color(171, 149, 96)
    }
}


/*class AubrumDimension(world: World, dimensionType: DimensionType) : HDimension(world, dimensionType, 0.04f) {
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
        return HChunkGenerators.AUBRUM.create(world, HBiomeProviders.AUBRUM.create(HBiomeProviders.AUBRUM.getConfig(world.worldInfo)), run {
            val settings = HChunkGenerators.AUBRUM.createSettings()
            settings.defaultBlock = HBlocks.CASTLETON_STONE.defaultState
            settings
        })
    }

    companion object {
        private val FOG_COLOR = HDimensions.color(171, 149, 96)
    }
}*/