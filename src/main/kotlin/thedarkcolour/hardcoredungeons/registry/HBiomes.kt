package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import thedarkcolour.hardcoredungeons.util.modLoc

/**
 * Biomes for Hardcore Dungeons.
 *
 * @author thedarkcolour, genericrandom64
 */
@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate", "unused")
object HBiomes {
    // Overworld biomes
    //val THICK_FOREST = REGISTRY.register("thick_forest", ThickForestBiome)
    val MUSHROOM_CLIFFS = biome("mushroom_cliffs")

    // Aubrum biomes
    val AUBRUM_WASTELAND = biome("aubrum_wasteland")
    val GOLDEN_FOREST = biome("golden_forest")
    val AUBRUM_MOUNTAINS = biome("aubrum_mountains")
    val AURI_PLAINS = biome("auri_plains")

    // Castleton biomes
    val CASTLETON_HILLS = biome("castleton_hills")
    val KNIGHTLY_SHRUBLAND = biome("knightly_shrubland")

    // Rainbowland biomes
    val RAINBOWLAND = biome("rainbowland")

    // Candyland biomes
    val GUMDROP_FIELDS = biome("gumdrop_fields")
    val CANDY_PLAINS = biome("candy_plains")

    private fun biome(path: String) = ResourceKey.create(Registries.BIOME, modLoc(path))
}