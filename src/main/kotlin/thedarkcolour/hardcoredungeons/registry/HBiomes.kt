package thedarkcolour.hardcoredungeons.registry

import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraftforge.common.BiomeManager
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.biome.HBiomeMaker

/**
 * Biomes for Hardcore Dungeons.
 *
 * @author TheDarkColour, genericrandom64
 */
@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
object HBiomes {
    // Overworld biomes
    val THICK_FOREST = HBiomeMaker.makeThickForestBiome().setRegistryKey("thick_forest")
    val MUSHROOM_CLIFFS = HBiomeMaker.makeMushroomCliffsBiome().setRegistryKey("mushroom_cliffs")

    // Aubrum biomes
    val AUBRUM_WASTELAND = HBiomeMaker.makeAubrumWastelandBiome().setRegistryKey("aubrum_wasteland")
    val GOLDEN_FOREST = HBiomeMaker.makeGoldenForestBiome().setRegistryKey("golden_forest")
    val AUBRUM_MOUNTAINS = HBiomeMaker.makeAubrumMountainsBiome().setRegistryKey("aubrum_mountains")
    val AURI_PLAINS = HBiomeMaker.makeAuriPlainsBiome().setRegistryKey("auri_plains")

    // Castleton biomes
    val CASTLETON_HILLS = HBiomeMaker.makeCastletonHillsBiome().setRegistryKey("castleton_hills")
    val KNIGHTLY_SHRUBLAND = HBiomeMaker.makeKnightlyShrublandBiome().setRegistryKey("knightly_shrubland")

    // Rainbowland biomes
    val RAINBOW_PLAINS = HBiomeMaker.makeRainbowPlainsBiome().setRegistryKey("rainbow_plains")

    fun registerBiomes(biomes: IForgeRegistry<Biome>) {
        biomes.register(RAINBOW_PLAINS)
        biomes.register(CASTLETON_HILLS)
        biomes.register(KNIGHTLY_SHRUBLAND)

        biomes.register(AUBRUM_WASTELAND)
        biomes.register(GOLDEN_FOREST)
        biomes.register(AUBRUM_MOUNTAINS)
        biomes.register(AURI_PLAINS)

        biomes.register(THICK_FOREST)
        biomes.register(MUSHROOM_CLIFFS)
    }

    val THICK_FOREST_KEY = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, ResourceLocation(HardcoreDungeons.ID, "thick_forest"))

    fun biomeLoading(event: BiomeLoadingEvent) {
        if (event.name == THICK_FOREST.registryName) {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, BiomeManager.BiomeEntry(THICK_FOREST_KEY, 6))
        }
        if (event.name == ResourceLocation("minecraft:mushroom_islands")) {
            HFeatures.withMushroomHut(event.generation)
        }
    }
}