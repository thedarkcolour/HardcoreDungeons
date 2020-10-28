package thedarkcolour.hardcoredungeons.registry

import net.minecraft.block.Block
import net.minecraft.client.renderer.color.ItemColors
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.*
import net.minecraft.item.Item.Properties
import net.minecraft.util.IItemProvider
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.client.color.RainbowColor
import thedarkcolour.hardcoredungeons.data.modelgen.ModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.item.HArmorMaterial
import thedarkcolour.hardcoredungeons.item.HRarities
import thedarkcolour.hardcoredungeons.item.castleton.BlockingSwordItem
import thedarkcolour.hardcoredungeons.item.castleton.CastletonTorchItem
import thedarkcolour.hardcoredungeons.item.debug.ClearWandItem
import thedarkcolour.hardcoredungeons.item.debug.CloneWandItem
import thedarkcolour.hardcoredungeons.item.debug.DistanceWandItem
import thedarkcolour.hardcoredungeons.item.debug.FillWandItem
import thedarkcolour.hardcoredungeons.item.misc.GunItem
import thedarkcolour.hardcoredungeons.item.misc.StaffItem
import thedarkcolour.hardcoredungeons.item.overworld.ShroomySwordItem
import thedarkcolour.kotlinforforge.forge.objectHolder
import thedarkcolour.kotlinforforge.forge.runWhenOn

/**
 * Handles item registry and contains references to every `Item` and `BlockItem` in HCD.
 *
 * Categorized by dimension and `BlockItem` instances always go first.
 *
 * @author TheDarkColour, genericrandom64
 */
@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType", "DuplicatedCode")
object HItems {
    // Overworld blocks
    val VASE by objectHolder<Item>("vase")

    // Overworld items
    val SHROOMY_SWORD = ShroomySwordItem(Properties().group(Group).maxStackSize(1).maxDamage(726)).setRegistryKey("shroomy_sword")
    val MUSHROOM_CAP = ArmorItem(HArmorMaterial.SHROOMY, EquipmentSlotType.HEAD, Properties().group(Group).maxStackSize(1).maxDamage(567)).setRegistryKey("mushroom_cap")
    val MINI_PISTOL = GunItem(Properties().group(Group), bulletDamage = 3.0f, drop = 0.0004f).setRegistryKey("mini_pistol")
    //val RING_OF_LAST_WORDS = Item(Properties().group(Group).maxStackSize(1).rarity(Rarity.UNCOMMON)).setRegistryKey("ring_of_last_words")

    // Castleton blocks
    //val CASTLETON_SOIL by objectHolder<Item>("castleton_soil")
    //val CASTLETON_STONE by objectHolder<Item>("castleton_stone")
    val CASTLETON_BRICK_STAIRS by objectHolder<Item>("castleton_brick_stairs")
    //val CRACKED_CASTLETON_BRICKS by objectHolder<Item>("cracked_castleton_bricks")
    //val CHARGED_CASTLETON_BRICKS by objectHolder<Item>("charged_castleton_bricks")
    val CHARGED_CASTLETON_BRICK_FENCE by objectHolder<Item>("charged_castleton_brick_fence")
    val LUMLIGHT_SAPLING by objectHolder<Item>("lumlight_sapling")
    val LUMLIGHT_LOG by objectHolder<Item>("lumlight_log")
    val LUMLIGHT_WOOD by objectHolder<Item>("lumlight_wood")
    val STRIPPED_LUMLIGHT_LOG by objectHolder<Item>("stripped_lumlight_log")
    val STRIPPED_LUMLIGHT_WOOD by objectHolder<Item>("stripped_lumlight_wood")
    val LUMLIGHT_PLANKS by objectHolder<Item>("lumlight_planks")
    val LUMLIGHT_STAIRS by objectHolder<Item>("lumlight_stairs")
    val LUMLIGHT_SLAB by objectHolder<Item>("lumlight_slab")
    val LUMLIGHT_FENCE by objectHolder<Item>("lumlight_fence")
    val LUMLIGHT_FENCE_GATE by objectHolder<Item>("lumlight_fence_gate")
    val LUMLIGHT_PRESSURE_PLATE by objectHolder<Item>("lumlight_pressure_plate")
    val LUMLIGHT_DOOR by objectHolder<Item>("lumlight_door")
    val PURPLE_LUMSHROOM by objectHolder<Item>("purple_lumshroom")
    val BLUE_LUMSHROOM by objectHolder<Item>("blue_lumshroom")
    //val CROWN by objectHolder<Item>("crown")
    val CHALICE by objectHolder<Item>("chalice")
    val CASTLETON_DUNGEON_CHEST by objectHolder<Item>("castleton_dungeon_chest")

    // Castleton items
    val CASTLETON_SWORD = BlockingSwordItem(Properties().group(Group).maxStackSize(1).rarity(Rarity.RARE)).setRegistryKey("castleton_sword")
    val VALABLADE = BlockingSwordItem(Properties().group(Group).maxStackSize(1).rarity(Rarity.EPIC)).setRegistryKey("valablade")
    val GILDED_VALABLADE = BlockingSwordItem(Properties().group(Group).maxStackSize(1).rarity(Rarity.UNCOMMON)).setRegistryKey("gilded_valablade")
    val CASTLE_GEM = Item(Properties().group(Group)).setRegistryKey("castle_gem")
    val FRAYED_SOUL_SPAWN_EGG = SpawnEggItem(HEntities.FRAYED_SOUL, 0x434142, 0x2984a3, Properties().group(Group)).setRegistryKey("frayed_soul_spawn_egg")
    val VOID_RUNNER_SPAWN_EGG = SpawnEggItem(HEntities.VOID_RUNNER, 0x000000, 0x00c8ff, Properties().group(Group)).setRegistryKey("void_runner_spawn_egg")
    val CASTLETON_DEER_SPAWN_EGG = SpawnEggItem(HEntities.CASTLETON_DEER, 0x434142, 0x2984a3, Properties().group(Group)).setRegistryKey("castleton_deer_spawn_egg")
    val KNIGHTLY_JUGGERNAUT_SPAWN_EGG = SpawnEggItem(HEntities.KNIGHTLY_JUGGERNAUT, 0x434142, 0x2984a3, Properties().group(Group)).setRegistryKey("knightly_juggernaut_spawn_egg")
    val CASTLETON_TORCH = CastletonTorchItem(HBlocks.CASTLETON_TORCH, HBlocks.CASTLETON_WALL_TORCH, true, Properties().group(Group)).setRegistryKey("castleton_torch")
    val BURNT_CASTLETON_TORCH = CastletonTorchItem(HBlocks.CASTLETON_TORCH, HBlocks.CASTLETON_WALL_TORCH, false, Properties().group(Group)).setRegistryKey("burnt_castleton_torch")
    val LUMLIGHT_SIGN = SignItem(Properties().maxStackSize(16).group(Group), HBlocks.LUMLIGHT_SIGN, HBlocks.LUMLIGHT_WALL_SIGN).setRegistryKey("lumlight_sign")
    val LUM = Item(Properties().group(Group)).setRegistryKey("lum")
    val VOLATILE_LUM = Item(Properties().group(Group)).setRegistryKey("volatile_lum")
    val TOWER_HELMET = ArmorItem(HArmorMaterial.TOWER, EquipmentSlotType.HEAD, Properties().group(Group).maxStackSize(1)).setRegistryKey("tower_helmet")
    val TOWER_CHESTPLATE = ArmorItem(HArmorMaterial.TOWER, EquipmentSlotType.CHEST, Properties().group(Group).maxStackSize(1)).setRegistryKey("tower_chestplate")
    val TOWER_LEGGINGS = ArmorItem(HArmorMaterial.TOWER, EquipmentSlotType.LEGS, Properties().group(Group).maxStackSize(1)).setRegistryKey("tower_leggings")
    val TOWER_BOOTS = ArmorItem(HArmorMaterial.TOWER, EquipmentSlotType.FEET, Properties().group(Group).maxStackSize(1)).setRegistryKey("tower_boots")
    val CASTLETON_STAFF = StaffItem(Properties().group(Group).maxStackSize(1).rarity(Rarity.UNCOMMON)).setRegistryKey("castleton_staff")
    val WILD_BERROOK = BlockItem(HBlocks.WILD_BERROOK, Properties().group(Group).food(Food.Builder().hunger(2).saturation(0.4f).fastToEat().build())).setRegistryKey("wild_berrook")

    // Rainbowland blocks
    val RAINBOW_SOIL by objectHolder<Item>("rainbow_soil")
    val RAINBOW_GRASS_BLOCK by objectHolder<Item>("rainbow_grass_block")
    val COTTONMARSH_LEAVES by objectHolder<Item>("cottonmarsh_leaves")
    val RAINBOW_GLASS by objectHolder<Item>("rainbow_glass")
    val RAINBOW_GLASS_PANE by objectHolder<Item>("rainbow_glass_pane")

    // Rainbowland items
    val RAINBOWSTONE_AXE = AxeItem(ItemTier.GOLD, 11.0f, -3.2f, Properties().group(Group).defaultMaxDamage(1326)).setRegistryKey("rainbowstone_axe")
    val RAINBOWSTONE_HOE = HoeItem(ItemTier.GOLD, -1, -1.0f, Properties().group(Group).defaultMaxDamage(1326)).setRegistryKey("rainbowstone_hoe")
    val RAINBOWSTONE_PICKAXE = PickaxeItem(ItemTier.GOLD, 1, -2.5f, Properties().group(Group).defaultMaxDamage(1326)).setRegistryKey("rainbowstone_pickaxe")
    val RAINBOWSTONE_SHOVEL = ShovelItem(ItemTier.GOLD, 2.0f, -3.0f, Properties().group(Group).defaultMaxDamage(1326)).setRegistryKey("rainbowstone_shovel")
    val RAINBOWSTONE_SWORD = SwordItem(ItemTier.GOLD, 9, -2.0f, Properties().group(Group).defaultMaxDamage(1326)).setRegistryKey("rainbowstone_sword")
    val RAINBOWSTONE_GEM = Item(Properties().group(Group)).setRegistryKey("rainbowstone_gem")

    // Aubrum blocks
    val FLAME_ROSE by objectHolder<Item>("flame_rose")
    val AURI_STAIRS by objectHolder<Item>("auri_stairs")
    val AURI_SLAB by objectHolder<Item>("auri_slab")
    val SCRAP_METAL by objectHolder<Item>("scrap_metal")

    // Aubrum items
    val AUBRI_MINI_PISTOL = GunItem(
        Properties().group(Group),
        chargeTime = 22,
        drop = 0.0004f,
    ).setRegistryKey("aubri_mini_pistol")
    val AUBRI_RIFLE = GunItem(
        Properties().group(Group),
        chargeTime = 16,
        velocity = 2.4f,
        bulletDamage = 4.6f,
        drop = 0.0001f,
    ).setRegistryKey("aubri_rifle")
    val AURIGOLD = Item(Properties().group(Group)).setRegistryKey("aurigold")
    val AURIGOLD_PENDANT = Item(Properties().group(Group)).setRegistryKey("aurigold_pendant")
    val AURILO_STAFF = StaffItem(Properties().group(Group)).setRegistryKey("aurilo_staff")

    // Misc items
    val CHILI_PEPPER = BlockNamedItem(HBlocks.CHILI_PEPPER, Properties().group(Group).food(Food.Builder().hunger(2).saturation(0.2F).setAlwaysEdible().fastToEat().build())).setRegistryKey("chili_pepper")
    //val CORN = BlockNamedItem(HBlocks.CORN, Properties().group(Group).food(Food.Builder().hunger(2).saturation(0.2F).setAlwaysEdible().build())).setRegistryKey("corn")
    //val ROASTED_CORN = Item(Properties().group(Group).food(Food.Builder().hunger(6).saturation(0.8F).setAlwaysEdible().build())).setRegistryKey("roasted_corn")
    val BULLET = Item(Properties().group(Group)).setRegistryKey("bullet")
    val SHELL = Item(Properties().group(Group)).setRegistryKey("shell")

    // Debug
    val FILL_WAND = FillWandItem(Properties().group(Group).maxStackSize(1).rarity(Rarity.RARE)).setRegistryKey("fill_wand")
    val CLEAR_WAND = ClearWandItem(Properties().group(Group).maxStackSize(1).rarity(Rarity.EPIC)).setRegistryKey("clear_wand")
    val CLONE_WAND = CloneWandItem(Properties().group(Group).maxStackSize(1).rarity(Rarity.UNCOMMON)).setRegistryKey("clone_wand")
    val DISTANCE_WAND = DistanceWandItem(Properties().group(Group).maxStackSize(1).rarity(HRarities.LEGENDARY)).setRegistryKey("distance_wand")

    fun registerItems(items: IForgeRegistry<Item>) {
        for (block in HBlocks.BLOCKS_W_ITEMS) {
            items.registerItemBlock(block)
        }

        for (block in HBlocks.BLOCKS_W_SIMPLE_ITEMS) {
            items.registerSimpleItem(block)
        }

        // Overworld items
        items.registerHandheldItem(SHROOMY_SWORD)
        items.registerHandheldItem(MINI_PISTOL)

        // Castleton items
        items.registerHandheldItem(CASTLETON_SWORD)
        items.registerHandheldItem(VALABLADE)
        items.registerHandheldItem(GILDED_VALABLADE)
        items.registerSimpleItem(CASTLE_GEM)
        items.registerSpawnEgg(FRAYED_SOUL_SPAWN_EGG)
        items.registerSpawnEgg(VOID_RUNNER_SPAWN_EGG)
        items.registerSpawnEgg(CASTLETON_DEER_SPAWN_EGG)
        items.registerSpawnEgg(KNIGHTLY_JUGGERNAUT_SPAWN_EGG)
        items.registerSimpleItem(CASTLETON_TORCH)
        items.registerSimpleItem(BURNT_CASTLETON_TORCH)
        items.registerSimpleItem(LUMLIGHT_SIGN)
        items.registerSimpleItem(LUM)
        items.registerSimpleItem(VOLATILE_LUM)
        items.registerSimpleItem(TOWER_HELMET)
        items.registerSimpleItem(TOWER_CHESTPLATE)
        items.registerSimpleItem(TOWER_LEGGINGS)
        items.registerSimpleItem(TOWER_BOOTS)
        items.registerHandheldItem(CASTLETON_STAFF)
        items.registerSimpleItem(WILD_BERROOK)

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

        // Misc
        items.registerSimpleItem(CHILI_PEPPER)
        //items.register(CORN)
        //items.register(ROASTED_CORN)
        items.registerSimpleItem(BULLET)
        items.registerSimpleItem(SHELL)

        // Debug
        items.registerSimpleItem(FILL_WAND)
        items.registerSimpleItem(CLEAR_WAND)
        items.registerSimpleItem(CLONE_WAND)
        items.registerSimpleItem(DISTANCE_WAND)
    }

    /**
     * Fired during the ColorHandlerEvent
     *
     * Registers IItemColors for certain items
     *
     * @see RainbowColor
     */
    fun setItemColors(colors: Any) {
        runWhenOn(Dist.CLIENT) {
            colors as ItemColors

            colors.register(RainbowColor, HItems::RAINBOW_GRASS_BLOCK::get)
            colors.register(RainbowColor, HItems::RAINBOW_GLASS::get)
            colors.register(RainbowColor, HItems::RAINBOW_GLASS_PANE::get)
        }
    }

    fun props(): Properties {
        return Properties().group(Group)
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
    fun IForgeRegistry<Item>.registerSimpleItem(item: IItemProvider) {
        ModelType.SIMPLE_ITEM.add(item)
        register(item.asItem())
    }

    /**
     * item w handheld model
     */
    fun IForgeRegistry<Item>.registerHandheldItem(item: IItemProvider) {
        ModelType.HANDHELD_ITEM.add(item)
        register(item.asItem())
    }

    fun IForgeRegistry<Item>.registerSpawnEgg(item: IItemProvider) {
        ModelType.SPAWN_EGG.add(item)
        register(item.asItem())
    }
}