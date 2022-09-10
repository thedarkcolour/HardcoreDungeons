package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.ModelType
import thedarkcolour.hardcoredungeons.legacy.ObjectHolderDelegate
import thedarkcolour.hardcoredungeons.util.modLoc

abstract class ItemModelType : ModelType<ObjectHolderDelegate<out Item>>() {
    private val items = ArrayList<ObjectHolderDelegate<out Item>>()

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for (item in items) {
            try {
                process(item(), gen)
            } catch (e: Exception) {
                println("Error generating ${item.registryObject.id}: " + e.message)
                continue
            }
        }
    }

    /**
     * Generate a model for the item.
     */
    abstract fun process(item: Item, gen: ModelGenerator)

    fun add(item: ObjectHolderDelegate<out Item>) {
        items.add(item)
    }

    companion object {
        val SIMPLE_ITEM = SimpleItemModelType() // 2d
        val BLOCK_ITEM = NewBlockItemModelType() // 3d
        val TRAPDOOR_ITEM = TrapdoorItemModelType()
        val HANDHELD_ITEM = ParentedModelType(ResourceLocation("item/handheld")) // tool
        val ROTATED_HANDHELD_ITEM = ParentedModelType(modLoc("item/handheld_rotated")) // tool with special gui rotations
        val ROTATED_GUN_ITEM = ParentedModelType(modLoc("item/gun_rotated")) // rotated handheld + 3rd person rotations for guns
        val SPAWN_EGG_ITEM = SpawnEggModelType() // egg
        val WALL_FENCE_ITEM = WallItemModelType()
    }
}