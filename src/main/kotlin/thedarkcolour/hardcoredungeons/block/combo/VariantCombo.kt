package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class VariantCombo(props: HProperties, name: String, vararg variants: String) {
    val variants = Array(variants.size) { i ->
        BlockMaker.cubeAllWithItem(variants[i] + "_" + name, props)
    }

    // Manually called
    fun addTag(tags: DataTags, blockTag: TagKey<Block>, itemTag: TagKey<Item>) {
        for (variant in variants) {
            tags.block(blockTag, itemTag, variant.get())
        }
    }
}