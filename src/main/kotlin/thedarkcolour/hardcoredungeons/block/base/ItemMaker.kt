package thedarkcolour.hardcoredungeons.block.base

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Food
import net.minecraft.item.Item
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.registry.HItemsNew
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

// todo write comments
object ItemMaker {
    fun <T : Item> handheld(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.HANDHELD_ITEM, supplier)
    }

    fun <T : Item> rotatedHandheld(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.ROTATED_HANDHELD_ITEM, supplier)
    }

    fun <T : Item> rotatedGun(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.ROTATED_GUN_ITEM, supplier)
    }

    fun <T : Item> simple(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.SIMPLE_ITEM, supplier)
    }

    fun simpleItem(name: String): ObjectHolderDelegate<Item> {
        return simple(name) { Item(Item.Properties().tab(Group)) }
    }

    fun simpleBlockItem(name: String, block: () -> Block): ObjectHolderDelegate<BlockItem> {
        return blockItem(name, ItemModelType.SIMPLE_ITEM, block)
    }

    fun foodBlockItem(name: String, block: () -> Block, food: Food): ObjectHolderDelegate<Item> {
        return registerModelled(name, ItemModelType.SIMPLE_ITEM) {
            BlockItem(block(), Item.Properties().tab(Group).food(food))
        }
    }

    fun food(nutrition: Int, saturation: Float, alwaysEdible: Boolean = false, fastEat: Boolean = false, meat: Boolean = false): Food {
        val foodBuilder = Food.Builder().nutrition(nutrition).saturationMod(saturation)
        if (alwaysEdible) foodBuilder.alwaysEat()
        if (fastEat) foodBuilder.fast()
        if (meat) foodBuilder.meat()
        return foodBuilder.build()
    }

    fun blockItem(name: String, type: ItemModelType = ItemModelType.BLOCK_ITEM, block: () -> Block): ObjectHolderDelegate<BlockItem> {
        return registerModelled(name, type) { BlockItem(block(), Item.Properties().tab(Group)) }
    }

    fun <T : Item> registerModelled(name: String, type: ItemModelType, supplier: () -> T): ObjectHolderDelegate<T> {
        val obj = HItemsNew.register(name, supplier)
        HItemsNew.onceRegistered { type.add(obj) }
        return obj
    }
}