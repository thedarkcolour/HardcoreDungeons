package thedarkcolour.hardcoredungeons.data.modelgen.item

import net.minecraft.block.Block
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.block.BlockModelType

class BlockItemModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        gen.blockItemModel(block)
    }
}