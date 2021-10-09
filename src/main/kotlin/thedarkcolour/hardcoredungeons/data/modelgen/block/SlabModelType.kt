package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class SlabModelType : BlockModelType<SlabBlock>() {
    override fun process(block: SlabBlock, appearance: Block, gen: ModelGenerator) {
        slab(gen, block, appearance)
    }

    fun slab(gen: ModelGenerator, slab: SlabBlock, block: Block) {
        val loc = gen.modLoc("block/" + block.registryName!!.path)
        gen.slabBlock(slab, loc, loc)
    }
}
