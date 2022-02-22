package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.ItemTagGenerator

/**
 * Used in combos as an abstraction layer for Item and Block tag data generation
 */
sealed interface DataTags {
    fun item(tag: ITag.INamedTag<Item>, vararg item: IItemProvider) = Unit
    fun block(tag: ITag.INamedTag<Block>, vararg block: Block) = Unit
    // Adds a tag to these tags
    fun tag(tagBlock: ITag.INamedTag<Block>, tagItem: ITag.INamedTag<Item>, blocks: ITag.INamedTag<Block>, items: ITag.INamedTag<Item>)

    // Adds the block to the block tag and its item form to the items tag
    fun block(tagBlock: ITag.INamedTag<Block>, tagItem: ITag.INamedTag<Item>, vararg blocks: Block) {
        block(tagBlock, *blocks)
        item(tagItem, *blocks)
    }

    class Items(val items: ItemTagGenerator) : DataTags {
        override fun item(tag: ITag.INamedTag<Item>, vararg item: IItemProvider) {
            val builder = this.items.tag(tag)

            for (i in item) {
                builder.add(i.asItem())
            }
        }

        override fun tag(tagBlock: ITag.INamedTag<Block>, tagItem: ITag.INamedTag<Item>, blocks: ITag.INamedTag<Block>, items: ITag.INamedTag<Item>) {
            this.items.tag(tagItem).addTag(items)
        }
    }

    class Blocks(val blocks: BlockTagGenerator) : DataTags {
        override fun block(tag: ITag.INamedTag<Block>, vararg block: Block) {
            this.blocks.tag(tag).add(*block)
        }

        override fun tag(
            tagBlock: ITag.INamedTag<Block>,
            tagItem: ITag.INamedTag<Item>,
            blocks: ITag.INamedTag<Block>,
            items: ITag.INamedTag<Item>
        ) {
            this.blocks.tag(tagBlock).addTag(blocks)
        }
    }
}