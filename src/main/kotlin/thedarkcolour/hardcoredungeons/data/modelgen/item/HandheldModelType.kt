package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.data.ModelGenerator

/**
 * Tools, swords, staves, etc.
 */
class HandheldModelType : ItemModelType() {
    /**
     * Generate a model for the item.
     */
    override fun process(item: Item, gen: ModelGenerator) {
        handHeldItem(item, gen)
    }

    private fun handHeldItem(item: Item, gen: ModelGenerator) =
        handHeldItem(item.registryName!!.path, gen)

    private fun handHeldItem(path: String, gen: ModelGenerator) =
        singleLayerItem(gen, path, gen.mcLoc("item/handheld"))

    private fun singleLayerItem(gen: ModelGenerator, path: String, parent: ResourceLocation) =
        gen.itemModels().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(parent))
            .texture("layer0", gen.modLoc("item/$path"))
}