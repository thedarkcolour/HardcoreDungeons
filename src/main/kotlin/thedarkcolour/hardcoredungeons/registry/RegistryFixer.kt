package thedarkcolour.hardcoredungeons.registry

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraft.world.biome.Biome
import net.minecraftforge.event.RegistryEvent.MissingMappings
import net.minecraftforge.registries.IForgeRegistryEntry
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * Handles missing registry entries.
 *
 * If a mapping remaps to a null value then the missing mapping is ignored.
 *
 * @author TheDarkColour
 */
object RegistryFixer {

    fun init() {
        createFixes<Biome> { map ->
            map["midknight_plains"] = HBiomes.CASTLETON_HILLS
            map["runed_flats"] = null
        }
        createFixes<Block> { map ->
            map["corn"] = null
            map["shroomy_bricks"] = HBlocks.SHROOMY_STONE_BRICKS
            map["rainbow_factory_table"] = null
            map["table"] = null
            map["extractor"] = null
        }
        createFixes<Item> { map ->
            map["rainbow_factory_table"] = null
            map["extractor"] = null
            map["table"] = null
            map["roasted_corn"] = null
            map["corn"] = null
        }
        createFixes<TileEntityType<*>> { map ->
            map["extractor"] = null
        }
    }

    private inline fun <reified T : IForgeRegistryEntry<T>> createFixes(exhaustive: Boolean = false, createMappings: (MutableMap<String, T?>) -> Unit) {
        val map = HashMap<String, T?>()

        createMappings(map)

        MOD_BUS.addGenericListener { event: MissingMappings<T> ->
            fixMappings(event, map, exhaustive)
        }
    }

    fun <T : IForgeRegistryEntry<T>> fixMappings(event: MissingMappings<T>, newMappings: Map<String, T?>, exhaustive: Boolean = false) {
        for (mapping in if (exhaustive) event.allMappings else event.mappings) {
            val path = mapping.key.path

            if (newMappings.contains(path)) {
                val newValue = newMappings[path]

                if (newValue == null) {
                    mapping.ignore()
                } else {
                    mapping.remap(newValue)
                }
            }
        }
    }
}