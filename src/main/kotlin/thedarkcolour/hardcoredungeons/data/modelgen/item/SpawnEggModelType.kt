package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.item.Item
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType

class SpawnEggModelType : ItemModelType() {
    /**
     * Generate a model for the item.
     */
    override fun process(item: Item, gen: ModelGenerator) {
        spawnEggItem(item, gen)
    }

    private fun spawnEggItem(item: Item, gen: ModelGenerator) =
        spawnEggItem(item.registryName!!.path, gen)

    private fun spawnEggItem(path: String, gen: ModelGenerator) =
        gen.itemModels().getBuilder(path)
            .parent(gen.mcModel("item/template_spawn_egg"))
}