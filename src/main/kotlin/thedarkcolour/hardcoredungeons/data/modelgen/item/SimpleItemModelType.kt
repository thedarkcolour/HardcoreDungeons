package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ItemModelBuilder
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.util.registryName

class SimpleItemModelType : ItemModelType() {
    /**
     * Generate a model for the item.
     */
    override fun process(item: Item, gen: ModelGenerator) {
        simpleItem(item, gen)
    }

    /**
     * Generates a 2d in-gui model with a single texture.
     * Think of the lantern or the hopper
     */
    private fun simpleItem(item: Item, gen: ModelGenerator): ItemModelBuilder {
        val path = item.registryName!!.path
        if (path.contains("air")) {
            HardcoreDungeons.LOGGER.warn("Item ${item.javaClass} has suspicious name")
        }
        return simpleItem(path, gen)
    }

    private fun simpleItem(path: String, gen: ModelGenerator): ItemModelBuilder {
        return gen.itemModels().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(gen.mcLoc("item/generated")))
            .texture("layer0", gen.modLoc("item/$path"))
    }
}