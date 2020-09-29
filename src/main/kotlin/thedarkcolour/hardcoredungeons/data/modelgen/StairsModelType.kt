package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import thedarkcolour.hardcoredungeons.block.decoration.StairsBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class StairsModelType : ModelType<StairsBlock>() {
    private val map = HashMap<StairsBlock, Block>()

    fun add(stairs: StairsBlock, fullBlock: Block) {
        map[stairs] = fullBlock
    }

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for ((s, f) in map) {
            stairs(s, f, gen)
        }
    }

    private fun stairs(stairs: Block, block: Block, gen: ModelGenerator) {
        if (stairs is StairsBlock) {
            gen.stairsBlock(stairs, gen.modLoc("block/" + block.registryName!!.path))
            gen.blockItemModel(stairs)
        } else {
            throw IllegalArgumentException("Not a stair")
        }
    }
}