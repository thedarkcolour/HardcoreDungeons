package thedarkcolour.hardcoredungeons.biomeprovider
/*
import net.minecraft.util.SharedSeedRandom
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.Biomes
import net.minecraft.world.biome.provider.BiomeProvider
import net.minecraft.world.gen.OctavesNoiseGenerator
import thedarkcolour.hardcoredungeons.biome.HBiome

class NoiseBiomeProvider(settings: NoiseBiomeProviderSettings) : BiomeProvider(settings.biomes as Set<Biome>) {
    private val temperatureNoise = OctavesNoiseGenerator(SharedSeedRandom(settings.seed), 8, -1)
    private val humidityOctaves = OctavesNoiseGenerator(SharedSeedRandom(settings.seed), 8, -1)
    private val hillinessOctaves = OctavesNoiseGenerator(SharedSeedRandom(settings.seed), 9, -1)
    private val styleOctaves = OctavesNoiseGenerator(SharedSeedRandom(settings.seed), 8, -1)
    private val hBiomes = settings.biomes

    override fun getNoiseBiome(x: Int, y: Int, z: Int): Biome {
        val f = x * 1.0181268882175227
        val g = z * 1.0181268882175227
        val h = x * 1.0
        val i = z * 1.0
        val noisePoint = HBiome.NoisePoint(
            (temperatureNoise.func_205563_a(f, 0.0, g) + temperatureNoise.func_205563_a(h, 0.0, i)).toFloat(),
            (humidityOctaves.func_205563_a(f, 0.0, g) + humidityOctaves.func_205563_a(h, 0.0, i)).toFloat(),
            (hillinessOctaves.func_205563_a(f, 0.0, g) + hillinessOctaves.func_205563_a(h, 0.0, i)).toFloat(),
            (styleOctaves.func_205563_a(f, 0.0, g) + styleOctaves.func_205563_a(h, 0.0, i)).toFloat(),
            1.0f
        )

        return this.hBiomes.stream().min(Comparator.comparing { biome: HBiome ->
            biome.getNoiseDistance(noisePoint)
        }).map { it as Biome }.orElse(Biomes.THE_END)
    }
}*/