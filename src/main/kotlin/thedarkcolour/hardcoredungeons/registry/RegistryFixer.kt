package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.biome.Biome
import net.minecraftforge.event.RegistryEvent

/**
 * Handles missing registry entries.
 *
 * If a mapping remaps to a null value then the missing mapping is ignored.
 *
 * @author TheDarkColour
 */
object RegistryFixer {
    val BIOME_MAPPINGS = hashMapOf<String, String?>().also { map ->
        map["midknight_plains"] = "castleton_hills"
        map["runed_flats"] = null
    }

    fun remapBiomes(e: RegistryEvent.MissingMappings<Biome>) {
        e.mappings
    }

    fun fixMappings(mappings: HashMap<String, String>) {}
}