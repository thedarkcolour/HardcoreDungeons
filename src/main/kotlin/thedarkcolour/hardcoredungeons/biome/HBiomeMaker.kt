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
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HFeatures
import thedarkcolour.hardcoredungeons.registry.HSurfaceBuilders

object HBiomeMaker {
    fun makeCastletonHillsBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.CASTLETON_SURFACE)
        genSettings.withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243767_a)

        HFeatures.addSparseLumlightTrees(genSettings)
        HFeatures.addPurpleLumshrooms(genSettings)
        DefaultBiomeFeatures.withBlueIce(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            category = Biome.Category.PLAINS,
            depth = 1.5f,
            scale = 0.07f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x6fa3bf, 0x1f37f2, 0x113d51, 0x1d5a75),
            genSettings = genSettings
        )
    }

    //todo
    fun makeKnightlyShrublandBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.CASTLETON_SURFACE)

        HFeatures.withLumlightCabin(genSettings)
        HFeatures.withLumlightShrubs(genSettings)

        val spawnSettings = spawnSettings()
        spawnSettings.addSpawn(EntityClassification.CREATURE, HEntities.CASTLETON_DEER, 6, 2, 6)

        return biome(
            precipitation = Biome.RainType.NONE,
            category = Biome.Category.PLAINS,
            depth = 0.1f,
            scale = 0.3f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x6fa3bf, 0x1f37f2, 0x113d51, 0x1d5a75),
            genSettings = genSettings
        )
    }

    fun makeRainbowPlainsBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.RAINBOWLAND_SURFACE)

        HFeatures.withRainbowlandOres(genSettings)
        HFeatures.withRainbowlandStructures(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            category = Biome.Category.PLAINS,
            depth = 0.1f,
            scale = 0.2f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x5008b2, 0x621ff2, 0xcfb3ff, 0xe388cf),
            genSettings = genSettings
        )
    }

    fun makeThickForestBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.THICK_FOREST_SURFACE, SurfaceBuilder.PODZOL_DIRT_GRAVEL_CONFIG)

        DefaultBiomeFeatures.withCavesAndCanyons(genSettings)
        DefaultBiomeFeatures.withStrongholdAndMineshaft(genSettings)
        DefaultBiomeFeatures.withMonsterRoom(genSettings)
        DefaultBiomeFeatures.withCommonOverworldBlocks(genSettings)
        DefaultBiomeFeatures.withOverworldOres(genSettings)
        DefaultBiomeFeatures.withDisks(genSettings)
        DefaultBiomeFeatures.withMountainEdgeTrees(genSettings)
        DefaultBiomeFeatures.withDefaultFlowers(genSettings)
        DefaultBiomeFeatures.withForestGrass(genSettings)
        DefaultBiomeFeatures.withTaigaGrassVegetation(genSettings)
        DefaultBiomeFeatures.withTreesInWater(genSettings)
        DefaultBiomeFeatures.withExtraGoldOre(genSettings)
        DefaultBiomeFeatures.withWarmFlowers(genSettings)
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(genSettings)
        HFeatures.withOakShrubs(genSettings)

        val spawnSettings = spawnSettings()
        spawnSettings.addSpawn(EntityClassification.CREATURE, EntityType.SHEEP, 12, 4, 4)
        spawnSettings.addSpawn(EntityClassification.CREATURE, EntityType.COW, 8, 4, 4)
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
            category = Biome.Category.FOREST,
            depth = 0.2f,
            scale = 0.25f,
            temperature = 0.7f,
            downfall = 0.8f,
            effects = effects(0x3f76e4, 0x50533, getSkyForTemp(0.7f), 12638463),
            genSettings = genSettings,
            spawnSettings = spawnSettings.build()
        )
    }

    fun makeMushroomCliffsBiome(): Biome {
        val genSettings = genSettings(SurfaceBuilder.DEFAULT, SurfaceBuilder.MYCELIUM_DIRT_GRAVEL_CONFIG)

        DefaultBiomeFeatures.withCavesAndCanyons(genSettings)
        DefaultBiomeFeatures.withStrongholdAndMineshaft(genSettings)
        DefaultBiomeFeatures.withMonsterRoom(genSettings)
        DefaultBiomeFeatures.withCommonOverworldBlocks(genSettings)
        DefaultBiomeFeatures.withOverworldOres(genSettings)
        DefaultBiomeFeatures.withDisks(genSettings)
        DefaultBiomeFeatures.withMushroomBiomeVegetation(genSettings)
        DefaultBiomeFeatures.withNormalMushroomGeneration(genSettings)
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(genSettings)

        HFeatures.withShroomyBoulders(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            category = Biome.Category.MUSHROOM,
            depth = 0.3625f,
            scale = 1.225f,
            temperature = 0.9f,
            downfall = 1.0f,
            effects = effects(0x3f76e4, 0x50533, getSkyForTemp(0.9f), 12638463),
            genSettings = genSettings
        )
    }

    fun makeAubrumWastelandBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        return biome(
            precipitation = Biome.RainType.NONE,
            category = Biome.Category.PLAINS,
            depth = 0.02f,
            scale = 0.4f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x669900, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings
        )
    }

    fun makeGoldenForestBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        return biome(
            precipitation = Biome.RainType.NONE,
            category = Biome.Category.MUSHROOM,
            depth = 0.3625f,
            scale = 0.1f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0xffbb33, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings

        )
    }

    fun makeAubrumMountainsBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        return biome(
            precipitation = Biome.RainType.NONE,
            category = Biome.Category.MUSHROOM,
            depth = 0.3625f,
            scale = 1.225f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x888888, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings
        )
    }

    fun makeAuriPlainsBiome(): Biome {
        val genSettings = genSettings(HSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        HFeatures.withAubrumFlowers(genSettings)

        return biome(
            precipitation = Biome.RainType.NONE,
            category = Biome.Category.MUSHROOM,
            depth = 0.02f,
            scale = 0.1f,
            temperature = 1.5f,
            downfall = 0.0f,
            effects = effects(0x999966, 0x1f37f2, 0xab9560, 0xc4af7c),
            genSettings = genSettings
        )
    }

    /**
     * Base biome function
     * Sky color is not generated
     */
    fun biome(
        precipitation: Biome.RainType,
        category: Biome.Category,
        depth: Float,
        scale: Float,
        temperature: Float,
        downfall: Float,
        effects: BiomeAmbience.Builder,
        genSettings: BiomeGenerationSettings.Builder,
        spawnSettings: MobSpawnInfo = MobSpawnInfo.EMPTY,
    ): Biome {
        return Biome.Builder()
            .precipitation(precipitation)
            .category(category)
            .depth(depth)
            .scale(scale)
            .temperature(temperature)
            .downfall(downfall)
            .setEffects(effects.build())
            .withGenerationSettings(genSettings.build())
            .withMobSpawnSettings(spawnSettings)
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
            .setWaterColor(waterColor)
            .setWaterFogColor(waterFogColor)
            .withSkyColor(skyColor)
            .setFogColor(skyFogColor)
    }

    /** Shortcut function and enforces surface builder */
    fun <C : ISurfaceBuilderConfig> genSettings(surfaceBuilder: SurfaceBuilder<C>, config: C): BiomeGenerationSettings.Builder {
        return BiomeGenerationSettings.Builder().withSurfaceBuilder(surfaceBuilder.func_242929_a(config))
    }

    /** Shortcut function and enforces surface builder */
    fun <C : ISurfaceBuilderConfig> genSettings(surfaceBuilder: ConfiguredSurfaceBuilder<C>): BiomeGenerationSettings.Builder {
        return BiomeGenerationSettings.Builder().withSurfaceBuilder(surfaceBuilder)
    }

    /** Shortcut function */
    fun spawnSettings(): MobSpawnInfo.Builder {
        return MobSpawnInfo.Builder()
    }

    /** Just for consistency why the fuck is it not build */
    fun MobSpawnInfo.Builder.build(): MobSpawnInfo {
        return copy()
    }

    /** Shortcut function */
    fun MobSpawnInfo.Builder.addSpawn(
        classification: EntityClassification,
        entityType: EntityType<*>,
        weight: Int,
        min: Int,
        max: Int
    ): MobSpawnInfo.Builder {
        return withSpawner(classification, MobSpawnInfo.Spawners(entityType, weight, min, max))
    }

    private fun getSkyForTemp(temperature: Float): Int {
        val a = MathHelper.clamp(temperature / 3.0f, -1.0f, 1.0f)
        return MathHelper.hsvToRGB(0.62222224f - a * 0.05f, 0.5f + a * 0.1f, 1.0f)
    }
}