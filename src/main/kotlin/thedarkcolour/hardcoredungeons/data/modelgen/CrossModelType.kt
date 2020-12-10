package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class CrossModelType : BlockModelType<Block>() {
    override fun process(block: Block, gen: ModelGenerator) {
        gen.simpleBlock(block, gen.models().cross(gen.name(block), gen.blockTexture(block)))
    }
}