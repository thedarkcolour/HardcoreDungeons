package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.item.Item
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class NewBlockItemModelType : ItemModelType() {
    override fun process(item: Item, gen: ModelGenerator) {
        val name = item.registryName!!.path
        gen.blockItemModel(name, name)
    }
}