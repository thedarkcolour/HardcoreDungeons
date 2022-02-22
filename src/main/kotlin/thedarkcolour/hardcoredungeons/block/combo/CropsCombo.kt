package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.Block
import net.minecraft.item.Food
import net.minecraft.tags.BlockTags
import net.minecraft.util.IItemProvider
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.registry.HBlocks

class CropsCombo(name: String, food: Food, cropSupplier: () -> Block) : ICombo, IItemProvider {
    val crop by HBlocks.register(name, cropSupplier)
    val item by ItemMaker.foodBlockItem(name, ::crop, food)

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.CROPS, crop)
        tags.block(BlockTags.BEE_GROWABLES, crop)
    }

    override fun asItem() = item
}