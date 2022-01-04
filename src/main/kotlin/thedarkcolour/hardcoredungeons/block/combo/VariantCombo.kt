package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tags.ITag
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.ItemTagGenerator

class VariantCombo(props: HProperties, name: String, vararg variants: String) {
    val variants = Array(variants.size) { i ->
        BlockMaker.cubeAllWithItem(variants[i] + "_" + name, props)
    }

    fun addTag(gen: BlockTagGenerator, tag: ITag.INamedTag<Block>) {
        val b = gen.tag(tag)

        for (variant in variants) {
            b.add(variant())
        }
    }

    fun addTag(gen: ItemTagGenerator, tag: ITag.INamedTag<Item>) {
        val i = gen.tag(tag)

        for (variant in variants) {
            i.add(variant().asItem())
        }
    }
}