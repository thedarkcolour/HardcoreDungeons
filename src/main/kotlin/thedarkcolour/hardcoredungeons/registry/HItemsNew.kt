package thedarkcolour.hardcoredungeons.registry

import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.block.HRegistry
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.item.HArmorMaterial
import thedarkcolour.hardcoredungeons.item.ItemMisc
import thedarkcolour.hardcoredungeons.item.SpeedBootsItem
import thedarkcolour.hardcoredungeons.item.debug.GreenWandItem

object HItemsNew : HRegistry<Item>(ForgeRegistries.ITEMS) {
    val GREEN_WAND by ItemMaker.simple("green_wand") { GreenWandItem(Item.Properties().tab(Group).stacksTo(1).rarity(ItemMisc.MYTHIC)) }
    val SPEED_BOOTS by ItemMaker.simple("speed_boots") { SpeedBootsItem(HArmorMaterial.SpeedBoots, Item.Properties().tab(Group).stacksTo(1).rarity(Rarity.UNCOMMON)) }
}