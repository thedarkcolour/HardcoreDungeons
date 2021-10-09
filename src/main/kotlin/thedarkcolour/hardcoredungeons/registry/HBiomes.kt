package thedarkcolour.hardcoredungeons.registry

import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraftforge.common.BiomeManager
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.biome.HBiomeMaker
import thedarkcolour.hardcoredungeons.block.HRegistry
import thedarkcolour.hardcoredungeons.config.HConfig
import thedarkcolour.hardcoredungeons.feature.HConfiguredFeatures

/**
 * Biomes for Hardcore Dungeons.
 *
 * @author TheDarkColour, genericrandom64
 */
@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate", "unused")
object HBiomes : HRegistry<Biome>(ForgeRegistries.BIOMES) {
    // Overworld biomes
    val THICK_FOREST by register("thick_forest") { HBiomeMaker.makeThickForestBiome() }
    val MUSHROOM_CLIFFS by register("mushroom_cliffs") { HBiomeMaker.makeMushroomCliffsBiome() }

    // Aubrum biomes
    val AUBRUM_WASTELAND by register("aubrum_wasteland") { HBiomeMaker.makeAubrumWastelandBiome() }
    val GOLDEN_FOREST by register("golden_forest") { HBiomeMaker.makeGoldenForestBiome() }
    val AUBRUM_MOUNTAINS by register("aubrum_mountains") { HBiomeMaker.makeAubrumMountainsBiome() }
    val AURI_PLAINS by register("auri_plains") { HBiomeMaker.makeAuriPlainsBiome() }

    // Castleton biomes
    val CASTLETON_HILLS by register("castleton_hills") { HBiomeMaker.makeCastletonHillsBiome() }
    val KNIGHTLY_SHRUBLAND by register("knightly_shrubland") { HBiomeMaker.makeKnightlyShrublandBiome() }

    // Rainbowland biomes
    val RAINBOW_PLAINS by register("rainbow_plains") { HBiomeMaker.makeRainbowPlainsBiome() }

    // Candyland biomes
    val GUMDROP_FIELDS by register("gumdrop_fields") { HBiomeMaker.makeGumdropFieldsBiome() }
    val CANDY_PLAINS by register("candy_plains") { HBiomeMaker.makeCandyPlainsBiome() }

    val THICK_FOREST_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, ResourceLocation(HardcoreDungeons.ID, "thick_forest"))

    fun biomeLoading(event: BiomeLoadingEvent) {
        if (event.name == THICK_FOREST.registryName && HConfig.thickForestGenerates.value) {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, BiomeManager.BiomeEntry(THICK_FOREST_KEY, 6))
        }
        if (event.name == ResourceLocation("minecraft:mushroom_islands")) {
            HConfiguredFeatures.withMushroomHut(event.generation)
        }

        if (HConfig.malachiteCrystalGeneration.value) {
            HConfiguredFeatures.withMalachiteCrystals(event.generation)
        }
    }
}