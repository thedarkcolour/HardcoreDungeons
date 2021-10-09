package thedarkcolour.hardcoredungeons.biome

import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.math.MathHelper
import net.minecraft.world.biome.*
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.carver.ConfiguredCarvers
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import thedarkcolour.hardcoredungeons.feature.HConfiguredFeatures
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HSurfaceBuilders
import thedarkcolour.hardcoredungeons.surfacebuilder.HConfiguredSurfaceBuilders

object HBiomeMaker {
    private val DEFAULT_CASTLETON_EFFECTS = effects(0x6fa3bf, 0x1f37f2, 0x113d51, 0x1d5a75)

    fun makeCastletonForest(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.CASTLETON_SURFACE)
        genSettings.addCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.CAVE)
        genSettings.addCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.CANYON)

        HConfiguredFeatures.withPurpleLumshrooms(genSettings)

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

        HConfiguredFeatures.withSparseLumlightTrees(genSettings)
        HConfiguredFeatures.withPurpleLumshrooms(genSettings)
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

    fun makeKnightlyShrublandBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.CASTLETON_SURFACE)

        HConfiguredFeatures.withLumlightCabin(genSettings)
        HConfiguredFeatures.withLumlightShrubs(genSettings)

        val spawnSettings = spawnSettings()
        spawnSettings.addSpawn(EntityClassification.CREATURE, HEntities.CASTLETON_DEER, 6, 2, 6)

        return biome(
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.PLAINS,
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
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.PLAINS,
            depth = 0.1f,
            scale = 0.2f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x5008b2, 0x621ff2, 0xcfb3ff, 0xe388cf),
            genSettings = genSettings
        )
    }

    fun makeThickForestBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.THICK_FOREST, SurfaceBuilder.CONFIG_PODZOL)

        DefaultBiomeFeatures.addDefaultCarvers(genSettings)
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(genSettings)
        DefaultBiomeFeatures.addDefaultMonsterRoom(genSettings)
        DefaultBiomeFeatures.addDefaultUndergroundVariety(genSettings) // different stones
        DefaultBiomeFeatures.addDefaultOres(genSettings) // ores
        DefaultBiomeFeatures.addDefaultSoftDisks(genSettings) // sand + clay
        DefaultBiomeFeatures.addMountainEdgeTrees(genSettings) // spruce and oak
        DefaultBiomeFeatures.addDefaultFlowers(genSettings) // default flowers
        DefaultBiomeFeatures.addForestGrass(genSettings) // grass
        DefaultBiomeFeatures.addTaigaGrass(genSettings) // ferns
        DefaultBiomeFeatures.addWaterTrees(genSettings) // water trees
        //DefaultBiomeFeatures.addExtraGold(genSettings)
        DefaultBiomeFeatures.addWarmFlowers(genSettings) // warm flowers
        DefaultBiomeFeatures.addDefaultExtraVegetation(genSettings) // pumpkin + sugar cane
        HConfiguredFeatures.withOakShrubs(genSettings)

        val spawnSettings = spawnSettings()
        spawnSettings.addSpawn(EntityClassification.CREATURE, EntityType.SHEEP, 12, 4, 4)
        spawnSettings.addSpawn(EntityClassification.CREATURE, HEntities.DEER, 7, 2, 3)
        spawnSettings.addSpawn(EntityClassification.AMBIENT, EntityType.BAT, 8, 8, 8)
        spawnSettings.addSpawn(EntityClassification.MONSTER, EntityType.SPIDER, 100, 4, 4)
        spawnSettings.addSpawn(EntityClassification.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
        spawnSettings.addSpawn(EntityClassification.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
        spawnSettings.addSpawn(EntityClassification.MONSTER, EntityType.SKELETON, 100, 4, 4)
        spawnSettings.addSpawn(EntityClassification.MONSTER, EntityType.CREEPER, 100, 4, 4)
        spawnSettings.addSpawn(EntityClassification.MONSTER, EntityType.SLIME, 100, 4, 4)
        spawnSettings.addSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
        spawnSettings.addSpawn(EntityClassification.MONSTER, EntityType.WITCH, 5, 1, 1)

        return biome(
            precipitation = Biome.RainType.RAIN,
            biomeCategory = Biome.Category.FOREST,
            depth = 0.2f,
            scale = 0.25f,
            temperature = 0.7f,
            downfall = 0.8f,
            effects = effects(0x3f76e4, 0x50533, getSkyForTemp(0.7f), 12638463),
            genSettings = genSettings,
            spawnSettings = spawnSettings,
        )
    }

    fun makeMushroomCliffsBiome(): Biome {
        val genSettings = genSettings(SurfaceBuilder.DEFAULT, SurfaceBuilder.CONFIG_MYCELIUM)

        DefaultBiomeFeatures.addDefaultCarvers(genSettings) // canyon + cave
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(genSettings) // stronghold etc.
        DefaultBiomeFeatures.addDefaultMonsterRoom(genSettings) // dungeon
        DefaultBiomeFeatures.addDefaultUndergroundVariety(genSettings) // different stones
        DefaultBiomeFeatures.addDefaultOres(genSettings) // ores
        DefaultBiomeFeatures.addDefaultSoftDisks(genSettings) // clay + sand
        DefaultBiomeFeatures.addMushroomFieldVegetation(genSettings) // mushroom island vegetation
        DefaultBiomeFeatures.addDefaultMushrooms(genSettings) // mushrooms
        DefaultBiomeFeatures.addDefaultExtraVegetation(genSettings) // pumpkin + sugar cane

        HConfiguredFeatures.addShroomyBoulders(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.MUSHROOM,
            depth = 0.3625f,
            scale = 1.225f,
            temperature = 0.9f,
            downfall = 1.0f,
            effects = effects(0x3f76e4, 0x50533, getSkyForTemp(0.9f), 12638463),
            genSettings = genSettings
        )
    }

    fun makeAubrumWastelandBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        return biome(
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.PLAINS,
            depth = 0.02f,
            scale = 0.4f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x669900, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings
        )
    }

    fun makeGoldenForestBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        return biome(
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.MUSHROOM,
            depth = 0.3625f,
            scale = 0.1f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0xffbb33, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings

        )
    }

    fun makeAubrumMountainsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        return biome(
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.MUSHROOM,
            depth = 0.3625f,
            scale = 1.225f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x888888, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings
        )
    }

    fun makeAuriPlainsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        HConfiguredFeatures.withAubrumFlowers(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.MUSHROOM,
            depth = 0.02f,
            scale = 0.1f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x999966, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings
        )
    }

    fun makeGumdropFieldsBiome(): Biome {
        val genSettings = genSettings(HConfiguredSurfaceBuilders.SUGARY_SURFACE)
        val spawnSettings = spawnSettings()

        //HConfiguredFeatures.withSparseCandyCanes(genSettings)
        //HConfiguredFeatures.withSparseChocolateBars(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.PLAINS,
            depth = 0.04f,
            scale = 0.2f,
            temperature = 1.5f,
            downfall = 0.4f,
            effects = effects(0xd19bdc, 0x50533, getSkyForTemp(0.9f), 12638463),
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
            precipitation = Biome.RainType.NONE,
            biomeCategory = Biome.Category.PLAINS,
            depth = 0.04f,
            scale = 0.2f,
            temperature = 1.5f,
            downfall = 0.4f,
            effects = effects(0xd19bdc, 0x50533, getSkyForTemp(0.9f), 12638463),
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

    /**
     * Biome ambience with default parameters and enforced the required ones.
     * Should prevent slip ups on my part :)
     */
    fun effects(
        waterColor: Int,
        waterFogColor: Int,
        skyColor: Int,
        skyFogColor: Int,
    ): BiomeAmbience.Builder {
        return BiomeAmbience.Builder()
            .waterColor(waterColor)
            .waterFogColor(waterFogColor)
            .skyColor(skyColor)
            .fogColor(skyFogColor)
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

    /** Shortcut function */
    fun MobSpawnInfo.Builder.addSpawn(
        classification: EntityClassification,
        entityType: EntityType<*>,
        weight: Int,
        min: Int,
        max: Int
    ): MobSpawnInfo.Builder {
        return addSpawn(classification, MobSpawnInfo.Spawners(entityType, weight, min, max))
    }

    private fun getSkyForTemp(temperature: Float): Int {
        val a = MathHelper.clamp(temperature / 3.0f, -1.0f, 1.0f)
        return MathHelper.hsvToRgb(0.62222224f - a * 0.05f, 0.5f + a * 0.1f, 1.0f)
    }
}