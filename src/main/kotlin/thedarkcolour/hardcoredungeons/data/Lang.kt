package thedarkcolour.hardcoredungeons.data

import net.minecraft.data.DataGenerator
import net.minecraft.item.Item
import net.minecraftforge.common.data.LanguageProvider
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems

/**
 * Language data provider for Hardcore Dungeons.
 *
 * Currently includes American English and Latin American Spanish.
 *
 * @author TheDarkColour
 */
abstract class Lang(gen: DataGenerator, locale: String) : LanguageProvider(gen, HardcoreDungeons.ID, locale) {
    /**
     * American English. todo sort like in HBlocks/HItems
     *
     * @author TheDarkColour
     */
    class English(gen: DataGenerator) : Lang(gen, "en_us") {
        override fun addTranslations() {
            add("itemGroup.hardcoredungeons", "Hardcore Dungeons")
/*
            // Overworld biomes
            add(HBiomes.THICK_FOREST, "Thick Forest")
            add(HBiomes.MUSHROOM_CLIFFS, "Mushroom Cliffs")

             //Aubrum biomes
            add(HBiomes.AUBRUM_WASTELAND, "Aubrum Wasteland")
            add(HBiomes.GOLDEN_FOREST, "Golden Forest")
            add(HBiomes.AUBRUM_MOUNTAINS, "Aubrum Mountains")
            add(HBiomes.AURI_PLAINS, "Auri Plains")


            // Castleton biomes
            add(HBiomes.CASTLETON_HILLS, "Castleton Hills")
            add(HBiomes.KNIGHTLY_SHRUBLAND, "Knightly Shrubland")
            // add(HBiomes.RUNED_FLATS, "Runed Flats")

            // Rainbowland biomes
            add(HBiomes.RAINBOW_PLAINS, "Rainbow Plains")
*/
            add(HBlocks.VASE, "Vase")
            add(HBlocks.SHROOMY_COBBLESTONE, "Shroomy Cobblestone")
            add(HBlocks.SHROOMY_COBBLESTONE_SLAB, "Shroomy Cobblestone Slab")
            add(HBlocks.SHROOMY_COBBLESTONE_STAIRS, "Shroomy Cobblestone Stairs")
            add(HBlocks.SHROOMY_STONE_BRICKS, "Shroomy Stone Bricks")
            add(HBlocks.SHROOMY_STONE_BRICK_SLAB, "Shroomy Stone Brick Slab")
            add(HBlocks.SHROOMY_STONE_BRICK_STAIRS, "Shroomy Stone Brick Stairs")
            add(HBlocks.SHROOMY_VASE, "Shroomy Vase")
            add(HBlocks.ASHY_FARMLAND, "Ashy Farmland")
            add(HBlocks.SANDY_FARMLAND, "Sandy Farmland")
            add(HBlocks.CASTLETON_SOIL, "Castleton Soil")
            add(HBlocks.CASTLETON_GRASS_BLOCK, "Castleton Grass")
            add(HBlocks.CASTLETON_LOAM, "Castleton Loam")
            add(HBlocks.CASTLETON_STONE, "Castleton Stone")
            add(HBlocks.CASTLETON_BRICKS, "Castleton Bricks")
            add(HBlocks.CASTLETON_BRICK_STAIRS, "Castleton Brick Stairs")
            add(HBlocks.CASTLETON_BRICK_SLAB, "Castleton Brick Slab")
            add(HBlocks.CASTLETON_BRICK_FENCE, "Castleton Brick Fence")
            add(HBlocks.CASTLETON_BRICK_WALL, "Castleton Brick Wall")
            add(HBlocks.CRACKED_CASTLETON_BRICKS, "Cracked Castleton Bricks")
            add(HBlocks.CHARGED_CASTLETON_BRICKS, "Charged Castleton Bricks")
            add(HBlocks.CHARGED_CASTLETON_BRICK_STAIRS, "Charged Castleton Brick Stairs")
            add(HBlocks.CHARGED_CASTLETON_BRICK_SLAB, "Charged Castleton Brick Slab")
            add(HBlocks.CHARGED_CASTLETON_BRICK_FENCE, "Charged Castleton Brick Fence")
            add(HBlocks.CHARGED_CASTLETON_BRICK_WALL, "Charged Castleton Brick Wall")
            add(HBlocks.CASTLETON_PORTAL_FRAME, "Castleton Portal Frame")
            add(HBlocks.CASTLETON_PORTAL, "Castleton Portal")
            add(HBlocks.PURPLE_CASTLETON_LAMP, "Purple Castleton Lamp")
            add(HBlocks.LUMLIGHT_SAPLING, "Lumlight Sapling")
            add(HBlocks.LUMLIGHT_LOG, "Lumlight Log")
            add(HBlocks.LUMLIGHT_WOOD, "Lumlight Wood")
            add(HBlocks.STRIPPED_LUMLIGHT_LOG, "Stripped Lumlight Log")
            add(HBlocks.STRIPPED_LUMLIGHT_WOOD, "Stripped Lumlight Wood")
            add(HBlocks.LUMLIGHT_LEAVES, "Lumlight Leaves")
            add(HBlocks.LUMLIGHT_PLANKS, "Lumlight Planks")
            add(HBlocks.LUMLIGHT_STAIRS, "Lumlight Stairs")
            add(HBlocks.LUMLIGHT_SLAB, "Lumlight Slab")
            add(HBlocks.LUMLIGHT_FENCE, "Lumlight Fence")
            add(HBlocks.LUMLIGHT_FENCE_GATE, "Lumlight Fence Gate")
            add(HBlocks.LUMLIGHT_PRESSURE_PLATE, "Lumlight Pressure Plate")
            add(HBlocks.LUMLIGHT_BUTTON, "Lumlight Button")
            add(HBlocks.LUMLIGHT_DOOR, "Lumlight Door")
            add(HBlocks.LUMLIGHT_TRAPDOOR, "Lumlight Trapdoor")
            add(HBlocks.PURPLE_LUMSHROOM, "Purple Lumshroom")
            add(HBlocks.BLUE_LUMSHROOM, "Blue Lumshroom")
            add(HBlocks.LOST_SKULL, "Lost Skull")
            add(HBlocks.CROWN, "Crown")
            add(HBlocks.CHALICE, "Chalice")
            add(HBlocks.PLATE, "Plate")
            add(HBlocks.FRAYED_SKULL, "Frayed Skull")
            add(HBlocks.RED_WINE_BOTTLE, "Red Wine Bottle")
            add(HBlocks.WHITE_WINE_BOTTLE, "White Wine Bottle")
            add(HBlocks.WINE_BOTTLE, "Wine Bottle")
            add(HBlocks.CASTLETON_LANTERN, "Castleton Lantern")
            add(HBlocks.DUNGEON_MOB_SPAWNER, "Dungeon Mob Spawner")
            add(HBlocks.LUMLIGHT_CAMPFIRE, "Lumlight Campfire")
            add(HBlocks.CASTLETON_VASE, "Castleton Vase")
            add(HBlocks.CASTLETON_TREASURE_VASE, "Castleton Treasure Vase")
            add(HBlocks.LUMLIGHT_BOOKSHELF, "Lumlight Bookshelf")
            add(HBlocks.CANDLE, "Candle")
            add(HBlocks.RUNED_CASTLETON_STONE, "Runed Castleton Stone")

            add(HBlocks.RAINBOW_SOIL, "Rainbow Soil")
            add(HBlocks.RAINBOW_GRASS_BLOCK, "Rainbow Grass Block")
            add(HBlocks.RAINBOW_ROCK, "Rainbow Rock")
            add(HBlocks.RAINBOW_BRICKS, "Rainbow Bricks")
            add(HBlocks.RAINBOW_BRICK_STAIRS, "Rainbow Brick Stairs")
            add(HBlocks.RAINBOW_BRICK_SLAB, "Rainbow Brick Slab")
            add(HBlocks.RAINBOW_BRICK_WALL, "Rainbow Brick Wall")
            add(HBlocks.RAINBOW_BRICK_FENCE, "Rainbow Brick Fence")
            add(HBlocks.RAINBOWLAND_PORTAL_FRAME, "Rainbowland Portal Frame")
            add(HBlocks.RAINBOWLAND_PORTAL, "Rainbowland Portal")
            add(HBlocks.COTTONMARSH_SAPLING, "Cottonmarsh Sapling")
            add(HBlocks.COTTONMARSH_LEAVES, "Cottonmarsh Leaves")
            add(HBlocks.COTTONMARSH_LOG, "Cottonmarsh Log")
            add(HBlocks.COTTONMARSH_WOOD, "Cottonmarsh Wood")
            add(HBlocks.STRIPPED_COTTONMARSH_LOG, "Stripped Cottonmarsh Log")
            add(HBlocks.STRIPPED_COTTONMARSH_WOOD, "Stripped Cottonmarsh Wood")
            add(HBlocks.RAINBOW_FACTORY_BRICKS, "Rainbow Factory Bricks")
            add(HBlocks.RAINBOW_FACTORY_BRICK_STAIRS, "Rainbow Factory Brick Stairs")
            add(HBlocks.RAINBOW_FACTORY_BRICK_SLAB, "Rainbow Factory Brick Slab")
            add(HBlocks.RAINBOW_FACTORY_BRICK_WALL, "Rainbow Factory Brick Wall")
            add(HBlocks.RAINBOW_FACTORY_BRICK_FENCE, "Rainbow Factory Brick Fence")
            add(HBlocks.RAINBOW_FACTORY_FURNACE, "Rainbow Factory Furnace")
            add(HBlocks.RAINBOW_GLASS, "Rainbow Glass")
            add(HBlocks.RAINBOW_GLASS_PANE, "Rainbow Glass Pane")
            add(HBlocks.RAINBOWSTONE_ORE, "Rainbowstone Ore")
            add(HBlocks.RAINBOWSTONE_BLOCK, "Rainbowstone Block")
            add(HBlocks.RAINBOW_FARMLAND, "Rainbowstone Farmland")

            add(HBlocks.FLAME_ROSE, "Flame Rose")
            add(HBlocks.GOLDEN_TULIP, "Golden Tulip")
            add(HBlocks.AUBRUM_PORTAL, "Aubrum Portal")
            add(HBlocks.AURISOIL, "Aurisoil")
            add(HBlocks.AURIGRASS_BLOCK, "Aurigrass Block")
            add(HBlocks.AURILOAM, "Auriloam")
            add(HBlocks.AUBRUM_ROCK, "Aubrum Rock")
            add(HBlocks.DRUM, "Drum")
            add(HBlocks.AURI_LOG, "Auri Log")
            add(HBlocks.AURI_WOOD, "Auri Wood")
            add(HBlocks.STRIPPED_AURI_LOG, "Stripped Auri Log")
            add(HBlocks.STRIPPED_AURI_WOOD, "Stripped Auri Wood")
            add(HBlocks.AURI_PLANKS, "Auri Planks")
            add(HBlocks.AURI_STAIRS, "Auri Stairs")
            add(HBlocks.AURI_SLAB, "Auri Slab")
            add(HBlocks.SCRAP_METAL, "Scrap Metal")
            add(HBlocks.AURIGOLD_ORE, "Aurigold Ore")

            add(HItems.SHROOMY_SWORD, "Shroomy Sword")
            add(HItems.FILL_WAND, "Fill Wand")
            add(HItems.CLEAR_WAND, "Clear Wand")
            add("item.hardcoredungeons.wand.clear", "Cleared blocks from")
            add("item.hardcoredungeons.wand.fill", "Filled blocks from")
            add("lang.hardcoredungeons.to", "to")
            addLore(HItems.SHROOMY_SWORD, "Has a chance to inflict poison on the target.")
        }
    }

    /**
     * Latin American Spanish.
     *
     * Classname doesn't matter because this
     * is strictly for use in dev environment.
     *
     * @author TheDarkColour
     */
    @Suppress("ClassName", "NonAsciiCharacters")
    class Español(gen: DataGenerator) : Lang(gen, "es_mx") {
        override fun addTranslations() {
            add("itemGroup.hardcoredungeons", "Hardcore Dungeons")
            add(HBlocks.FLAME_ROSE, "Rosa del fuego")
            add(HBlocks.GOLDEN_TULIP, "Tulipán de oro")
            add(HBlocks.CASTLETON_SOIL, "Tierra de Castleton")
            add(HBlocks.CASTLETON_GRASS_BLOCK, "Pasto de Castleton")
            //add(HBlocks.SHROOMY_COBBLESTONE, "Shroomy Cobblestone")
            //add(HBlocks.SHROOMY_COBBLESTONE_SLAB, "Shroomy Cobblestone Slab")
            //add(HBlocks.SHROOMY_COBBLESTONE_STAIRS, "Shroomy Cobblestone Stairs")
            add(HBlocks.SHROOMY_STONE_BRICKS, "Ladrillos de piedra hongosa")
            //add(HBlocks.SHROOMY_STONE_BRICK_SLAB, "Shroomy Stone Brick Slab")
            //add(HBlocks.SHROOMY_STONE_BRICK_STAIRS, "Shroomy Stone Brick Stairs")
            add(HBlocks.LUMLIGHT_LEAVES, "Hojas de lumlight")
            add(HBlocks.PURPLE_CASTLETON_LAMP, "Lámpara púrpura de Castleton")
            //add(HBlocks.COTTONMARSH_LEAVES, "Cottonmarsh Leaves")
            //add(HBlocks.RAINBOW_BRICKS, "Rainbow Bricks")
            //add(HBlocks.LUMLIGHT_WOOD, "Lumlight Wood")
            add(HBlocks.LUMLIGHT_STAIRS, "Escaleras de lumlight")
            //add(HBlocks.LUMLIGHT_SLAB, "Lumlight Slab")
            //add(HBlocks.PURPLE_LUMSHROOM, "Purple Lumshroom")
            //add(HBlocks.COTTONMARSH_WOOD, "Cottonmarsh Wood")
            //add(HBlocks.RAINBOW_FACTORY_BRICKS, "Rainbow Factory Bricks")
            //add(HBlocks.RAINBOW_FACTORY_FURNACE, "Rainbow Factory Furnace")
            //add(HBlocks.RAINBOW_ROCK, "Rainbow Rock")
            add(HBlocks.CASTLETON_LANTERN, "Farol de Castleton")
            add("item.hardcoredungeons.wand.clear", "Despejó bloques de")
            add("item.hardcoredungeons.wand.fill", "Llenó bloques de")
            add("lang.hardcoredungeons.to", "a")
        }
    }

    /**
     * Adds a translation for an item that has lore.
     */
    protected fun addLore(item: Item, translation: String) {
        add(item.translationKey + ".lore", translation)
    }
}