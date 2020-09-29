package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.Biomes
import net.minecraftforge.common.BiomeDictionary
import net.minecraftforge.common.BiomeManager
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.biome.HBiomeMaker
import thedarkcolour.hardcoredungeons.biome.aubrum.AubrumMountainsBiome
import thedarkcolour.hardcoredungeons.biome.aubrum.AubrumWastelandBiome
import thedarkcolour.hardcoredungeons.biome.aubrum.AuriPlainsBiome
import thedarkcolour.hardcoredungeons.biome.aubrum.GoldenForestBiome
import thedarkcolour.hardcoredungeons.biome.castleton.CastletonHillsBiome
import thedarkcolour.hardcoredungeons.biome.castleton.KnightlyShrublandBiome
import thedarkcolour.hardcoredungeons.biome.castleton.RunedFlatsBiome
import thedarkcolour.hardcoredungeons.biome.overworld.MushroomCliffsBiome
import thedarkcolour.hardcoredungeons.biome.overworld.ThickForestBiome
import thedarkcolour.hardcoredungeons.biome.rainbowland.RainbowPlainsBiome
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

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
    //val AUBRUM_WASTELAND = AubrumWastelandBiome().setRegistryKey("aubrum_wasteland")
    //val GOLDEN_FOREST = GoldenForestBiome().setRegistryKey("golden_forest")
    //val AUBRUM_MOUNTAINS = AubrumMountainsBiome().setRegistryKey("aubrum_mountains")
    //val AURI_PLAINS = AuriPlainsBiome().setRegistryKey("auri_plains")

    // Castleton biomes
    val CASTLETON_HILLS = HBiomeMaker.makeCastletonHillsBiome().setRegistryKey("castleton_hills")
    val KNIGHTLY_SHRUBLAND = HBiomeMaker.makeKnightlyShrublandBiome().setRegistryKey("knightly_shrubland")
   //val RUNED_FLATS = RunedFlatsBiome().setRegistryKey("runed_flats") // todo remove

    // Rainbowland biomes
    val RAINBOW_PLAINS = HBiomeMaker.makeRainbowPlainsBiome().setRegistryKey("rainbowland_plains")

    init {
        FORGE_BUS.addListener(consumer = ::modifyBiomeGen, priority = EventPriority.HIGH)
    }

    fun registerBiomes(biomes: IForgeRegistry<Biome>) {
        biomes.register(RAINBOW_PLAINS)
        biomes.register(CASTLETON_HILLS)
        biomes.register(KNIGHTLY_SHRUBLAND)
        //biomes.register(AUBRUM_WASTELAND)
        //biomes.register(AUBRUM_MOUNTAINS)
        //biomes.register(GOLDEN_FOREST)
        //biomes.register(AURI_PLAINS)

        biomes.register(RUNED_FLATS)
        biomes.register(THICK_FOREST)
        biomes.register(MUSHROOM_CLIFFS)

        HStructures.MUSHROOM_HUT.addToBiome(Biomes.MUSHROOM_FIELDS)
    }

    fun modifyBiomeGen(event: BiomeLoadingEvent) {
        if (event.name ==)
    }

    fun postBiomeRegistry() {
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, BiomeManager.BiomeEntry(THICK_FOREST, 6))
        BiomeDictionary.addTypes(MUSHROOM_CLIFFS, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.RARE, BiomeDictionary.Type.OVERWORLD)
    }
}