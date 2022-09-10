package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.*
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.item.HArmorMaterial
import thedarkcolour.hardcoredungeons.item.StaffItem
import thedarkcolour.hardcoredungeons.registry.HEntities

// @formatter:off

val CASTLE_GEM_ITEM by ItemMaker.resourceItem("castle_gem")

// Spawn eggs
val FRAYED_SOUL_SPAWN_EGG_ITEM by ItemMaker.spawnEgg(HEntities::FRAYED_SOUL, 0x434142, 0x2984a3)
val CASTLETON_DEER_SPAWN_EGG_ITEM by ItemMaker.spawnEgg(HEntities::CASTLETON_DEER, 0x434142, 0x2984a3)
val KNIGHTLY_JUGGERNAUT_SPAWN_EGG_ITEM by ItemMaker.spawnEgg(HEntities::KNIGHTLY_JUGGERNAUT, 0x434142, 0x2984a3)

// Tower armor
val TOWER_HELMET_ITEM by ItemMaker.simple("tower_helmet") {ArmorItem(HArmorMaterial.TOWER, EquipmentSlot.HEAD, Item.Properties().tab(Group).stacksTo(1))}
val TOWER_CHESTPLATE_ITEM by ItemMaker.simple("tower_chestplate") {ArmorItem(HArmorMaterial.TOWER, EquipmentSlot.CHEST, Item.Properties().tab(Group).stacksTo(1))}
val TOWER_LEGGINGS_ITEM by ItemMaker.simple("tower_leggings") {ArmorItem(HArmorMaterial.TOWER, EquipmentSlot.LEGS, Item.Properties().tab(Group).stacksTo(1))}
val TOWER_BOOTS_ITEM by ItemMaker.simple("tower_boots") {ArmorItem(HArmorMaterial.TOWER, EquipmentSlot.FEET, Item.Properties().tab(Group).stacksTo(1))}

val BLUE_CASTLETON_DUNGEON_KEY_ITEM by ItemMaker.simple("blue_castleton_dungeon_key") { Item(Item.Properties().tab(Group).stacksTo(1).rarity(Rarity.RARE)) }

// Weapons
val CASTLETON_SWORD_ITEM by ItemMaker.rotatedHandheld("castleton_sword") { SwordItem(Tiers.GOLD, 9, -2.4f, ItemMaker.props().stacksTo(1).rarity(Rarity.RARE)) }
val CASTLETON_STAFF_ITEM by ItemMaker.rotatedHandheld("castleton_staff") { StaffItem(ItemMaker.props().stacksTo(1).rarity(Rarity.UNCOMMON)) }

// @formatter:on