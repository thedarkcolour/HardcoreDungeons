package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.world.item.Item
import net.minecraftforge.common.ForgeSpawnEggItem
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.registry.HEntities

val CASTLE_GEM_ITEM by ItemMaker.resourceItem("castle_gem")
val FRAYED_SOUL_SPAWN_EGG_ITEM by ItemMaker.simple("frayed_soul_spawn_egg") {
    ForgeSpawnEggItem(HEntities::FRAYED_SOUL, 0x434142, 0x2984a3, Item.Properties().tab(Group))
}
val CASTLETON_DEER_SPAWN_EGG_ITEM by ItemMaker.simple("castleton_deer_spawn_egg") {
    ForgeSpawnEggItem(HEntities::CASTLETON_DEER, 0x434142, 0x2984a3, Item.Properties().tab(Group))
}
val KNIGHTLY_JUGGERNAUT_SPAWN_EGG_ITEM by ItemMaker.spawnEgg(HEntities::KNIGHTLY_JUGGERNAUT, 0x434142, 0x2984a3)