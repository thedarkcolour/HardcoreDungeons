package thedarkcolour.hardcoredungeons.data.modelgen

import thedarkcolour.hardcoredungeons.block.decoration.TrapDoorBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class TrapDoorModelType : BlockModelType<TrapDoorBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: TrapDoorBlock, gen: ModelGenerator) {
        gen.trapdoorBlock(block, gen.modLoc("block/" + block.registryName!!.path), true)
    }
}