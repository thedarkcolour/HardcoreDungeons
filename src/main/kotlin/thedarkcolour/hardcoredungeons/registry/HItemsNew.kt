package thedarkcolour.hardcoredungeons.registry

import net.minecraft.item.Item
import net.minecraft.item.ItemTier
import net.minecraft.item.Rarity
import net.minecraft.item.SwordItem
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.item.*
import thedarkcolour.hardcoredungeons.item.debug.GreenWandItem
import thedarkcolour.hardcoredungeons.item.misc.GunItem
import thedarkcolour.hardcoredungeons.item.misc.StaffItem
import thedarkcolour.hardcoredungeons.item.overworld.ShroomySwordItem

object HItemsNew : HRegistry<Item>(ForgeRegistries.ITEMS) {
    val GREEN_WAND by ItemMaker.simple("green_wand") { GreenWandItem(Item.Properties().tab(Group).stacksTo(1).rarity(ItemMisc.MYTHIC)) }
    val SPEED_BOOTS by ItemMaker.simple("speed_boots") { SpeedBootsItem(HArmorMaterial.SPEED_BOOTS, Item.Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)) }

    val SHROOMY_SWORD by ItemMaker.rotatedHandheld("shroomy_sword") { ShroomySwordItem(Item.Properties().tab(Group).stacksTo(1).durability(726)) }

    val MINI_PISTOL by ItemMaker.rotatedGun("mini_pistol") { GunItem(Item.Properties().tab(Group), chargeTime = 22, bulletDamage = 3.0f, drop = 0.0004f)  }

    val AUBRI_MINI_PISTOL by ItemMaker.rotatedGun("aubri_mini_pistol") { GunItem(Item.Properties().tab(Group).stacksTo(1), chargeTime = 22, drop = 0.0004f) }
    val AUBRI_RIFLE by ItemMaker.rotatedGun("aubri_rifle") { GunItem(Item.Properties().tab(Group).stacksTo(1), chargeTime = 16, velocity = 2.4f, bulletDamage = 4.6f, drop = 0.0001f) }

    val CASTLETON_SWORD by ItemMaker.rotatedHandheld("castleton_sword") { SwordItem(ItemTier.GOLD, 9, -2.4f, Item.Properties().tab(Group).stacksTo(1).rarity(Rarity.RARE)) }
    val VALABLADE by ItemMaker.rotatedHandheld("valablade") { SwordItem(ItemTier.GOLD, 7, -2.4f, Item.Properties().tab(Group).stacksTo(1).rarity(Rarity.EPIC)) }
    val GILDED_VALABLADE by ItemMaker.rotatedHandheld("gilded_valablade") { SwordItem(ItemTier.GOLD, 10, -2.4f, Item.Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)) }
    val CASTLETON_STAFF by ItemMaker.rotatedHandheld("castleton_staff") { StaffItem(Item.Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)) }
    val MALACHITE_SWORD by ItemMaker.rotatedHandheld("malachite_sword") { SwordItem(HItemTier.MALACHITE, 3, -2.4f, Item.Properties().tab(Group).stacksTo(1)) }

    val RAINBOWSTONE_SWORD by ItemMaker.rotatedHandheld("rainbowstone_sword") { SwordItem(ItemTier.GOLD, 9, -2.0f, Item.Properties().tab(Group).durability(1326)) }

    val CANDY_CANE_SWORD by ItemMaker.rotatedHandheld("candy_cane_sword") { SwordItem(HItemTier.CANDY_CANE, 4, 0.4f, Item.Properties().stacksTo(1).tab(Group)) }
}