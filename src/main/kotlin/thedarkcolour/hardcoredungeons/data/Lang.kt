package thedarkcolour.hardcoredungeons.data

import net.minecraft.data.DataGenerator
import net.minecraft.world.item.Item
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.ItemLike
import net.minecraftforge.common.data.LanguageProvider
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.combo.StairsSlabWallCombo
import thedarkcolour.hardcoredungeons.registry.HEnchantments
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.*

/**
 * Language data provider for Hardcore Dungeons.
 *
 * Currently includes American English and Latin American Spanish.
 *
 * @author thedarkcolour
 */
abstract class Lang(gen: DataGenerator, locale: String) : LanguageProvider(gen, HardcoreDungeons.ID, locale) {
    /**
     * American English.
     *
     * @author thedarkcolour
     */
    class English(gen: DataGenerator) : Lang(gen, "en_us") {
        override fun addTranslations() {
            add("itemGroup.hardcoredungeons", "§fHardcore Dungeons")
/*
            // Overworld biomes
            add(HBiomes.THICK_FOREST, "Thick Forest")
            add(HBiomes.MUSHROOM_CLIFFS, "Mushroom Cliffs")

            // Aubrum biomes
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
            add(HBlocks.SHROOMY_COBBLESTONE.block, "Shroomy Cobblestone")
            add(HBlocks.SHROOMY_COBBLESTONE.slab, "Shroomy Cobblestone Slab")
            add(HBlocks.SHROOMY_COBBLESTONE.stairs, "Shroomy Cobblestone Stairs")
            add(HBlocks.SHROOMY_COBBLESTONE.wall, "Shroomy Cobblestone Wall")
            add(HBlocks.SHROOMY_STONE_BRICKS.block, "Shroomy Stone Bricks")
            add(HBlocks.SHROOMY_STONE_BRICKS.slab, "Shroomy Stone Brick Slab")
            add(HBlocks.SHROOMY_STONE_BRICKS.stairs, "Shroomy Stone Brick Stairs")
            add(HBlocks.SHROOMY_STONE_BRICKS.wall, "Shroomy Stone Brick Wall")
            add(HBlocks.SHROOMY_VASE, "Shroomy Vase")
            add(HBlocks.ASHY_FARMLAND, "Ashy Farmland")
            add(HBlocks.SANDY_FARMLAND, "Sandy Farmland")

            add(HBlocks.CASTLETON_SOIL.soil, "Castleton Soil")
            add(HBlocks.CASTLETON_SOIL.grass, "Castleton Grass")
            add(HBlocks.CASTLETON_SOIL.loam, "Castleton Loam")
            add(HBlocks.CASTLETON_STONE.stone.block, "Castleton Stone")
            add(HBlocks.CASTLETON_STONE.polished.block, "Polished Castleton Stone")
            add(HBlocks.CASTLETON_STONE.brick.block, "Castleton Bricks")
            add(HBlocks.CASTLETON_STONE.brick.stairs, "Castleton Brick Stairs")
            add(HBlocks.CASTLETON_STONE.brick.slab, "Castleton Brick Slab")
            add(HBlocks.CASTLETON_STONE.brick.fence, "Castleton Brick Fence")
            add(HBlocks.CASTLETON_STONE.brick.wall, "Castleton Brick Wall")
            add(HBlocks.CRACKED_CASTLETON_BRICKS, "Cracked Castleton Bricks")
            add(HBlocks.CHARGED_CASTLETON_BRICKS.block, "Charged Castleton Bricks")
            add(HBlocks.CHARGED_CASTLETON_BRICKS.stairs, "Charged Castleton Brick Stairs")
            add(HBlocks.CHARGED_CASTLETON_BRICKS.slab, "Charged Castleton Brick Slab")
            add(HBlocks.CHARGED_CASTLETON_BRICKS.fence, "Charged Castleton Brick Fence")
            add(HBlocks.CHARGED_CASTLETON_BRICKS.wall, "Charged Castleton Brick Wall")
            add(HBlocks.CASTLETON_PORTAL.frame, "Castleton Portal Frame")
            add(HBlocks.CASTLETON_PORTAL.portal, "Castleton Portal")
            add(HBlocks.PURPLE_CASTLETON_LAMP, "Purple Castleton Lamp")
            add(HBlocks.LUMLIGHT_WOOD.sapling.plant, "Lumlight Sapling")
            add(HBlocks.LUMLIGHT_WOOD.log, "Lumlight Log")
            add(HBlocks.LUMLIGHT_WOOD.wood, "Lumlight Wood")
            add(HBlocks.LUMLIGHT_WOOD.strippedLog, "Stripped Lumlight Log")
            add(HBlocks.LUMLIGHT_WOOD.strippedWood, "Stripped Lumlight Wood")
            add(HBlocks.LUMLIGHT_WOOD.leaves, "Lumlight Leaves")
            add(HBlocks.LUMLIGHT_WOOD.planks, "Lumlight Planks")
            add(HBlocks.LUMLIGHT_WOOD.stairs, "Lumlight Stairs")
            add(HBlocks.LUMLIGHT_WOOD.slab, "Lumlight Slab")
            add(HBlocks.LUMLIGHT_WOOD.fence, "Lumlight Fence")
            add(HBlocks.LUMLIGHT_WOOD.fenceGate, "Lumlight Fence Gate")
            add(HBlocks.LUMLIGHT_WOOD.pressurePlate, "Lumlight Pressure Plate")
            add(HBlocks.LUMLIGHT_WOOD.button, "Lumlight Button")
            add(HBlocks.LUMLIGHT_WOOD.trapdoor, "Lumlight Trapdoor")
            add(HBlocks.LUMLIGHT_CAMPFIRE, "Lumlight Campfire")
            add(HBlocks.CASTLETON_VASE, "Castleton Vase")
            add(HBlocks.CASTLETON_TREASURE_VASE, "Castleton Treasure Vase")
            add(HBlocks.LUMLIGHT_BOOKSHELF, "Lumlight Bookshelf")
            add(HBlocks.CANDLE, "Candle")
            add(HBlocks.RUNED_CASTLETON_STONE, "Runed Castleton Stone")
            add(HBlocks.BLUE_CASTLETON_DUNGEON_LOCK, "Blue Castleton Dungeon Lock")
            //add(HBlocks.DIAMOND_CRYSTAL.crystal, "Diamond Crystal")
            add(HBlocks.MALACHITE_BLOCK, "Malachite Block")
            add(HBlocks.SPRUCE_PLANKS.variants[0](), "Large Spruce Planks")
            add(HBlocks.MALACHITE_CRYSTAL.crystal, "Malachite Crystal")

            add(HBlocks.RAINBOW_SOIL.soil, "Rainbow Soil")
            add(HBlocks.RAINBOW_SOIL.grass, "Rainbow Grass Block")
            add(HBlocks.RAINBOW_ROCK.stone.block, "Rainbow Rock")
            add(HBlocks.RAINBOW_ROCK.brick.block, "Rainbow Bricks")
            add(HBlocks.RAINBOW_ROCK.brick.stairs, "Rainbow Brick Stairs")
            add(HBlocks.RAINBOW_ROCK.brick.slab, "Rainbow Brick Slab")
            add(HBlocks.RAINBOW_ROCK.brick.wall, "Rainbow Brick Wall")
            add(HBlocks.RAINBOW_ROCK.brick.fence, "Rainbow Brick Fence")
            add(HBlocks.RAINBOWLAND_PORTAL.frame, "Rainbowland Portal Frame")
            add(HBlocks.RAINBOWLAND_PORTAL.portal, "Rainbowland Portal")
            add(HBlocks.COTTONMARSH_WOOD.sapling.plant, "Cottonmarsh Sapling")
            add(HBlocks.COTTONMARSH_WOOD.leaves, "Cottonmarsh Leaves")
            add(HBlocks.COTTONMARSH_WOOD.log, "Cottonmarsh Log")
            add(HBlocks.COTTONMARSH_WOOD.wood, "Cottonmarsh Wood")
            add(HBlocks.COTTONMARSH_WOOD.strippedLog, "Stripped Cottonmarsh Log")
            add(HBlocks.COTTONMARSH_WOOD.strippedWood, "Stripped Cottonmarsh Wood")
            add(HBlocks.RAINBOW_FACTORY_BRICKS.block, "Rainbow Factory Bricks")
            add(HBlocks.RAINBOW_FACTORY_BRICKS.stairs, "Rainbow Factory Brick Stairs")
            add(HBlocks.RAINBOW_FACTORY_BRICKS.slab, "Rainbow Factory Brick Slab")
            add(HBlocks.RAINBOW_FACTORY_BRICKS.wall, "Rainbow Factory Brick Wall")
            add(HBlocks.RAINBOW_FACTORY_BRICKS.fence, "Rainbow Factory Brick Fence")
            add(HBlocks.RAINBOW_FACTORY_FURNACE, "Rainbow Factory Furnace")
            add(HBlocks.RAINBOW_GLASS, "Rainbow Glass")
            add(HBlocks.RAINBOW_GLASS_PANE, "Rainbow Glass Pane")
            add(HBlocks.RAINBOWSTONE_ORE, "Rainbowstone Ore")
            add(HBlocks.RAINBOWSTONE_BLOCK, "Rainbowstone Block")
            add(HBlocks.RAINBOW_FARMLAND, "Rainbowstone Farmland")

            add(HBlocks.AUBRUM_PORTAL.portal, "Aubrum Portal")
            add(HBlocks.AURISOIL.soil, "Aurisoil")
            add(HBlocks.AURISOIL.grass, "Aurigrass Block")
            add(HBlocks.AURISOIL.loam, "Auriloam")
            add(HBlocks.FLAME_ROSE.plant, "Flame Rose")
            add(HBlocks.GOLDEN_TULIP.plant, "Golden Tulip")
            add(HBlocks.AUBRUM_ROCK, "Aubrum Rock")
            add(HBlocks.DRUM, "Drum")
            add(HBlocks.AURI_WOOD.log, "Auri Log")
            add(HBlocks.AURI_WOOD.wood, "Auri Wood")
            add(HBlocks.AURI_WOOD.strippedLog, "Stripped Auri Log")
            add(HBlocks.AURI_WOOD.strippedWood, "Stripped Auri Wood")
            add(HBlocks.AURI_WOOD.planks, "Auri Planks")
            add(HBlocks.AURI_WOOD.stairs, "Auri Stairs")
            add(HBlocks.AURI_WOOD.slab, "Auri Slab")
            add(HBlocks.SCRAP_METAL, "Scrap Metal")
            add(HBlocks.AURIGOLD_ORE, "Aurigold Ore")

            add(HBlocks.CANDY_CANE_BLOCK, "Candy Cane Block")
            add(HBlocks.BENT_CANDY_CANE_BLOCK, "Bent Candy Cane Block")
            add(HBlocks.SUGARY_SOIL.soil, "Sugary Soil")
            add(HBlocks.SUGARY_SOIL.grass, "Sugary Grass Block")
            add(HBlocks.SUGAR_BLOCK, "Sugar Block")
            add(HBlocks.CHOCOLATE_BLOCK.block, "Chocolate Block")
            add(HBlocks.CHOCOLATE_BLOCK.stairs, "Chocolate Stairs")
            add(HBlocks.CHOCOLATE_BLOCK.slab, "Chocolate Slab")

            add(HBlocks.LUMLIGHT_WOOD.door, "Lumlight Door")
            add(HBlocks.PURPLE_LUMSHROOM.plant, "Purple Lumshroom")
            add(HBlocks.BLUE_LUMSHROOM.plant, "Blue Lumshroom")
            add(HBlocks.LOST_SKULL, "Lost Skull")
            add(HBlocks.CROWN, "Crown")
            add(HBlocks.CHALICE, "Chalice")
            add(HBlocks.PLATE, "Plate")
            add(HBlocks.FRAYED_SKULL, "Frayed Skull")
            add(HBlocks.RED_WINE_BOTTLE, "Red Wine Bottle")
            add(HBlocks.WHITE_WINE_BOTTLE, "White Wine Bottle")
            add(HBlocks.WINE_BOTTLE, "Wine Bottle")
            add(HBlocks.CASTLETON_LANTERN, "Castleton Lantern")

            add(HBlocks.GREEN_GUMDROP.plant, "Green Gumdrop")
            add(HBlocks.MINI_GREEN_GUMDROP.plant, "Mini Green Gumdrop")
            add(HBlocks.PINK_GUMDROP.plant, "Pink Gumdrop")
            add(HBlocks.MINI_PINK_GUMDROP.plant, "Mini Pink Gumdrop")
            add(HBlocks.BLUE_GUMDROP.plant, "Blue Gumdrop")
            add(HBlocks.MINI_BLUE_GUMDROP.plant, "Mini Blue Gumdrop")
            add(HBlocks.PURPLE_GUMDROP.plant, "Purple Gumdrop")
            add(HBlocks.MINI_PURPLE_GUMDROP.plant, "Mini Purple Gumdrop")
            add(HBlocks.RED_GUMDROP.plant, "Red Gumdrop")
            add(HBlocks.MINI_RED_GUMDROP.plant, "Mini Red Gumdrop")
            add(HBlocks.YELLOW_GUMDROP.plant, "Yellow Gumdrop")
            add(HBlocks.MINI_YELLOW_GUMDROP.plant, "Mini Yellow Gumdrop")
            add(SHROOMY_SWORD_ITEM, "Shroomy Sword")
            add(MINI_PISTOL_ITEM, "Mini Pistol")

            add(SYRINGE_ITEM, "Syringe")
            add("item.hardcoredungeons.potion_syringe.effect.empty", "Uncraftable Syringe")
            add("item.hardcoredungeons.potion_syringe.effect.water", "Water Syringe")
            add("item.hardcoredungeons.potion_syringe.effect.mundane", "Mundane Syringe")
            add("item.hardcoredungeons.potion_syringe.effect.thick", "Thick Syringe")
            add("item.hardcoredungeons.potion_syringe.effect.awkward", "Awkward Syringe")
            add("item.hardcoredungeons.potion_syringe.effect.night_vision", "Syringe of Night Vision")
            add("item.hardcoredungeons.potion_syringe.effect.invisibility", "Syringe of Invisibility")
            add("item.hardcoredungeons.potion_syringe.effect.leaping", "Syringe of Leaping")
            add("item.hardcoredungeons.potion_syringe.effect.fire_resistance", "Syringe of Fire Resistance")
            add("item.hardcoredungeons.potion_syringe.effect.swiftness", "Syringe of Swiftness")
            add("item.hardcoredungeons.potion_syringe.effect.slowness", "Syringe of Slowness")
            add("item.hardcoredungeons.potion_syringe.effect.water_breathing", "Syringe of Water Breathing")
            add("item.hardcoredungeons.potion_syringe.effect.healing", "Syringe of Healing")
            add("item.hardcoredungeons.potion_syringe.effect.harming", "Syringe of Harming")
            add("item.hardcoredungeons.potion_syringe.effect.poison", "Syringe of Poison")
            add("item.hardcoredungeons.potion_syringe.effect.regeneration", "Syringe of Regeneration")
            add("item.hardcoredungeons.potion_syringe.effect.strength", "Syringe of Strength")
            add("item.hardcoredungeons.potion_syringe.effect.weakness", "Syringe of Weakness")
            add("item.hardcoredungeons.potion_syringe.effect.levitation", "Syringe of Levitation")
            add("item.hardcoredungeons.potion_syringe.effect.luck", "Syringe of Luck")
            add("item.hardcoredungeons.potion_syringe.effect.turtle_master", "Syringe of Turtle Master")
            add("item.hardcoredungeons.potion_syringe.effect.slow_falling", "Syringe of Slow Falling")

            add(DEER_ANTLER_ITEM, "Deer Antler")
            add(VENISON_ITEM, "Venison")
            add(COOKED_VENISON_ITEM, "Cooked Venison")
            add(DEER_SPAWN_EGG_ITEM, "Deer Spawn Egg")

            add(CASTLETON_SWORD_ITEM, "Castleton Sword")
            add(CASTLE_GEM_ITEM, "Castle Gem")
            add(FRAYED_SOUL_SPAWN_EGG_ITEM, "Frayed Soul Spawn Egg")
            add(CASTLETON_DEER_SPAWN_EGG_ITEM, "Castleton Deer Spawn Egg")
            add(KNIGHTLY_JUGGERNAUT_SPAWN_EGG_ITEM, "Knightly Juggernaut Spawn Egg")
            add(HBlocks.CASTLETON_TORCH.litItem, "Castleton Torch")
            add(HBlocks.CASTLETON_TORCH.burntItem, "Burnt Castleton Torch")
            add(HBlocks.LUMLIGHT_WOOD.sign, "Lumlight Sign")
            add(TOWER_HELMET_ITEM, "Tower Helmet")
            add(TOWER_CHESTPLATE_ITEM, "Tower Chestplate")
            add(TOWER_LEGGINGS_ITEM, "Tower Leggings")
            add(TOWER_BOOTS_ITEM, "Tower Boots")
            add(CASTLETON_STAFF_ITEM, "Castleton Staff")
            add(HBlocks.WILD_BERROOK, "Wild Berrook")
            add(MUSHROOM_CAP_ITEM, "Mushroom Cap")
            add(MUSHROOM_CHESTPLATE_ITEM, "Mushroom Chestplate")
            add(MALACHITE_SWORD_ITEM, "Malachite Sword")
            add(MALACHITE_SHOVEL_ITEM, "Malachite Shovel")
            add(MALACHITE_PICKAXE_ITEM, "Malachite Pickaxe")
            add(MALACHITE_AXE_ITEM, "Malachite Axe")
            add(MALACHITE_HOE_ITEM, "Malachite Hoe")

            add(RAINBOWSTONE_AXE_ITEM, "Rainbowstone Axe")
            add(RAINBOWSTONE_HOE_ITEM, "Rainbowstone Hoe")
            add(RAINBOWSTONE_PICKAXE_ITEM, "Rainbowstone Pickaxe")
            add(RAINBOWSTONE_SHOVEL_ITEM, "Rainbowstone Shovel")
            add(RAINBOWSTONE_SWORD_ITEM, "Rainbowstone Sword")
            add(RAINBOWSTONE_GEM_ITEM, "Rainbowstone Gem")

            add(AUBRI_MINI_PISTOL_ITEM, "Aubri Mini Pistol")
            add(AUBRI_RIFLE_ITEM, "Aubri Rifle")
            add(AURIGOLD_ITEM, "Aurigold")
            add(AURIGOLD_PENDANT_ITEM, "Aurigold Pendant")

            add(CANDY_CANE_SWORD_ITEM, "Candy Cane Sword")
            add(CANDY_CANE_ITEM, "Candy Cane")

            add(HBlocks.CHILI_PEPPER, "Chili Pepper")
            add(BULLET_ITEM, "Bullet")
            add(INCENDIARY_BULLET_ITEM, "Incendiary Bullet")
            add(SHELL_ITEM, "Shell")

            add(FILL_WAND_ITEM, "Fill Wand")
            add(CLEAR_WAND_ITEM, "Clear Wand")
            add(CLONE_WAND_ITEM, "Clone Wand")
            add(DISTANCE_WAND_ITEM, "Distance Wand")

            add("item.hardcoredungeons.wand.clear", "Cleared blocks from")
            add("item.hardcoredungeons.wand.fill", "Filled blocks from")
            add("lang.hardcoredungeons.to", "to")
            addLore(SHROOMY_SWORD_ITEM, "Has a chance to inflict poison on the target.")

            addEnchantment(HEnchantments.WITHERING, "Withering", "Causes additional wither damage when used to attack a mob.")
        }

        fun addCombo(combo: StairsSlabWallCombo, block: String) {
            add(combo.block, block)
            add(combo.slab, "Losa de " + block.lowercase())
            add(combo.stairs, "Escaleras de " + block.lowercase())
        }
    }

    /**
     * Mexican Spanish.
     *
     * @author thedarkcolour
     */
    @Suppress("ClassName", "NonAsciiCharacters", "SpellCheckingInspection")
    class Español(gen: DataGenerator) : Lang(gen, "es_mx") {
        override fun addTranslations() {
            add("itemGroup.hardcoredungeons", "§fHardcore Dungeons")
            add(HBlocks.FLAME_ROSE.plant, "Rosa del fuego")
            add(HBlocks.GOLDEN_TULIP.plant, "Tulipán de oro")
            add(HBlocks.CASTLETON_SOIL.soil, "Tierra de Castleton")
            add(HBlocks.CASTLETON_SOIL.grass, "Pasto de Castleton")
            addCombo(HBlocks.SHROOMY_COBBLESTONE, "Piedra hongosa labrada")
            addCombo(HBlocks.SHROOMY_STONE_BRICKS, "Ladrillos de piedra hongosa")
            add(HBlocks.LUMLIGHT_WOOD.leaves, "Hojas de lumlight")
            add(HBlocks.PURPLE_CASTLETON_LAMP, "Lámpara púrpura de Castleton")
            //add(HBlocksNew.COTTONMARSH_LEAVES, "Cottonmarsh Leaves")
            //add(HBlocksNew.RAINBOW_BRICKS, "Rainbow Bricks")
            //add(HBlocksNew.LUMLIGHT_WOOD, "Lumlight Wood")
            add(HBlocks.LUMLIGHT_WOOD.stairs, "Escaleras de lumlight")
            //add(HBlocksNew.LUMLIGHT_SLAB, "Lumlight Slab")
            //add(HBlocksNew.PURPLE_LUMSHROOM, "Purple Lumshroom")
            //add(HBlocksNew.COTTONMARSH_WOOD, "Cottonmarsh Wood")
            //add(HBlocksNew.RAINBOW_FACTORY_BRICKS, "Rainbow Factory Bricks")
            //add(HBlocksNew.RAINBOW_FACTORY_FURNACE, "Rainbow Factory Furnace")
            //add(HBlocksNew.RAINBOW_ROCK, "Rainbow Rock")
            add(HBlocks.CASTLETON_LANTERN, "Farol de Castleton")
            add("item.hardcoredungeons.wand.clear", "Despejó bloques de")
            add("item.hardcoredungeons.wand.fill", "Llenó bloques de")
            add("lang.hardcoredungeons.to", "a")
        }

        fun addCombo(combo: StairsSlabWallCombo, block: String) {
            add(combo.block, block)
            add(combo.slab, "Losa de " + block.lowercase())
            add(combo.stairs, "Escaleras de " + block.lowercase())
        }
    }

    /**
     * Adds a translation for an item that has lore.
     */
    protected fun addLore(item: Item, translation: String) {
        add(item.descriptionId + ".lore", translation)
    }

    protected fun add(item: ItemLike, translation: String) {
        add(item.asItem(), translation)
    }

    protected fun addEnchantment(enchantment: Enchantment, name: String, desc: String) {
        add(enchantment, name)
        add(enchantment.descriptionId + ".desc", desc)
    }
}