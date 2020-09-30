package thedarkcolour.hardcoredungeons.registry
/*
import net.minecraft.block.BlockState
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeGenerationSettings
/*import net.minecraft.world.biome.provider.BiomeProviderType
import net.minecraft.world.gen.ChunkGeneratorType
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.OverworldChunkGenerator
import net.minecraft.world.gen.OverworldGenSettings*/
import net.minecraftforge.registries.IForgeRegistry

object HChunkGenerators {
    val CASTLETON = ChunkGeneratorType(::CastletonChunkGenerator, false, ::OverworldGenSettings).setRegistryKey("castleton")
    val AUBRUM = ChunkGeneratorType(::AubrumChunkGenerator, false, ::OverworldGenSettings).setRegistryKey("aubrum")

    fun register(generators: IForgeRegistry<ChunkGeneratorType<*, *>>) {
        generators.register(CASTLETON)
        generators.register(AUBRUM)
    }

    /**
     * Makes a [OverworldChunkGenerator] with a single [Biome]
     *
     * @param biome used to generate all chunks
     * @param stoneBlock stone block that was used until Castleton split off into a separate chunk generator
     *
     * @see thedarkcolour.hardcoredungeons.dimension.rainbowland.RainbowlandDimension
     */
    fun fixedChunkGen(worldIn: World, biome: Biome, stoneBlock: BlockState): OverworldChunkGenerator {
        val settings = ChunkGeneratorType.SURFACE.createSettings()
        settings.defaultBlock = stoneBlock

        return ChunkGeneratorType.SURFACE.create(worldIn, BiomeProviderType.FIXED.create(BiomeProviderType.FIXED.createSettings(worldIn.worldInfo).setBiome(biome)), settings)
    }

    fun <T : GenerationSettings> chunkGenSettings(chunkGenType: ChunkGeneratorType<T, *>, settings: (T) -> Unit): T {
        val t = chunkGenType.createSettings()
        settings(t)
        return t
    }
}*/