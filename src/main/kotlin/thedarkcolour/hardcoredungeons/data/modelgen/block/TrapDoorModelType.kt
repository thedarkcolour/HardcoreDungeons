package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.block.Block
import net.minecraft.block.TrapDoorBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class TrapDoorModelType : BlockModelType<TrapDoorBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: TrapDoorBlock, appearance: Block, gen: ModelGenerator) {
        gen.trapdoorBlock(block, gen.modLoc("block/" + block.registryName!!.path), true)
    }
}