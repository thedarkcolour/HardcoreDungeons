package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.world.item.Item
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.ModelType
import kotlin.properties.ReadOnlyProperty

abstract class ItemModelType : ModelType<() -> Item>() {
    private val items: ArrayList<ReadOnlyProperty<Item, Any?>> = ArrayList()

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for (item in items) {
            try {
                process(item(), gen)
            } catch (e: Exception) {
                e.printStackTrace()
                continue
            }
        }
    }

    /**
     * Generate a model for the item.
     */
    abstract fun process(item: Item, gen: ModelGenerator)

    // Should be an ObjectHolderDelegate
    fun add(item: ReadOnlyProperty<Item, Any?>) {
        items.add(item)
    }

    companion object {
        val SIMPLE_ITEM = SimpleItemModelType() // 2d
        val BLOCK_ITEM = NewBlockItemModelType() // 3d
        val HANDHELD_ITEM = HandheldModelType() // tool
        val SPAWN_EGG_ITEM = SpawnEggModelType() // egg
        val WALL_FENCE_ITEM = WallItemModelType()
    }
}