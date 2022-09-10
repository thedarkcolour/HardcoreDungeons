package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.world.item.Item
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.item.GunItem
import thedarkcolour.hardcoredungeons.item.PendantItem

// @formatter:off

// Valuables
val AURIGOLD_ITEM by ItemMaker.resourceItem("aurigold")
val AURIGOLD_PENDANT_ITEM by ItemMaker.simple("aurigold_pendant") { PendantItem(Item.Properties().tab(Group).stacksTo(1)) }

// Weapons
val AUBRI_MINI_PISTOL_ITEM by ItemMaker.rotatedGun("aubri_mini_pistol") { GunItem(ItemMaker.props().stacksTo(1), chargeTime = 22, drop = 0.0004f) }
val AUBRI_RIFLE_ITEM by ItemMaker.rotatedGun("aubri_rifle") { GunItem(ItemMaker.props().stacksTo(1), chargeTime = 16, velocity = 2.4f, bulletDamage = 4.6f, drop = 0.0001f) }


// @formatter:on
