package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.ItemTagGenerator

/**
 * Used in combos as an abstraction layer for Item and Block tag data generation
 */
sealed interface DataTags {
    fun item(tag: TagKey<Item>, vararg item: ItemLike) = Unit
    fun block(tag: TagKey<Block>, vararg block: Block) = Unit
    // Adds a tag to these tags
    fun tag(tagBlock: TagKey<Block>, tagItem: TagKey<Item>, blocks: TagKey<Block>, items: TagKey<Item>)

    // Adds the block to the block tag and its item form to the items tag
    fun block(tagBlock: TagKey<Block>, tagItem: TagKey<Item>, vararg blocks: Block) {
        block(tagBlock, *blocks)
        item(tagItem, *blocks)
    }

    class Items(val items: ItemTagGenerator) : DataTags {
        override fun item(tag: TagKey<Item>, vararg item: ItemLike) {
            val builder = this.items.tag(tag)

            for (i in item) {
                builder.add(i.asItem())
            }
        }

        override fun tag(tagBlock: TagKey<Block>, tagItem: TagKey<Item>, blocks: TagKey<Block>, items: TagKey<Item>) {
            this.items.tag(tagItem).addTag(items)
        }
    }

    class Blocks(val blocks: BlockTagGenerator) : DataTags {
        override fun block(tag: TagKey<Block>, vararg block: Block) {
            this.blocks.tag(tag).add(*block)
        }

        override fun tag(tagBlock: TagKey<Block>, tagItem: TagKey<Item>, blocks: TagKey<Block>, items: TagKey<Item>) {
            this.blocks.tag(tagBlock).addTag(blocks)
        }
    }
}