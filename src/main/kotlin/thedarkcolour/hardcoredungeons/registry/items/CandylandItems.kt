package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.world.item.Item
import net.minecraft.world.item.SwordItem
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.item.HItemTier
import thedarkcolour.hardcoredungeons.item.ItemMisc


// @formatter:off

val CANDY_CANE_ITEM by ItemMaker.simple("candy_cane") { Item(Item.Properties().tab(Group).food(ItemMisc.CANDY_CANE)) }
val CANDY_CANE_SWORD_ITEM by ItemMaker.rotatedHandheld("candy_cane_sword") { SwordItem(HItemTier.CANDY_CANE, 4, 0.4f, ItemMaker.props().stacksTo(1)) }


// @formatter:on
