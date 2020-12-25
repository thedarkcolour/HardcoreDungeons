package thedarkcolour.hardcoredungeons.block.plant.misc

import net.minecraft.block.CarrotBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Items
import net.minecraft.util.IItemProvider
import thedarkcolour.hardcoredungeons.registry.HBlocks

class GoldenCarrotsBlock(properties: Properties) : CarrotBlock(properties) {
    init {
        BlockItem.BLOCK_TO_ITEM[HBlocks.GOLDEN_CARROTS] = Items.GOLDEN_CARROT
    }

    override fun getSeedsItem(): IItemProvider {
        return Items.GOLDEN_CARROT
    }
}