package thedarkcolour.hardcoredungeons.biome

import net.minecraft.data.worldgen.BiomeDefaultFeatures
import net.minecraft.data.worldgen.Carvers
import net.minecraft.util.Mth
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.BiomeGenerationSettings
import net.minecraft.world.level.biome.BiomeSpecialEffects
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration
import thedarkcolour.hardcoredungeons.feature.HConfiguredFeatures
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HSurfaceBuilders
import thedarkcolour.hardcoredungeons.surfacebuilder.HConfiguredSurfaceBuilders

object HBiomeMaker {
    private val DEFAULT_CASTLETON_EFFECTS = fx(0x6fa3bf, 0x1f37f2, 0x113d51, 0x1d5a75)

    fun makeCastletonForest(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.CASTLETON_SURFACE)
        genSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
        genSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON)

        HConfiguredFeatures.withPurpleLumshrooms(genSettings)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            Biome.BiomeCategory.FOREST,
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
        genSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)

        HConfiguredFeatures.withSparseLumlightTrees(genSettings)
        HConfiguredFeatures.withPurpleLumshrooms(genSettings)
        BiomeDefaultFeatures.addBlueIce(genSettings)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.PLAINS,
            depth = 1.5f,
            scale = 0.07f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = DEFAULT_CASTLETON_EFFECTS,
            genSettings = genSettings
        )
    }

    fun makeKnightlyShrublandBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.CASTLETON_SURFACE)

        HConfiguredFeatures.withLumlightCabin(genSettings)
        HConfiguredFeatures.withLumlightShrubs(genSettings)

        val spawnSettings = spawnSettings()
        spawnSettings.addSpawn(MobCategory.CREATURE, HEntities.CASTLETON_DEER, 6, 2, 6)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.PLAINS,
            depth = 0.1f,
            scale = 0.3f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = DEFAULT_CASTLETON_EFFECTS,
            genSettings = genSettings
        )
    }

    fun makeRainbowPlainsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.RAINBOWLAND_SURFACE)

        HConfiguredFeatures.withRainbowlandOres(genSettings)
        HConfiguredFeatures.withRainbowlandStructures(genSettings)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.PLAINS,
            depth = 0.1f,
            scale = 0.2f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = fx(0x5008b2, 0x621ff2, 0xcfb3ff, 0xe388cf),
            genSettings = genSettings
        )
    }

    fun makeThickForestBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.THICK_FOREST, SurfaceBuilder.CONFIG_PODZOL)

        BiomeDefaultFeatures.addDefaultCarvers(genSettings)
        BiomeDefaultFeatures.addDefaultOverworldLandStructures(genSettings)
        BiomeDefaultFeatures.addDefaultMonsterRoom(genSettings)
        BiomeDefaultFeatures.addDefaultUndergroundVariety(genSettings) // different stones
        BiomeDefaultFeatures.addDefaultOres(genSettings) // ores
        BiomeDefaultFeatures.addDefaultSoftDisks(genSettings) // sand + clay
        BiomeDefaultFeatures.addMountainEdgeTrees(genSettings) // spruce and oak
        BiomeDefaultFeatures.addDefaultFlowers(genSettings) // default flowers
        BiomeDefaultFeatures.addForestGrass(genSettings) // grass
        BiomeDefaultFeatures.addTaigaGrass(genSettings) // ferns
        BiomeDefaultFeatures.addWaterTrees(genSettings) // water trees
        //BiomeDefaultFeatures.addExtraGold(genSettings)
        BiomeDefaultFeatures.addWarmFlowers(genSettings) // warm flowers
        BiomeDefaultFeatures.addDefaultExtraVegetation(genSettings) // pumpkin + sugar cane
        HConfiguredFeatures.withOakShrubs(genSettings)

        val spawnSettings = spawnSettings()
        spawnSettings.addSpawn(MobCategory.CREATURE, EntityType.SHEEP, 12, 4, 4)
        spawnSettings.addSpawn(MobCategory.CREATURE, HEntities.DEER, 7, 2, 3)
        spawnSettings.addSpawn(MobCategory.AMBIENT, EntityType.BAT, 8, 8, 8)
        spawnSettings.addSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
        spawnSettings.addSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
        spawnSettings.addSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
        spawnSettings.addSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 4, 4)
        spawnSettings.addSpawn(MobCategory.MONSTER, EntityType.CREEPER, 100, 4, 4)
        spawnSettings.addSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
        spawnSettings.addSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
        spawnSettings.addSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)

        return biome(
            precipitation = Biome.Precipitation.RAIN,
            biomeCategory = Biome.BiomeCategory.FOREST,
            depth = 0.2f,
            scale = 0.25f,
            temperature = 0.7f,
            downfall = 0.8f,
            effects = fx(0x3f76e4, 0x50533, getSkyForTemp(0.7f), 12638463),
            genSettings = genSettings,
            spawnSettings = spawnSettings,
        )
    }

    fun makeMushroomCliffsBiome(): Biome {
        val genSettings = genSettings(SurfaceBuilder.DEFAULT, SurfaceBuilder.CONFIG_MYCELIUM)

        BiomeDefaultFeatures.addDefaultCarvers(genSettings) // canyon + cave
        BiomeDefaultFeatures.addDefaultOverworldLandStructures(genSettings) // stronghold etc.
        BiomeDefaultFeatures.addDefaultMonsterRoom(genSettings) // dungeon
        BiomeDefaultFeatures.addDefaultUndergroundVariety(genSettings) // different stones
        BiomeDefaultFeatures.addDefaultOres(genSettings) // ores
        BiomeDefaultFeatures.addDefaultSoftDisks(genSettings) // clay + sand
        BiomeDefaultFeatures.addMushroomFieldVegetation(genSettings) // mushroom island vegetation
        BiomeDefaultFeatures.addDefaultMushrooms(genSettings) // mushrooms
        BiomeDefaultFeatures.addDefaultExtraVegetation(genSettings) // pumpkin + sugar cane

        HConfiguredFeatures.addShroomyBoulders(genSettings)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.MUSHROOM,
            depth = 0.3625f,
            scale = 1.225f,
            temperature = 0.9f,
            downfall = 1.0f,
            effects = fx(0x3f76e4, 0x50533, getSkyForTemp(0.9f), 12638463),
            genSettings = genSettings
        )
    }

    fun makeAubrumWastelandBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.PLAINS,
            depth = 0.02f,
            scale = 0.4f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = fx(0x669900, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings
        )
    }

    fun makeGoldenForestBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.MUSHROOM,
            depth = 0.3625f,
            scale = 0.1f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = fx(0xffbb33, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings

        )
    }

    fun makeAubrumMountainsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.MUSHROOM,
            depth = 0.3625f,
            scale = 1.225f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = fx(0x888888, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings
        )
    }

    fun makeAuriPlainsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        HConfiguredFeatures.withAubrumFlowers(genSettings)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.MUSHROOM,
            depth = 0.02f,
            scale = 0.1f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = fx(0x999966, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings
        )
    }

    fun makeGumdropFieldsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.SUGARY_SURFACE)
        val spawnSettings = spawnSettings()

        //HConfiguredFeatures.withSparseCandyCanes(genSettings)
        //HConfiguredFeatures.withSparseChocolateBars(genSettings)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.PLAINS,
            depth = 0.04f,
            scale = 0.2f,
            temperature = 1.5f,
            downfall = 0.4f,
            effects = fx(0xd19bdc, 0x50533, getSkyForTemp(0.9f), 12638463),
            genSettings = genSettings,
            spawnSettings = spawnSettings,
        )
    }

    fun makeCandyPlainsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.SUGARY_SURFACE)
        val spawnSettings = spawnSettings()

        HConfiguredFeatures.withSparseCandyCanes(genSettings)
        HConfiguredFeatures.withSparseChocolateBars(genSettings)

        return biome(
            precipitation = Biome.Precipitation.NONE,
            biomeCategory = Biome.BiomeCategory.PLAINS,
            depth = 0.04f,
            scale = 0.2f,
            temperature = 1.5f,
            downfall = 0.4f,
            effects = fx(0xd19bdc, 0x50533, getSkyForTemp(0.9f), 12638463),
            genSettings = genSettings,
            spawnSettings = spawnSettings,
        )
    }

    /**
     * Base biome function
     * Sky color is not generated
     */
    fun biome(
        precipitation: Biome.Precipitation,
        biomeCategory: Biome.BiomeCategory,
        depth: Float,
        scale: Float,
        temperature: Float,
        downfall: Float,
        effects: BiomeSpecialEffects.Builder,
        genSettings: BiomeGenerationSettings.Builder,
        spawnSettings: MobSpawnSettings.Builder? = null,
    ): Biome {
        return Biome.BiomeBuilder()
            .precipitation(precipitation)
            .biomeCategory(biomeCategory)
            .depth(depth)
            .scale(scale)
            .temperature(temperature)
            .downfall(downfall)
            .specialEffects(effects.build())
            .generationSettings(genSettings.build())
            .mobSpawnSettings(spawnSettings?.build() ?: MobSpawnSettings.EMPTY)
            .build()
    }

    /**
     * Biome ambience with default parameters and enforced the required ones.
     * Should prevent slip ups on my part :)
     */
    fun fx(
        waterColor: Int,
        waterFogColor: Int,
        skyColor: Int,
        skyFogColor: Int,
    ): BiomeSpecialEffects.Builder {
        return BiomeSpecialEffects.Builder()
            .waterColor(waterColor)
            .waterFogColor(waterFogColor)
            .skyColor(skyColor)
            .fogColor(skyFogColor)
    }

    /** Shortcut function and enforces surface builder */
    fun <C : SurfaceBuilderConfiguration> genSettings(surfaceBuilder: SurfaceBuilder<C>, config: C): BiomeGenerationSettings.Builder {
        return BiomeGenerationSettings.Builder().surfaceBuilder(surfaceBuilder.configured(config))
    }

    /** Shortcut function and enforces surface builder */
    fun <C : SurfaceBuilderConfiguration> genSettings(surfaceBuilder: ConfiguredSurfaceBuilder<C>): BiomeGenerationSettings.Builder {
        return BiomeGenerationSettings.Builder().surfaceBuilder(surfaceBuilder)
    }

    /** Shortcut function */
    fun spawnSettings(): MobSpawnSettings.Builder {
        return MobSpawnSettings.Builder()
    }

    /** Shortcut function */
    fun MobSpawnSettings.Builder.addSpawn(
        classification: MobCategory,
        entityType: EntityType<*>,
        weight: Int,
        min: Int,
        max: Int
    ): MobSpawnSettings.Builder {
        return addSpawn(classification, MobSpawnSettings.SpawnerData(entityType, weight, min, max))
    }

    private fun getSkyForTemp(temperature: Float): Int {
        val a = Mth.clamp(temperature / 3.0f, -1.0f, 1.0f)
        return Mth.hsvToRgb(0.62222224f - a * 0.05f, 0.5f + a * 0.1f, 1.0f)
    }
}