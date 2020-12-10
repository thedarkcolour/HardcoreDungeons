package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.data.ModelGenerator

abstract class BlockModelType<B : Block> : ModelType<B>() {
    val blocks = arrayListOf<B>()

    fun add(t: B) {
        if (t.registryName!!.path.contains("air"))
            HardcoreDungeons.LOGGER.warn("Block $t has suspicious name")

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