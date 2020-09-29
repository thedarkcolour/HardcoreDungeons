package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import thedarkcolour.hardcoredungeons.data.ModelGenerator

abstract class BlockModelType<B : Block> : ModelType<B>() {
    val blocks = arrayListOf<B>()

    fun add(t: B) {
        blocks.add(t)
    }

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for (block in blocks) {
            process(block, gen)
        }
    }

    /**
     * Generate a model for block [block].
     */
    abstract fun process(block: B, gen: ModelGenerator)
}