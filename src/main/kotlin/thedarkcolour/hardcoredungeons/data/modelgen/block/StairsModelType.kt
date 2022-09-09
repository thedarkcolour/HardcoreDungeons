package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.StairBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.util.registryName

class StairsModelType : BlockModelType<Block>() {
    private val map = HashMap<StairBlock, Block>()

    fun add(stairs: StairBlock, fullBlock: Block) {
        map[stairs] = fullBlock
    }

    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        if (block !is StairBlock) throw IllegalArgumentException("stop")

        stairs(block, appearance, gen)
    }

    private fun stairs(stairs: StairBlock, block: Block, gen: ModelGenerator) {
        gen.stairsBlock(stairs, gen.modLoc("block/" + block.registryName!!.path))
        gen.blockItemModel(stairs)
    }
}