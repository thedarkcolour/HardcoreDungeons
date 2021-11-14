package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.level.block.Block
import net.minecraft.client.renderer.color.ItemColors
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.*
import net.minecraft.item.Item.Properties
import net.minecraft.item.crafting.Ingredient
import net.minecraft.potion.PotionBrewing
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.brewing.BrewingRecipeRegistry
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.block.HBlocks
import thedarkcolour.hardcoredungeons.client.color.PotionColor
import thedarkcolour.hardcoredungeons.client.color.RainbowColor
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.item.HArmorMaterial
import thedarkcolour.hardcoredungeons.item.HItemTier
import thedarkcolour.hardcoredungeons.item.ItemMisc
import thedarkcolour.hardcoredungeons.item.castleton.CumChaliceItem
import thedarkcolour.hardcoredungeons.item.debug.ClearWandItem
import thedarkcolour.hardcoredungeons.item.debug.CloneWandItem
import thedarkcolour.hardcoredungeons.item.debug.DistanceWandItem
import thedarkcolour.hardcoredungeons.item.debug.FillWandItem
import thedarkcolour.hardcoredungeons.item.misc.*
import thedarkcolour.hardcoredungeons.item.overworld.MushroomArmorItem
import thedarkcolour.hardcoredungeons.item.overworld.ShroomySwordItem
import thedarkcolour.hardcoredungeons.recipe.SyringeRecipe
import thedarkcolour.hardcoredungeons.util.modLoc

/**
 * Handles item registry and contains references to every `Item` and `BlockItem` in HCD.
 *
 * Categorized by dimension and `BlockItem` instances always go first.
 *
 * @author TheDarkColour, genericrandom64
 */
@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType", "DuplicatedCode")
object HItems {
    // @formatter:off

    // Overworld items
    val SHROOMY_SWORD = ShroomySwordItem(Properties().tab(Group).stacksTo(1).durability(726)).setRegistryKey("shroomy_sword")
    val MUSHROOM_CAP = MushroomArmorItem(EquipmentSlotType.HEAD, Properties().tab(Group).stacksTo(1).durability(567)).setRegistryKey("mushroom_cap")
    val MUSHROOM_CHESTPLATE = MushroomArmorItem(EquipmentSlotType.CHEST, Properties().tab(Group).stacksTo(1).durability(642)).setRegistryKey("mushroom_chestplate")
    val MINI_PISTOL = GunItem(Properties().tab(Group), chargeTime = 22, bulletDamage = 3.0f, drop = 0.0004f).setRegistryKey("mini_pistol")
    val SYRINGE = EmptySyringeItem(Properties().tab(Group).stacksTo(16)).setRegistryKey("syringe")
    val POTION_SYRINGE = SyringeItem(Properties().tab(Group).stacksTo(2)).setRegistryKey("potion_syringe")
    val DEER_ANTLER = genericItem("deer_antler")
    val MALACHITE_CRYSTAL by HBlocks.MALACHITE_CRYSTAL::item//= genericItem("malachite_crystal")
    val DIAMOND_CRYSTAL by HBlocks.DIAMOND_CRYSTAL::item//= genericItem("diamond_crystal")
    val PRISTINE_DIAMOND = genericItem("pristine_diamond")
    val BULLET = genericItem("bullet")
    val INCENDIARY_BULLET = genericItem("incendiary_bullet")
    val SHELL = genericItem("shell")
    val VENISON = Item(Properties().tab(Group).food(Food.Builder().nutrition(3).saturationMod(0.2f).meat().build())).setRegistryKey("venison")
    val COOKED_VENISON = Item(Properties().tab(Group).food(Food.Builder().nutrition(7).saturationMod(0.9f).meat().build())).setRegistryKey("cooked_venison")
    val DEER_SPAWN_EGG = SpawnEggItem(HEntities.DEER, 0x4c2922, 0x202020, Properties().tab(Group)).setRegistryKey("deer_spawn_egg")
    val MALACHITE_SWORD = SwordItem(HItemTier.MALACHITE, 3, -2.4f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_sword")
    val MALACHITE_SHOVEL = ShovelItem(HItemTier.MALACHITE, 1.5f, -3.0f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_shovel")
    val MALACHITE_PICKAXE = MalachitePickaxeItem(HItemTier.MALACHITE, 1, -2.8f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_pickaxe")
    val MALACHITE_AXE = AxeItem(HItemTier.MALACHITE, 5.0f, -3.0f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_axe")
    val MALACHITE_HOE = HoeItem(HItemTier.MALACHITE, -3, 0.0f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_hoe")
    val MALACHITE_SCYTHE = ScytheItem(HItemTier.MALACHITE, 3, 0.0f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_scythe")
    val MUSHROOM_ARCHER_SPAWN_EGG = SpawnEggItem(HEntities.MUSHROOM_ARCHER, 0x434142, 0x2984a3, Properties().tab(Group)).setRegistryKey("knightly_juggernaut_spawn_egg")

    // Castleton items
    val CASTLETON_SWORD = SwordItem(ItemTier.GOLD, 9, -2.4f, Properties().tab(Group).stacksTo(1).rarity(Rarity.RARE)).setRegistryKey("castleton_sword")
    val VALABLADE = SwordItem(ItemTier.GOLD, 7, -2.4f, Properties().tab(Group).stacksTo(1).rarity(Rarity.EPIC)).setRegistryKey("valablade")
    val GILDED_VALABLADE = SwordItem(ItemTier.GOLD, 10, -2.4f, Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)).setRegistryKey("gilded_valablade")
    val CASTLE_GEM = Item(Properties().tab(Group)).setRegistryKey("castle_gem")
    val FRAYED_SOUL_SPAWN_EGG = SpawnEggItem(HEntities.FRAYED_SOUL, 0x434142, 0x2984a3, Properties().tab(Group)).setRegistryKey("frayed_soul_spawn_egg")
    val VOID_RUNNER_SPAWN_EGG = SpawnEggItem(HEntities.VOID_RUNNER, 0x000000, 0x00c8ff, Properties().tab(Group)).setRegistryKey("void_runner_spawn_egg")
    val CASTLETON_DEER_SPAWN_EGG = SpawnEggItem(HEntities.CASTLETON_DEER, 0x434142, 0x2984a3, Properties().tab(Group)).setRegistryKey("castleton_deer_spawn_egg")
    val KNIGHTLY_JUGGERNAUT_SPAWN_EGG = SpawnEggItem(HEntities.KNIGHTLY_JUGGERNAUT, 0x434142, 0x2984a3, Properties().tab(Group)).setRegistryKey("knightly_juggernaut_spawn_egg")
    val CASTLETON_TORCH by HBlocks.CASTLETON_TORCH::litItem//= CastletonTorchItem(HBlocks.CASTLETON_TORCH, HBlocks.CASTLETON_WALL_TORCH, true, Properties().tab(Group)).setRegistryKey("castleton_torch")
    val BURNT_CASTLETON_TORCH by HBlocks.CASTLETON_TORCH::burntItem//= CastletonTorchItem(HBlocks.CASTLETON_TORCH, HBlocks.CASTLETON_WALL_TORCH, false, Properties().tab(Group)).setRegistryKey("burnt_castleton_torch")
    val LUMLIGHT_SIGN by HBlocks.LUMLIGHT_WOOD.sign::item//= SignItem(Properties().stacksTo(16).tab(Group), HBlocks.LUMLIGHT_SIGN, HBlocks.LUMLIGHT_WALL_SIGN).setRegistryKey("lumlight_sign")
    val TOWER_HELMET = ArmorItem(HArmorMaterial.TOWER, EquipmentSlotType.HEAD, Properties().tab(Group).stacksTo(1)).setRegistryKey("tower_helmet")
    val TOWER_CHESTPLATE = ArmorItem(HArmorMaterial.TOWER, EquipmentSlotType.CHEST, Properties().tab(Group).stacksTo(1)).setRegistryKey("tower_chestplate")
    val TOWER_LEGGINGS = ArmorItem(HArmorMaterial.TOWER, EquipmentSlotType.LEGS, Properties().tab(Group).stacksTo(1)).setRegistryKey("tower_leggings")
    val TOWER_BOOTS = ArmorItem(HArmorMaterial.TOWER, EquipmentSlotType.FEET, Properties().tab(Group).stacksTo(1)).setRegistryKey("tower_boots")
    val CASTLETON_STAFF = StaffItem(Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)).setRegistryKey("castleton_staff")
    val WILD_BERROOK by HBlocks.WILD_BERROOK::item//= BlockItem(HBlocks.WILD_BERROOK, Properties().tab(Group).food(ItemMisc.WILD_BERROOK)).setRegistryKey("wild_berrook")
    val BLUE_CASTLETON_DUNGEON_KEY = Item(Properties().tab(Group).stacksTo(1).rarity(Rarity.RARE)).setRegistryKey("blue_castleton_dungeon_key")
    val CUM_CHALICE = CumChaliceItem(Properties().stacksTo(1).food(ItemMisc.CUM_CHALICE)).setRegistryKey("cum_chalice")

    // Rainbowland items
    val RAINBOWSTONE_AXE = AxeItem(ItemTier.GOLD, 11.0f, -3.2f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_axe")
    val RAINBOWSTONE_HOE = HoeItem(ItemTier.GOLD, -1, -1.0f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_hoe")
    val RAINBOWSTONE_PICKAXE = PickaxeItem(ItemTier.GOLD, 1, -2.5f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_pickaxe")
    val RAINBOWSTONE_SHOVEL = ShovelItem(ItemTier.GOLD, 2.0f, -3.0f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_shovel")
    val RAINBOWSTONE_SWORD = SwordItem(ItemTier.GOLD, 9, -2.0f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_sword")
    val RAINBOWSTONE_GEM = Item(Properties().tab(Group)).setRegistryKey("rainbowstone_gem")

    // Aubrum items
    val AUBRI_MINI_PISTOL = GunItem(Properties().tab(Group).stacksTo(1), chargeTime = 22, drop = 0.0004f).setRegistryKey("aubri_mini_pistol")
    val AUBRI_RIFLE = GunItem(Properties().tab(Group).stacksTo(1), chargeTime = 16, velocity = 2.4f, bulletDamage = 4.6f, drop = 0.0001f).setRegistryKey("aubri_rifle")
    val AURIGOLD = Item(Properties().tab(Group)).setRegistryKey("aurigold")
    val AURIGOLD_PENDANT = Item(Properties().tab(Group).stacksTo(1)).setRegistryKey("aurigold_pendant")
    val AURILO_STAFF = StaffItem(Properties().tab(Group).stacksTo(1)).setRegistryKey("aurilo_staff")

    // Candyland items
    val CANDY_CANE_SWORD = SwordItem(HItemTier.CANDY_CANE, 4, 0.4f, Properties().stacksTo(1).tab(Group)).setRegistryKey("candy_cane_sword")
    val CANDY_CANE = Item(Properties().tab(Group).food(ItemMisc.CANDY_CANE)).setRegistryKey("candy_cane")

    // Misc items
    val CHILI_PEPPER by HBlocks.CHILI_PEPPER::item//= BlockNamedItem(checkNotNull(HBlocks.CHILI_PEPPER), Properties().tab(Group).food(Food.Builder().nutrition(2).saturationMod(0.2F).alwaysEat().fast().build())).setRegistryKey("chili_pepper")
    //val CORN = BlockNamedItem(HBlocks.CORN, Properties().tab(Group).food(Food.Builder().nutrition(2).saturationMod(0.2F).setAlwaysEdible().build())).setRegistryKey("corn")
    //val ROASTED_CORN = Item(Properties().tab(Group).food(Food.Builder().nutrition(6).saturationMod(0.8F).setAlwaysEdible().build())).setRegistryKey("roasted_corn")

    // Debug
    val FILL_WAND = FillWandItem(Properties().tab(Group).stacksTo(1).rarity(Rarity.RARE)).setRegistryKey("fill_wand")
    val CLEAR_WAND = ClearWandItem(Properties().tab(Group).stacksTo(1).rarity(Rarity.EPIC)).setRegistryKey("clear_wand")
    val CLONE_WAND = CloneWandItem(Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)).setRegistryKey("clone_wand")
    val DISTANCE_WAND = DistanceWandItem(Properties().tab(Group).stacksTo(1).rarity(ItemMisc.LEGENDARY)).setRegistryKey("distance_wand")
    val GEAR_WAND = Item(Properties().tab(Group).stacksTo(1)).setRegistryKey("gear_wand")

    // @formatter:on

    // a generic item :D
    fun genericItem(name: String): Item {
        return Item(Properties().tab(Group)).setRegistryKey(name)
    }

    fun registerItems(items: IForgeRegistry<Item>) {
        //for (block in HBlocks.BLOCKS_W_ITEMS) {
        //    items.registerItemBlock(block)
        //}

        //for (block in HBlocks.BLOCKS_W_SIMPLE_ITEMS) {
        //    items.registerSimpleItem(BlockItem(block, props()).setRegistryName(block.registryName!!))
        //}

        // Overworld items
        items.registerHandheldItem(SHROOMY_SWORD)
        items.registerSimpleItem(MUSHROOM_CAP)
        items.registerSimpleItem(MUSHROOM_CHESTPLATE)
        items.registerHandheldItem(MINI_PISTOL)
        items.registerSimpleItem(SYRINGE)
        items.register(POTION_SYRINGE)
        items.registerSimpleItem(DEER_ANTLER)
        //items.registerSimpleItem(MALACHITE_CRYSTAL)
        //items.registerSimpleItem(DIAMOND_CRYSTAL)
        items.registerSimpleItem(PRISTINE_DIAMOND)
        items.registerSimpleItem(VENISON)
        items.registerSimpleItem(COOKED_VENISON)
        items.registerSpawnEgg(DEER_SPAWN_EGG)
        items.registerHandheldItem(MALACHITE_SWORD)
        items.registerHandheldItem(MALACHITE_SHOVEL)
        items.register(MALACHITE_PICKAXE) //items.registerHandheldItem(MALACHITE_PICKAXE)
        items.registerHandheldItem(MALACHITE_AXE)
        items.registerHandheldItem(MALACHITE_HOE)
        items.registerHandheldItem(MALACHITE_SCYTHE)

        // Castleton items
        items.registerHandheldItem(CASTLETON_SWORD)
        items.registerHandheldItem(VALABLADE)
        items.registerHandheldItem(GILDED_VALABLADE)
        items.registerSimpleItem(CASTLE_GEM)
        items.registerSpawnEgg(FRAYED_SOUL_SPAWN_EGG)
        items.registerSpawnEgg(VOID_RUNNER_SPAWN_EGG)
        items.registerSpawnEgg(CASTLETON_DEER_SPAWN_EGG)
        items.registerSpawnEgg(KNIGHTLY_JUGGERNAUT_SPAWN_EGG)
        //items.registerSimpleItem(CASTLETON_TORCH)
        //items.registerSimpleItem(BURNT_CASTLETON_TORCH)
        //items.registerSimpleItem(LUMLIGHT_SIGN)
        items.registerSimpleItem(TOWER_HELMET)
        items.registerSimpleItem(TOWER_CHESTPLATE)
        items.registerSimpleItem(TOWER_LEGGINGS)
        items.registerSimpleItem(TOWER_BOOTS)
        items.registerHandheldItem(CASTLETON_STAFF)
        //items.registerSimpleItem(WILD_BERROOK)
        items.registerSimpleItem(BLUE_CASTLETON_DUNGEON_KEY)

        // Rainbowland items
        items.registerHandheldItem(RAINBOWSTONE_AXE)
        items.registerHandheldItem(RAINBOWSTONE_HOE)
        items.registerHandheldItem(RAINBOWSTONE_PICKAXE)
        items.registerHandheldItem(RAINBOWSTONE_SHOVEL)
        items.registerHandheldItem(RAINBOWSTONE_SWORD)
        items.registerHandheldItem(RAINBOWSTONE_GEM)

        // Aubrum items
        items.registerHandheldItem(AUBRI_MINI_PISTOL)
        items.registerHandheldItem(AUBRI_RIFLE)
        items.registerSimpleItem(AURIGOLD)
        items.registerSimpleItem(AURIGOLD_PENDANT)
        items.registerHandheldItem(AURILO_STAFF)

        // Candyland items
        items.registerHandheldItem(CANDY_CANE_SWORD)
        items.registerSimpleItem(CANDY_CANE)

        // Misc
        //items.registerSimpleItem(CHILI_PEPPER)
        items.registerSimpleItem(BULLET)
        items.registerSimpleItem(INCENDIARY_BULLET)
        items.registerSimpleItem(SHELL)

        // Debug
        items.registerSimpleItem(FILL_WAND)
        items.registerSimpleItem(CLEAR_WAND)
        items.registerSimpleItem(CLONE_WAND)
        items.registerSimpleItem(DISTANCE_WAND)

        // Potion stuff
        PotionBrewing.ALLOWED_CONTAINERS.add(Ingredient.of(POTION_SYRINGE))
        BrewingRecipeRegistry.addRecipe(SyringeRecipe)
    }

    /**
     * Fired during the ColorHandlerEvent
     *
     * Registers IItemColors for certain items
     *
     * @see RainbowColor
     */
    @OnlyIn(Dist.CLIENT)
    fun setItemColors(colors: ItemColors) {
        colors.register(RainbowColor, HBlocks.RAINBOW_SOIL.grass)
        colors.register(RainbowColor, HBlocks.RAINBOW_GLASS)
        colors.register(RainbowColor, HBlocks.RAINBOW_GLASS_PANE)
        colors.register(PotionColor, { POTION_SYRINGE })
    }

    // Item Model Overrides
    fun registerItemProperties() {
        ItemModelsProperties.register(MALACHITE_PICKAXE, modLoc("charged")) { stack, _, _ ->
            MalachitePickaxeItem.getBoost(stack).toFloat()
        }
    }

    fun props(): Properties {
        return Properties().tab(Group)
    }

    /**
     * Generates an item block for the given block and registers it.
     *
     * @param block The block to generate an item form for
     */
    private fun IForgeRegistry<Item>.registerItemBlock(block: Block) {
        register(BlockItem(block, props()).setRegistryName(block.registryName!!))
    }

    /**
     * item w layer0 model
     */
    fun IForgeRegistry<Item>.registerSimpleItem(item: Item) {
        ItemModelType.SIMPLE_ITEM.add { item }
        register(item)
    }

    /**
     * item w handheld model
     */
    fun IForgeRegistry<Item>.registerHandheldItem(item: Item) {
        ItemModelType.HANDHELD_ITEM.add { item }
        register(item)
    }

    fun IForgeRegistry<Item>.registerSpawnEgg(item: Item) {
        ItemModelType.SPAWN_EGG_ITEM.add { item }
        register(item)
    }
}