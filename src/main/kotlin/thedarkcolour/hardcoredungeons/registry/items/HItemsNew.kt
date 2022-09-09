package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.item.ItemTier
import net.minecraft.item.Rarity
import net.minecraft.item.SwordItem
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.AxeItem
import net.minecraft.world.item.HoeItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.ShovelItem
import net.minecraftforge.common.ForgeSpawnEggItem
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.item.*
import thedarkcolour.hardcoredungeons.item.debug.GreenWandItem
import thedarkcolour.hardcoredungeons.item.GunItem
import thedarkcolour.hardcoredungeons.item.StaffItem
import thedarkcolour.hardcoredungeons.item.ShroomySwordItem
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HRegistry

object HItemsNew : HRegistry<Item>(ForgeRegistries.Keys.ITEMS) {
    val GREEN_WAND by ItemMaker.simple("green_wand") { GreenWandItem(Properties().tab(Group).stacksTo(1).rarity(ItemMisc.MYTHIC)) }
    val SPEED_BOOTS by ItemMaker.simple("speed_boots") { SpeedBootsItem(HArmorMaterial.SPEED_BOOTS, Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)) }

    // Overworld items
    val MUSHROOM_CAP by ItemMaker.simple("mushroom_cap") { MushroomArmorItem(EquipmentSlot.HEAD, Properties().tab(Group).stacksTo(1).durability(567)) }
    val MUSHROOM_CHESTPLATE by ItemMaker.simple("mushroom_chestplate") { MushroomArmorItem(EquipmentSlot.CHEST, Properties().tab(Group).stacksTo(1).durability(642)) }
    val SYRINGE by ItemMaker.simple("syringe") { EmptySyringeItem(Properties().tab(Group).stacksTo(16)) }
    val POTION_SYRINGE by ItemMaker.simple("potion_syringe") { SyringeItem(Properties().tab(Group).stacksTo(2)) }
    val DEER_ANTLER by ItemMaker.resourceItem("deer_antler")
    //val PRISTINE_DIAMOND by ItemMaker.resourceItem("pristine_diamond")
    val BULLET by ItemMaker.resourceItem("bullet")
    val INCENDIARY_BULLET by ItemMaker.resourceItem("incendiary_bullet")
    val SHELL by ItemMaker.resourceItem("shell")
    val VENISON by ItemMaker.simple("venison") { Item(Properties().tab(Group).food(FoodProperties.Builder().nutrition(3).saturationMod(0.2f).meat().build())) }
    val COOKED_VENISON by ItemMaker.simple("cooked_venison") { Item(Properties().tab(Group).food(FoodProperties.Builder().nutrition(7).saturationMod(0.9f).meat().build())) }
    val DEER_SPAWN_EGG by ItemMaker.simple("deer_spawn_egg") { ForgeSpawnEggItem(HEntities::DEER, 0x4c2922, 0x202020, Properties().tab(Group)) }
    val MALACHITE_SHOVEL by ItemMaker.simple("malachite_shovel") { ShovelItem(HItemTier.MALACHITE, 1.5f, -3.0f, Properties().tab(Group).stacksTo(1)) }
    val MALACHITE_PICKAXE by ItemMaker.simple("malachite_pickaxe") { MalachitePickaxeItem(HItemTier.MALACHITE, 1, -2.8f, Properties().tab(Group).stacksTo(1)) }
    val MALACHITE_AXE by ItemMaker.simple("malachite_axe") { AxeItem(HItemTier.MALACHITE, 5.0f, -3.0f, Properties().tab(Group).stacksTo(1)) }
    val MALACHITE_HOE by ItemMaker.simple("malachite_hoe") { HoeItem(HItemTier.MALACHITE, -3, 0.0f, Properties().tab(Group).stacksTo(1)) }
    val MALACHITE_SCYTHE by ItemMaker.simple("malachite_scythe") { ScytheItem(HItemTier.MALACHITE, 3, 0.0f, Properties().tab(Group).stacksTo(1)) }
    val MUSHROOM_ARCHER_SPAWN_EGG by ItemMaker.simple("mushroom_archer_spawn_egg") { ForgeSpawnEggItem(HEntities::MUSHROOM_ARCHER, 0x434142, 0x2984a3, Properties().tab(Group)) }

    // Castleton items


    val SHROOMY_SWORD by ItemMaker.rotatedHandheld("shroomy_sword") { ShroomySwordItem(Properties().tab(Group).stacksTo(1).durability(726)) }

    val MINI_PISTOL by ItemMaker.rotatedGun("mini_pistol") { GunItem(Properties().tab(Group), chargeTime = 22, bulletDamage = 3.0f, drop = 0.0004f)  }

    val AUBRI_MINI_PISTOL by ItemMaker.rotatedGun("aubri_mini_pistol") { GunItem(Properties().tab(Group).stacksTo(1), chargeTime = 22, drop = 0.0004f) }
    val AUBRI_RIFLE by ItemMaker.rotatedGun("aubri_rifle") { GunItem(Properties().tab(Group).stacksTo(1), chargeTime = 16, velocity = 2.4f, bulletDamage = 4.6f, drop = 0.0001f) }

    val CASTLETON_SWORD by ItemMaker.rotatedHandheld("castleton_sword") { SwordItem(ItemTier.GOLD, 9, -2.4f, Properties().tab(Group).stacksTo(1).rarity(Rarity.RARE)) }
    val VALABLADE by ItemMaker.rotatedHandheld("valablade") { SwordItem(ItemTier.GOLD, 7, -2.4f, Properties().tab(Group).stacksTo(1).rarity(Rarity.EPIC)) }
    val GILDED_VALABLADE by ItemMaker.rotatedHandheld("gilded_valablade") { SwordItem(ItemTier.GOLD, 10, -2.4f, Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)) }
    val CASTLETON_STAFF by ItemMaker.rotatedHandheld("castleton_staff") { StaffItem(Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)) }
    val MALACHITE_SWORD by ItemMaker.rotatedHandheld("malachite_sword") { SwordItem(HItemTier.MALACHITE, 3, -2.4f, Properties().tab(Group).stacksTo(1)) }

    val RAINBOWSTONE_SWORD by ItemMaker.rotatedHandheld("rainbowstone_sword") { SwordItem(ItemTier.GOLD, 9, -2.0f, Properties().tab(Group).durability(1326)) }

    val CANDY_CANE_SWORD by ItemMaker.rotatedHandheld("candy_cane_sword") { SwordItem(HItemTier.CANDY_CANE, 4, 0.4f, Properties().stacksTo(1).tab(Group)) }
}