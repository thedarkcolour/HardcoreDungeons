package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.item.Item
import thedarkcolour.hardcoredungeons.data.ModelGenerator

// This depends on the block model existing
class WallItemModelType : ItemModelType() {
    override fun process(item: Item, gen: ModelGenerator) {
        val path = item.registryName!!.path

        // item model
        gen.itemModels().getBuilder(path)
            .parent(gen.modModel("block/" + path + "_inventory"))
    }
}