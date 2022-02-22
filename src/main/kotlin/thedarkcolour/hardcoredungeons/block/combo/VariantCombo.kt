package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tags.ITag
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class VariantCombo(props: HProperties, name: String, vararg variants: String) {
    val variants = Array(variants.size) { i ->
        BlockMaker.cubeAllWithItem(variants[i] + "_" + name, props)
    }

    fun addTag(tags: DataTags, blockTag: ITag.INamedTag<Block>, itemTag: ITag.INamedTag<Item>) {
        for (variant in variants) {
            tags.block(blockTag, itemTag, variant.get())
        }
    }
}