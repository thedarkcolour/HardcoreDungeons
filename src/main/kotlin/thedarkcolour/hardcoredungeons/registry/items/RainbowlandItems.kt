package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.world.item.*

// @formatter:off

// Todo change to "rainbowstone"
val RAINBOWSTONE_GEM_ITEM by ItemMaker.resourceItem("rainbowstone_gem")

// tools
val RAINBOWSTONE_AXE_ITEM by ItemMaker.handheld("rainbowstone_axe") { AxeItem(Tiers.GOLD, 11.0f, -3.2f, ItemMaker.props().durability(1326)) }
val RAINBOWSTONE_HOE_ITEM by ItemMaker.handheld("rainbowstone_hoe") { HoeItem(Tiers.GOLD, -1, -1.0f, ItemMaker.props().durability(1326)) }
val RAINBOWSTONE_PICKAXE_ITEM by ItemMaker.handheld("rainbowstone_pickaxe") { PickaxeItem(Tiers.GOLD, 1, -2.5f, ItemMaker.props().durability(1326)) }
val RAINBOWSTONE_SHOVEL_ITEM by ItemMaker.handheld("rainbowstone_shovel") { ShovelItem(Tiers.GOLD, 2.0f, -3.0f, ItemMaker.props().durability(1326)) }
val RAINBOWSTONE_SWORD_ITEM by ItemMaker.handheld("rainbowstone_sword") { SwordItem(Tiers.GOLD, 9, -2.0f, ItemMaker.props().durability(1326)) }


// @formatter:on
