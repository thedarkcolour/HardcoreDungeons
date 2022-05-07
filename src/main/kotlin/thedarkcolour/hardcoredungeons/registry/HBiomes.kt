package thedarkcolour.hardcoredungeons.registry

import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry
import net.minecraftforge.event.world.BiomeLoadingEvent
import team.rusty.util.worldgen.biome.AbstractBiomeRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.worldgen.biome.*
import thedarkcolour.hardcoredungeons.config.HConfig
import thedarkcolour.hardcoredungeons.worldgen.feature.HWorldGen
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * Biomes for Hardcore Dungeons.
 *
 * @author TheDarkColour, genericrandom64
 */
@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate", "unused")
object HBiomes {
    private val REGISTRY = AbstractBiomeRegistry(HardcoreDungeons.ID)

    fun init() {
        REGISTRY.register(MOD_BUS)
    }

    // Overworld biomes
    val THICK_FOREST = REGISTRY.register("thick_forest", ThickForestBiome)
    val MUSHROOM_CLIFFS = REGISTRY.register("mushroom_cliffs", MushroomCliffsBiome)

    // Aubrum biomes
    val AUBRUM_WASTELAND = REGISTRY.register("aubrum_wasteland", AubrumWastelandBiome)
    val GOLDEN_FOREST = REGISTRY.register("golden_forest", GoldenForestBiome)
    val AUBRUM_MOUNTAINS = REGISTRY.register("aubrum_mountains", AubrumMountainsBiome)
    val AURI_PLAINS = REGISTRY.register("auri_plains", AuriPlainsBiome)

    // Castleton biomes
    val CASTLETON_HILLS = REGISTRY.register("castleton_hills", CastletonHillsBiome)
    val KNIGHTLY_SHRUBLAND = REGISTRY.register("knightly_shrubland", KnightlyShrublandBiome)

    // Rainbowland biomes
    val RAINBOW_PLAINS = REGISTRY.register("rainbow_plains", RainbowPlainsBiome)

    // Candyland biomes
    val GUMDROP_FIELDS = REGISTRY.register("gumdrop_fields", GumdropFieldsBiome)
    val CANDY_PLAINS = REGISTRY.register("candy_plains", CandyPlainsBiome)

    val THICK_FOREST_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, ResourceLocation(HardcoreDungeons.ID, "thick_forest"))

    fun biomeLoading(event: BiomeLoadingEvent) {
        if (event.name == ResourceLocation("minecraft:mushroom_islands")) {
            HWorldGen.withMushroomHut(event.generation)
        }

        if (HConfig.malachiteCrystalGeneration.value) {
            HWorldGen.withMalachiteCrystals(event.generation)
        }
    }
}