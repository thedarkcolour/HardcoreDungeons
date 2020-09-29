package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import thedarkcolour.hardcoredungeons.block.decoration.SlabBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class SlabModelType : ModelType<SlabBlock>() {
    private val block2Slabs = HashMap<SlabBlock, Block>()

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for ((slab, full) in block2Slabs) {
            slab(gen, slab, full)

            gen.blockItemModel(slab)
        }
    }

    fun slab(gen: ModelGenerator, slab: SlabBlock, block: Block) {
        val loc = gen.modLoc("block/" + block.registryName!!.path)
        gen.slabBlock(slab, loc, loc)
    }

    fun add(block: SlabBlock, fullBlock: Block) {
        block2Slabs[block] = fullBlock
    }
}
