package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.item.Item
import net.minecraft.util.IItemProvider
import thedarkcolour.hardcoredungeons.data.ModelGenerator

abstract class ItemModelType : ModelType<Item>() {
    private val items = ArrayList<Item>()

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for (item in items) {
            apply(item, gen)
        }
    }

    /**
     * Generate a model for the item.
     */
    abstract fun apply(item: Item, gen: ModelGenerator)

    fun add(item: IItemProvider) {
        items.add(item.asItem())
    }
}