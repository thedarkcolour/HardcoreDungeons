package thedarkcolour.hardcoredungeons.block.plant.misc

import net.minecraft.block.CarrotBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Items
import net.minecraft.util.IItemProvider

class GoldenCarrotsBlock(properties: Properties) : CarrotBlock(properties) {
    init {
        BlockItem.BY_BLOCK[this] = Items.GOLDEN_CARROT
    }

    override fun getBaseSeedId(): IItemProvider {
        return Items.GOLDEN_CARROT
    }
}