package thedarkcolour.hardcoredungeons.block.base

import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.registry.HItemsNew
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

object ItemMaker {
    /**
     * For creating items with handheld models, like Pickaxes and Shovels
     */
    fun <T : Item> handheld(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.HANDHELD_ITEM, supplier)
    }

    /**
     * For making simple 2D item models, like Apples, Bread, or other common simple items
     */
    fun <T : Item> simple(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.SIMPLE_ITEM, supplier)
    }

    /**
     * For creating a simple item with no properties, like Iron Ingots, Gunpowder, or most crafting ingredients
     */
    fun simpleItem(name: String): ObjectHolderDelegate<Item> {
        return simple(name) { Item(Item.Properties().tab(Group)) }
    }

    fun simpleBlockItem(name: String, block: () -> Block): ObjectHolderDelegate<BlockItem> {
        return blockItem(name, ItemModelType.SIMPLE_ITEM, block)
    }

    /**
     * Special kind of BlockItem that can be eaten, like Carrots, Potatoes, and Sweet Berries
     */
    fun foodBlockItem(name: String, block: () -> Block, food: FoodProperties): ObjectHolderDelegate<BlockItem> {
        return registerModelled(name, ItemModelType.SIMPLE_ITEM) {
            BlockItem(block(), Item.Properties().tab(Group).food(food))
        }
    }

    /**
     * Kotlin method to convert the FoodBuilder to a function with default params
     */
    fun food(nutrition: Int, saturation: Float, alwaysEdible: Boolean = false, fastEat: Boolean = false, meat: Boolean = false): FoodProperties {
        val foodBuilder = FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation)
        if (alwaysEdible) foodBuilder.alwaysEat()
        if (fastEat) foodBuilder.fast()
        if (meat) foodBuilder.meat()
        return foodBuilder.build()
    }

    fun blockItem(name: String, type: ItemModelType = ItemModelType.BLOCK_ITEM, block: () -> Block): ObjectHolderDelegate<BlockItem> {
        return registerModelled(name, type) { BlockItem(block(), Item.Properties().tab(Group)) }
    }

    /**
     * Registers an item and sets its model for data generation
     */
    fun <T : Item> registerModelled(name: String, type: ItemModelType, supplier: () -> T): ObjectHolderDelegate<T> {
        val obj = HItemsNew.register(name, supplier)
        HItemsNew.onceRegistered { type.add(obj) }
        return obj
    }
}