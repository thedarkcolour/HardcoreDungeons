package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.*
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.alchemy.PotionBrewing
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.RegisterColorHandlersEvent
import net.minecraftforge.common.ForgeSpawnEggItem
import net.minecraftforge.common.brewing.BrewingRecipeRegistry
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.client.color.PotionColor
import thedarkcolour.hardcoredungeons.client.color.RainbowColor
import thedarkcolour.hardcoredungeons.item.*
import thedarkcolour.hardcoredungeons.item.debug.ClearWandItem
import thedarkcolour.hardcoredungeons.item.debug.CloneWandItem
import thedarkcolour.hardcoredungeons.item.debug.DistanceWandItem
import thedarkcolour.hardcoredungeons.item.debug.FillWandItem
import thedarkcolour.hardcoredungeons.recipe.SyringeRecipe
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HRegistry
import thedarkcolour.hardcoredungeons.util.modLoc

/**
 * Handles item registry and contains references to every `Item` and `BlockItem` in HCD.
 *
 * Categorized by dimension and `BlockItem` instances always go first.
 *
 * @author thedarkcolour, genericrandom64
 */
@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType", "DuplicatedCode")
object HItems : HRegistry<Item>(ForgeRegistries.Keys.ITEMS) {
    // @formatter:off

    // Overworld items
    //val SYRINGE = EmptySyringeItem(Properties().tab(Group).stacksTo(16)).setRegistryKey("syringe")
    //val POTION_SYRINGE = SyringeItem(Properties().tab(Group).stacksTo(2)).setRegistryKey("potion_syringe")
    //val DEER_ANTLER = resourceItem("deer_antler")
    //val PRISTINE_DIAMOND = resourceItem("pristine_diamond")
    //val BULLET = resourceItem("bullet")
    //val INCENDIARY_BULLET = resourceItem("incendiary_bullet")
    //val SHELL = resourceItem("shell")
    //val VENISON = Item(Properties().tab(Group).food(FoodProperties.Builder().nutrition(3).saturationMod(0.2f).meat().build())).setRegistryKey("venison")
    //val COOKED_VENISON = Item(Properties().tab(Group).food(FoodProperties.Builder().nutrition(7).saturationMod(0.9f).meat().build())).setRegistryKey("cooked_venison")
    //val DEER_SPAWN_EGG = ForgeSpawnEggItem(HEntities::DEER, 0x4c2922, 0x202020, Properties().tab(Group)).setRegistryKey("deer_spawn_egg")
    //val MALACHITE_SHOVEL = ShovelItem(HItemTier.MALACHITE, 1.5f, -3.0f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_shovel")
    //val MALACHITE_PICKAXE = MalachitePickaxeItem(HItemTier.MALACHITE, 1, -2.8f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_pickaxe")
    //val MALACHITE_AXE = AxeItem(HItemTier.MALACHITE, 5.0f, -3.0f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_axe")
    //val MALACHITE_HOE = HoeItem(HItemTier.MALACHITE, -3, 0.0f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_hoe")
    //val MALACHITE_SCYTHE = ScytheItem(HItemTier.MALACHITE, 3, 0.0f, Properties().tab(Group).stacksTo(1)).setRegistryKey("malachite_scythe")
    //val MUSHROOM_ARCHER_SPAWN_EGG = ForgeSpawnEggItem(HEntities::MUSHROOM_ARCHER, 0x434142, 0x2984a3, Properties().tab(Group)).setRegistryKey("knightly_juggernaut_spawn_egg")

    // Castleton items
    //val CASTLE_GEM = resourceItem("castle_gem")
    //val FRAYED_SOUL_SPAWN_EGG = ForgeSpawnEggItem(HEntities::FRAYED_SOUL, 0x434142, 0x2984a3, Properties().tab(Group)).setRegistryKey("frayed_soul_spawn_egg")
    val VOID_RUNNER_SPAWN_EGG = ForgeSpawnEggItem(HEntities::VOID_RUNNER, 0x000000, 0x00c8ff, Properties().tab(Group)).setRegistryKey("void_runner_spawn_egg")
    val CASTLETON_DEER_SPAWN_EGG = ForgeSpawnEggItem(HEntities::CASTLETON_DEER, 0x434142, 0x2984a3, Properties().tab(Group)).setRegistryKey("castleton_deer_spawn_egg")
    val KNIGHTLY_JUGGERNAUT_SPAWN_EGG = ForgeSpawnEggItem(HEntities::KNIGHTLY_JUGGERNAUT, 0x434142, 0x2984a3, Properties().tab(Group)).setRegistryKey("knightly_juggernaut_spawn_egg")
    val TOWER_HELMET = ArmorItem(HArmorMaterial.TOWER, EquipmentSlot.HEAD, Properties().tab(Group).stacksTo(1)).setRegistryKey("tower_helmet")
    val TOWER_CHESTPLATE = ArmorItem(HArmorMaterial.TOWER, EquipmentSlot.CHEST, Properties().tab(Group).stacksTo(1)).setRegistryKey("tower_chestplate")
    val TOWER_LEGGINGS = ArmorItem(HArmorMaterial.TOWER, EquipmentSlot.LEGS, Properties().tab(Group).stacksTo(1)).setRegistryKey("tower_leggings")
    val TOWER_BOOTS = ArmorItem(HArmorMaterial.TOWER, EquipmentSlot.FEET, Properties().tab(Group).stacksTo(1)).setRegistryKey("tower_boots")
    val BLUE_CASTLETON_DUNGEON_KEY = Item(Properties().tab(Group).stacksTo(1).rarity(Rarity.RARE)).setRegistryKey("blue_castleton_dungeon_key")

    // Rainbowland items
    val RAINBOWSTONE_AXE = AxeItem(Tiers.GOLD, 11.0f, -3.2f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_axe")
    val RAINBOWSTONE_HOE = HoeItem(Tiers.GOLD, -1, -1.0f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_hoe")
    val RAINBOWSTONE_PICKAXE = PickaxeItem(Tiers.GOLD, 1, -2.5f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_pickaxe")
    val RAINBOWSTONE_SHOVEL = ShovelItem(Tiers.GOLD, 2.0f, -3.0f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_shovel")
    val RAINBOWSTONE_SWORD = SwordItem(Tiers.GOLD, 9, -2.0f, Properties().tab(Group).durability(1326)).setRegistryKey("rainbowstone_sword")
    val RAINBOWSTONE_GEM = resourceItem("rainbowstone_gem")

    // Aubrum items
    val AURIGOLD = resourceItem("aurigold")
    val AURIGOLD_PENDANT = PendantItem(Properties().tab(Group).stacksTo(1)).setRegistryKey("aurigold_pendant")
    val AURILO_STAFF = StaffItem(Properties().tab(Group).stacksTo(1)).setRegistryKey("aurilo_staff")

    // Candyland items
    val CANDY_CANE = Item(Properties().tab(Group).food(ItemMisc.CANDY_CANE)).setRegistryKey("candy_cane")

    // Debug
    val FILL_WAND = FillWandItem(Properties().tab(Group).stacksTo(1).rarity(Rarity.RARE)).setRegistryKey("fill_wand")
    val CLEAR_WAND = ClearWandItem(Properties().tab(Group).stacksTo(1).rarity(Rarity.EPIC)).setRegistryKey("clear_wand")
    val CLONE_WAND = CloneWandItem(Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)).setRegistryKey("clone_wand")
    val DISTANCE_WAND = DistanceWandItem(Properties().tab(Group).stacksTo(1).rarity(ItemMisc.LEGENDARY)).setRegistryKey("distance_wand")
    val GEAR_WAND = GearWandItem(Properties().tab(Group).stacksTo(1)).setRegistryKey("gear_wand")

    // @formatter:on

    fun resourceItem(name: String): Item {
        return Item(Properties().tab(Group)).setRegistryKey(name)
    }

    fun registerItems(items: IForgeRegistry<Item>) {
        // Overworld items

        // Castleton items
        items.registerSimpleItem(CASTLE_GEM)
        items.registerSpawnEgg(FRAYED_SOUL_SPAWN_EGG)
        items.registerSpawnEgg(VOID_RUNNER_SPAWN_EGG)
        items.registerSpawnEgg(CASTLETON_DEER_SPAWN_EGG)
        items.registerSpawnEgg(KNIGHTLY_JUGGERNAUT_SPAWN_EGG)
        items.registerSimpleItem(TOWER_HELMET)
        items.registerSimpleItem(TOWER_CHESTPLATE)
        items.registerSimpleItem(TOWER_LEGGINGS)
        items.registerSimpleItem(TOWER_BOOTS)
        items.registerSimpleItem(BLUE_CASTLETON_DUNGEON_KEY)

        // Rainbowland items
        items.registerHandheldItem(RAINBOWSTONE_AXE)
        items.registerHandheldItem(RAINBOWSTONE_HOE)
        items.registerHandheldItem(RAINBOWSTONE_PICKAXE)
        items.registerHandheldItem(RAINBOWSTONE_SHOVEL)
        items.registerHandheldItem(RAINBOWSTONE_GEM)

        // Aubrum items
        items.registerSimpleItem(AURIGOLD)
        items.registerSimpleItem(AURIGOLD_PENDANT)
        items.registerHandheldItem(AURILO_STAFF)

        // Candyland items
        items.registerSimpleItem(CANDY_CANE)

        // Misc
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
    fun setItemColors(colors: RegisterColorHandlersEvent.Item) {
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
}