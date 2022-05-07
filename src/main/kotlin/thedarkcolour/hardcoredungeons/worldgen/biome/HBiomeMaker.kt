package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.world.biome.*
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.carver.ConfiguredCarvers
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import thedarkcolour.hardcoredungeons.worldgen.feature.HWorldGen
import thedarkcolour.hardcoredungeons.worldgen.surfacebuilder.HConfiguredSurfaceBuilders

object HBiomeMaker {
    val DEFAULT_CASTLETON_EFFECTS = biomeFx(0x6fa3bf, 0x1f37f2, 0x113d51, 0x1d5a75)

    fun makeCastletonForest(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.CASTLETON_SURFACE)
        genSettings.addCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.CAVE)
        genSettings.addCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.CANYON)

        HWorldGen.withPurpleLumshrooms(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            Biome.Category.FOREST,
            depth = 0.1f,
            scale = 0.3f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = DEFAULT_CASTLETON_EFFECTS,
            genSettings = genSettings
        )
    }

    fun makeCastletonHillsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.CASTLETON_SURFACE)
        genSettings.addCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.CAVE)

        HWorldGen.withSparseLumlightTrees(genSettings)
        HWorldGen.withPurpleLumshrooms(genSettings)
        DefaultBiomeFeatures.addBlueIce(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.PLAINS,
            depth = 1.5f,
            scale = 0.07f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = DEFAULT_CASTLETON_EFFECTS,
            genSettings = genSettings
        )
    }

    fun makeGumdropFieldsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.SUGARY_SURFACE)
        val spawnSettings = spawnSettings()

        HWorldGen.withSparseCandyCanes(genSettings)
        HWorldGen.withSparseChocolateBars(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.PLAINS,
            depth = 0.04f,
            scale = 0.2f,
            temperature = 1.5f,
            downfall = 0.4f,
            effects = biomeFx(0xd19bdc, 0x50533, getSkyForTemp(0.9f), 12638463),
            genSettings = genSettings,
            spawnSettings = spawnSettings,
        )
    }

    /**
     * Base biome function
     * Sky color is not generated
     */
    fun biome(
        precipitation: Biome.RainType,
        biomeCategory: Biome.Category,
        depth: Float,
        scale: Float,
        temperature: Float,
        downfall: Float,
        effects: BiomeAmbience.Builder,
        genSettings: BiomeGenerationSettings.Builder,
        spawnSettings: MobSpawnInfo.Builder? = null,
    ): Biome {
        return Biome.Builder()
            .precipitation(precipitation)
            .biomeCategory(biomeCategory)
            .depth(depth)
            .scale(scale)
            .temperature(temperature)
            .downfall(downfall)
            .specialEffects(effects.build())
            .generationSettings(genSettings.build())
            .mobSpawnSettings(spawnSettings?.build() ?: MobSpawnInfo.EMPTY)
            .build()
    }

    /** Shortcut function and enforces surface builder */
    fun <C : ISurfaceBuilderConfig> genSettings(surfaceBuilder: SurfaceBuilder<C>, config: C): BiomeGenerationSettings.Builder {
        return BiomeGenerationSettings.Builder().surfaceBuilder(surfaceBuilder.configured(config))
    }

    /** Shortcut function and enforces surface builder */
    fun <C : ISurfaceBuilderConfig> genSettings(surfaceBuilder: ConfiguredSurfaceBuilder<C>): BiomeGenerationSettings.Builder {
        return BiomeGenerationSettings.Builder().surfaceBuilder(surfaceBuilder)
    }

    /** Shortcut function */
    fun spawnSettings(): MobSpawnInfo.Builder {
        return MobSpawnInfo.Builder()
    }
}