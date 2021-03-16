package thedarkcolour.hardcoredungeons.registry

import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.potion.Effect
import net.minecraft.tileentity.TileEntityType
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraftforge.event.RegistryEvent.MissingMappings
import net.minecraftforge.registries.DataSerializerEntry
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

    // Add our event listeners
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
            map["cheeky_teleporter_top"] = null
            map["cheeky_teleporter"] = null
            map["stone_texture"] = null
            map["stone_texture_stairs"] = null
            map["stone_texture_slab"] = null
            map["dungeon_mob_spawner"] = HBlocks.DUNGEON_SPAWNER
        }
        createFixes<Effect> { map ->
            map["rage"] = null
        }
        createFixes<Item> { map ->
            map["rainbow_factory_table"] = null
            map["extractor"] = null
            map["table"] = null
            map["roasted_corn"] = null
            map["corn"] = null
            map["cheeky_teleporter_top"] = null
            map["cheeky_teleporter"] = null
            map["stone_texture"] = null
            map["stone_texture_stairs"] = null
            map["stone_texture_slab"] = null
            map["lum"] = null
            map["volatile_lum"] = null
            // don't worry about wiping because this is a creative item
            map["dungeon_mob_spawner"] = null
        }
        createFixes<TileEntityType<*>> { map ->
            map["extractor"] = null
            map["lumlight_campfire"] = null
        }
        createFixes<ContainerType<*>> { map ->
            map["extractor"] = null
            map["dungeon_loot"] = null
        }
        createFixes<EntityType<*>> { map ->
            map["cheeky"] = null
            map["rainbowland_sheep"] = null
        }
        createFixes<SurfaceBuilder<*>> { map ->
            map["forest_of_lakes"] = HSurfaceBuilders.THICK_FOREST
        }
        createFixes<DataSerializerEntry> { map ->
            map["deer_pattern"] = HDataSerializers.DEER_TYPE
        }
    }

    /**
     * Make a map of registry fixes and register them to the event bus.
     *
     * @param exhaustive whether to check all namespaces instead of just `hardcoredungeons`
     * @param createMappings a function that takes in a new map and contains mappings for missing registry entries
     */
    private inline fun <reified T : IForgeRegistryEntry<T>> createFixes(exhaustive: Boolean = false, createMappings: (MutableMap<String, T?>) -> Unit) {
        val map = HashMap<String, T?>()

        createMappings(map)

        MOD_BUS.addGenericListener { event: MissingMappings<T> ->
            fixMappings(event, map, exhaustive)
        }
    }

    /**
     * Checks through the missing mappings and remaps each
     * missing mapping that has a corresponding key in `newMappings`.
     *
     * @param event the missing mappings registry event with a list of missing mappings
     * @param newMappings a map of old mappings to new values
     * @param exhaustive whether to check every namespace for missing mappings
     */
    private fun <T : IForgeRegistryEntry<T>> fixMappings(event: MissingMappings<T>, newMappings: Map<String, T?>, exhaustive: Boolean = false) {
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