package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.block.StairsBlock
import thedarkcolour.hardcoredungeons.block.base.HStairsBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class StairsModelType : BlockModelType<Block>() {
    private val map = HashMap<HStairsBlock, Block>()

    fun add(stairs: HStairsBlock, fullBlock: Block) {
        map[stairs] = fullBlock
    }

    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        if (block !is StairsBlock) throw IllegalArgumentException("stop")

        stairs(block, appearance, gen)
    }

    private fun stairs(stairs: StairsBlock, block: Block, gen: ModelGenerator) {
        gen.stairsBlock(stairs, gen.modLoc("block/" + block.registryName!!.path))
        gen.blockItemModel(stairs)
    }
}