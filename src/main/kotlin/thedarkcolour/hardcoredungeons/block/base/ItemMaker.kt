package thedarkcolour.hardcoredungeons.block.base

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.ForgeSpawnEggItem
import net.minecraftforge.data.loading.DatagenModLoader
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.legacy.ObjectHolderDelegate
import thedarkcolour.hardcoredungeons.registry.items.HItemsNew

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

    fun resourceItem(name: String): ObjectHolderDelegate<Item> {
        return simple(name) { Item(Item.Properties().tab(Group)) }
    }

    fun spawnEgg(entity: ObjectHolderDelegate<out EntityType<out Mob>>, background: Int, highlight: Int): ObjectHolderDelegate<ForgeSpawnEggItem> {
        return simple(entity.registryObject.id.path + "_spawn_egg") { ForgeSpawnEggItem(entity, background, highlight, EmptyProperties) }
    }

    fun simpleBlockItem(name: String, block: () -> Block): ObjectHolderDelegate<BlockItem> {
        return blockItem(name, ItemModelType.SIMPLE_ITEM, block)
    }

    fun foodBlockItem(name: String, block: () -> Block, food: FoodProperties): ObjectHolderDelegate<Item> {
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

    fun blockItem(name: String, type: ItemModelType = ItemModelType.BLOCK_ITEM, block: () -> Block): ObjectHolderDelegate<BlockItem> {
        return registerModelled(name, type) { BlockItem(block(), EmptyProperties) }
    }

    fun <T : Item> registerModelled(name: String, type: ItemModelType, supplier: () -> T): ObjectHolderDelegate<T> {
        val obj = HItemsNew.register(name, supplier)
        if (DatagenModLoader.isRunningDataGen())
            type.add(obj)
        return obj
    }

    // Unmodifiable
    object EmptyProperties : Item.Properties() {
        init {
            tab(Group)
        }

        private fun warn(): Item.Properties {
            HardcoreDungeons.LOGGER.warn("Tried to set property on empty item properties instance!")
            return this
        }

        override fun food(p_41490_: FoodProperties) = warn()
        override fun stacksTo(p_41488_: Int) = warn()
        override fun defaultDurability(p_41500_: Int) = warn()
        override fun durability(p_41504_: Int) = warn()
        override fun craftRemainder(p_41496_: Item) = warn()
        override fun tab(p_41492_: CreativeModeTab) = warn()
        override fun rarity(p_41498_: Rarity) = warn()
        override fun fireResistant() = warn()
        override fun setNoRepair() = warn()
    }
}