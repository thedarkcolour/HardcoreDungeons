package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.ChatFormatting
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.*
import net.minecraftforge.common.ForgeSpawnEggItem
import thedarkcolour.hardcoredungeons.item.*
import thedarkcolour.hardcoredungeons.registry.HEntities

// @formatter:off

// Rarities
val LEGENDARY_RARITY: Rarity = Rarity.create("HARDCOREDUNGEONS_LEGENDARY", ChatFormatting.RED)

// Syringes (Todo remove)
val POTION_SYRINGE_ITEM by HItems.register("potion_syringe") { SyringeItem(ItemMaker.props().stacksTo(8)) }
val SYRINGE_ITEM by ItemMaker.simple("syringe") { EmptySyringeItem(ItemMaker.props().stacksTo(16)) }

val INFERNAL_SWORD by ItemMaker.handheld("infernal_sword") { HSwordItem(8.0f, 2.4f, 18, 1394, ItemMaker.props().stacksTo(1)) }

// Ammo
val BULLET_ITEM by ItemMaker.resourceItem("bullet")
val INCENDIARY_BULLET_ITEM by ItemMaker.resourceItem("incendiary_bullet")
val SHELL_ITEM by ItemMaker.resourceItem("shell")

// Guns
val MINI_PISTOL_ITEM by ItemMaker.rotatedGun("mini_pistol") { GunItem(ItemMaker.singleStack(), chargeTime = 22, bulletDamage = 3.0f, drop = 0.0004f) }

// Malachite set
val MALACHITE_SHOVEL_ITEM by ItemMaker.simple("malachite_shovel") { ShovelItem(HItemTier.MALACHITE, 1.5f, -3.0f, ItemMaker.singleStack()) }
val MALACHITE_PICKAXE_ITEM by HItems.register("malachite_pickaxe") { MalachitePickaxeItem(HItemTier.MALACHITE, 1, -2.8f, ItemMaker.singleStack()) }
val MALACHITE_AXE_ITEM by ItemMaker.simple("malachite_axe") { AxeItem(HItemTier.MALACHITE, 5.0f, -3.0f, ItemMaker.singleStack()) }
val MALACHITE_HOE_ITEM by ItemMaker.simple("malachite_hoe") { HoeItem(HItemTier.MALACHITE, -3, 0.0f, ItemMaker.singleStack()) }
val MALACHITE_SCYTHE_ITEM by ItemMaker.simple("malachite_scythe") { ScytheItem(HItemTier.MALACHITE, 3, 0.0f, ItemMaker.singleStack()) }
val MALACHITE_SWORD_ITEM by ItemMaker.handheld("malachite_sword") { SwordItem(HItemTier.MALACHITE, 3, -2.4f, ItemMaker.singleStack()) }

// Deer
val VENISON_ITEM by ItemMaker.simple("venison") { Item(Item.Properties().food(FoodProperties.Builder().nutrition(3).saturationMod(0.2f).meat().build())) }
val COOKED_VENISON_ITEM by ItemMaker.simple("cooked_venison") { Item(Item.Properties().food(FoodProperties.Builder().nutrition(7).saturationMod(0.9f).meat().build())) }
val DEER_ANTLER_ITEM by ItemMaker.resourceItem("deer_antler")
val DEER_SPAWN_EGG_ITEM by ItemMaker.simple("deer_spawn_egg") { ForgeSpawnEggItem(HEntities::DEER, 0x4c2922, 0x202020, Item.Properties()) }

// Mushroom set
val MUSHROOM_CAP_ITEM by ItemMaker.simple("mushroom_cap") { MushroomArmorItem(ArmorItem.Type.HELMET, Item.Properties().stacksTo(1).durability(567)) }
val MUSHROOM_CHESTPLATE_ITEM by ItemMaker.simple("mushroom_chestplate") { MushroomArmorItem(ArmorItem.Type.CHESTPLATE, Item.Properties().stacksTo(1).durability(642)) }
val SHROOMY_SWORD_ITEM by ItemMaker.handheld("shroomy_sword") { ShroomySwordItem(Item.Properties().stacksTo(1).durability(726)) }
val MUSHROOM_ARCHER_SPAWN_EGG_ITEM by ItemMaker.spawnEgg(HEntities::MUSHROOM_ARCHER, 0x434142, 0x2984a3)

// @formatter:on
