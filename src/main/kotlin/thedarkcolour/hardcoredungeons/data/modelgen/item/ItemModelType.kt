package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.ModelType
import thedarkcolour.hardcoredungeons.util.modLoc

abstract class ItemModelType : ModelType<() -> Item>() {
    private val items = ArrayList<() -> Item>()

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for (item in items) {
            try {
                process(item(), gen)
            } catch (e: Exception) {
                println("Error generating ${item().registryName}: " + e.message)
                continue
            }
        }
    }

    /**
     * Generate a model for the item.
     */
    abstract fun process(item: Item, gen: ModelGenerator)

    // Should be an ObjectHolderDelegate
    fun add(item: () -> Item) {
        items.add(item)
    }

    companion object {
        val SIMPLE_ITEM = SimpleItemModelType() // 2d
        val BLOCK_ITEM = NewBlockItemModelType() // 3d
        val HANDHELD_ITEM = ParentedModelType(ResourceLocation("item/handheld")) // tool
        val ROTATED_HANDHELD_ITEM = ParentedModelType(modLoc("item/handheld_rotated")) // tool with special gui rotations
        val ROTATED_GUN_ITEM = ParentedModelType(modLoc("item/gun_rotated")) // rotated handheld + 3rd person rotations for guns
        val SPAWN_EGG_ITEM = SpawnEggModelType() // egg
        val WALL_FENCE_ITEM = WallItemModelType()
    }
}