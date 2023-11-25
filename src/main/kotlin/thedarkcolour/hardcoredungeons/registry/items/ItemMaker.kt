package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.ForgeSpawnEggItem
import net.minecraftforge.data.loading.DatagenModLoader
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

object ItemMaker {
    // Pickaxe, sword, and generally any tools should be handheld
    fun <T : Item> handheld(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.HANDHELD_ITEM, supplier)
    }

    // Handheld, but the inventory item model is rotated
    @Deprecated(replaceWith = ReplaceWith("ItemMaker.handheld(name, supplier)"), message = "?")
    fun <T : Item> rotatedHandheld(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.ROTATED_HANDHELD_ITEM, supplier)
    }

    // Gun model, but the inventory item model is rotated
    fun <T : Item> rotatedGun(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.ROTATED_GUN_ITEM, supplier)
    }

    // Basic 2d item, nothing special
    fun <T : Item> simple(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, ItemModelType.SIMPLE_ITEM, supplier)
    }

    // Crafting ingredient type of item
    fun resourceItem(name: String): ObjectHolderDelegate<Item> {
        return simple(name) { Item(DefaultProperties) }
    }

    // Spawn egg for any mob
    @Suppress("NAME_SHADOWING", "UNCHECKED_CAST")
    fun spawnEgg(entityType: KProperty0<EntityType<*>>, baseColor: Int, spotColor: Int) : ObjectHolderDelegate<ForgeSpawnEggItem> {
        entityType.isAccessible = true
        val entityType = entityType.getDelegate() as ObjectHolderDelegate<EntityType<out Mob>>

        return registerModelled(entityType.registryObject.id.path + "_spawn_egg", ItemModelType.SPAWN_EGG_ITEM) {
            ForgeSpawnEggItem(entityType, baseColor, spotColor, DefaultProperties)
        }
    }

    /**
     * 2d block item, hopper, campfire
     */
    fun simpleBlockItem(name: String, block: () -> Block): ObjectHolderDelegate<BlockItem> {
        return blockItem(name, ItemModelType.SIMPLE_ITEM, block)
    }

    /**
     * 2d placeable food item, carrots, chili peppers, and sweet berries
     */
    fun foodBlockItem(name: String, block: () -> Block, food: FoodProperties): ObjectHolderDelegate<Item> {
        return registerModelled(name, ItemModelType.SIMPLE_ITEM) {
            BlockItem(block(), Item.Properties().food(food))
        }
    }

    /**
     * Creates food properties with optional arguments instead of a huge builder chain
     */
    fun food(nutrition: Int, saturation: Float, alwaysEdible: Boolean = false, fastEat: Boolean = false, meat: Boolean = false): FoodProperties {
        val foodBuilder = FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation)
        if (alwaysEdible) foodBuilder.alwaysEat()
        if (fastEat) foodBuilder.fast()
        if (meat) foodBuilder.meat()
        return foodBuilder.build()
    }

    fun blockItem(name: String, type: ItemModelType = ItemModelType.BLOCK_ITEM, block: () -> Block): ObjectHolderDelegate<BlockItem> {
        return registerModelled(name, type) { BlockItem(block(), DefaultProperties) }
    }

    /**
     * Registers an item and assigns it to the specified model type, which gets generated in runData.
     */
    fun <T : Item> registerModelled(name: String, type: ItemModelType, supplier: () -> T): ObjectHolderDelegate<T> {
        val obj = HItems.register(name, supplier)
        if (DatagenModLoader.isRunningDataGen())
            type.add(obj)
        return obj
    }

    // Sets the creative tab
    fun props(): Item.Properties {
        return Item.Properties()
    }

    fun singleStack(): Item.Properties {
        return props().stacksTo(1)
    }

    // Unmodifiable, sets creative tab and nothing else
    object DefaultProperties : Item.Properties() {
        private var set = false

        init {
            set = true
        }

        /**
         * Print long annoying errors whenever something goes wrong
         */
        private fun warn(): Item.Properties {
            try {
                throw IllegalStateException("Tried to set property on empty item properties instance!")
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
            return this
        }

        override fun food(food: FoodProperties) = warn()
        override fun stacksTo(stacksTo: Int) = warn()
        override fun defaultDurability(defaultDurability: Int) = warn()
        override fun durability(durability: Int) = warn()
        override fun craftRemainder(craftRemainder: Item) = warn()
        override fun rarity(rarity: Rarity) = warn()
        override fun fireResistant() = warn()
        override fun setNoRepair() = warn()
    }
}
