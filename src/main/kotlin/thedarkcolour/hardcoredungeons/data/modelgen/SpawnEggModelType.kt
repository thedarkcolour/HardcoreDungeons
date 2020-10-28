package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.item.Item
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class SpawnEggModelType : ItemModelType() {
    /**
     * Generate a model for the item.
     */
    override fun apply(item: Item, gen: ModelGenerator) {
        spawnEggItem(item, gen)
    }

    private fun spawnEggItem(item: Item, gen: ModelGenerator) =
        spawnEggItem(item.registryName!!.path, gen)

    private fun spawnEggItem(path: String, gen: ModelGenerator) =
        gen.itemModels().getBuilder(path)
            .parent(gen.mcFile("item/template_spawn_egg"))
}