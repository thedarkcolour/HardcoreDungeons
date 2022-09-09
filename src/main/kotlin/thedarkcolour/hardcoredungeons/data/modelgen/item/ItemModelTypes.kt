package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.item.Item
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.data.ModelGenerator

/**
 * 3D block item that defers to the block model's transformations
 */
class NewBlockItemModelType : ItemModelType() {
    override fun process(item: Item, gen: ModelGenerator) {
        val name = item.registryName!!.path
        gen.blockItemModel(name, name)
    }
}

class TrapdoorItemModelType : ItemModelType() {
    override fun process(item: Item, gen: ModelGenerator) {
        val name = item.registryName!!.path
        gen.blockItemModel(name, name + "_bottom")
    }
}

class ParentedModelType(parentLoc: ResourceLocation) : ItemModelType() {
    private val parentFile = ModelFile.UncheckedModelFile(parentLoc)

    override fun process(item: Item, gen: ModelGenerator) {
        val path = item.registryName!!.path

        gen.itemModels().getBuilder(path)
            .parent(parentFile)
            .texture("layer0", gen.modLoc("item/$path"))
    }
}

/**
 * Depends on the block model existing
 */
class WallItemModelType : ItemModelType() {
    override fun process(item: Item, gen: ModelGenerator) {
        val path = item.registryName!!.path

        // item model
        gen.itemModels().getBuilder(path)
            .parent(gen.modModel("block/" + path + "_inventory"))
    }
}

class SpawnEggModelType : ItemModelType() {
    override fun process(item: Item, gen: ModelGenerator) {
        gen.itemModels().getBuilder(item.registryName!!.path)
            .parent(gen.mcModel("item/template_spawn_egg"))
    }
}