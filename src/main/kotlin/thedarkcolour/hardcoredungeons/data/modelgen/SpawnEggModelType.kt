package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.item.Item
import net.minecraftforge.client.model.generators.ModelFile
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
            .parent(ModelFile.UncheckedModelFile(gen.mcLoc("item/template_spawn_egg")))
}