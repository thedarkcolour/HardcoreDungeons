package thedarkcolour.hardcoredungeons.block.base

import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.registry.HItemsNew
import kotlin.properties.ReadOnlyProperty

private typealias ItemObject<T> = ReadOnlyProperty<T, Any?>

// todo write comments
object ItemMaker {
    fun <T : Item> handheld(name: String, supplier: () -> T): ItemObject<T> {
        return registerModelled(name, ItemModelType.HANDHELD_ITEM, supplier)
    }

    fun <T : Item> simple(name: String, supplier: () -> T): ItemObject<T> {
        return registerModelled(name, ItemModelType.SIMPLE_ITEM, supplier)
    }

    fun simpleItem(name: String): ItemObject<Item> {
        return simple(name) { Item(Item.Properties().tab(Group)) }
    }

    fun simpleBlockItem(name: String, block: () -> Block): ReadOnlyProperty<BlockItem, Any?> {
        return blockItem(name, ItemModelType.SIMPLE_ITEM, block)
    }

    fun foodBlockItem(name: String, block: () -> Block, food: FoodProperties): ReadOnlyProperty<BlockItem, Any?> {
        return registerModelled(name, ItemModelType.SIMPLE_ITEM) {
            BlockItem(block(), Item.Properties().tab(Group).food(food))
        }
    }

    fun food(nutrition: Int, saturation: Float, alwaysEdible: Boolean = false, fastEat: Boolean = false, meat: Boolean = false): FoodProperties {
        val foodBuilder = FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation)
        if (alwaysEdible) foodBuilder.alwaysEat()
        if (fastEat) foodBuilder.fast()
        if (meat) foodBuilder.meat()
        return foodBuilder.build()
    }

    fun blockItem(name: String, type: ItemModelType = ItemModelType.BLOCK_ITEM, block: () -> Block): ReadOnlyProperty<BlockItem, Any?> {
        return registerModelled(name, type) { BlockItem(block(), Item.Properties().tab(Group)) }
    }

    fun <T : Item> registerModelled(name: String, type: ItemModelType, supplier: () -> T): ReadOnlyProperty<T, Any?> {
        val obj = HItemsNew.register(name, supplier)
        HItemsNew.onceRegistered { type.add(obj) }
        return obj
    }
}