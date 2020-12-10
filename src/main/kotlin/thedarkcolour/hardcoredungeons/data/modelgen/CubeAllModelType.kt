package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class CubeAllModelType : BlockModelType<Block>() {
    override fun process(block: Block, gen: ModelGenerator) {
        // block state and model
        gen.simpleBlock(block)
        // item model
        gen.blockItemModel(block)
    }
}