package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.data.ModelGenerator

abstract class BlockModelType<B : Block> : ModelType<B>() {
    val blocks = hashMapOf<B, B>()

    @Deprecated(replaceWith = ReplaceWith("add(t,t)"), message = "")
    fun add(t: B) {
        add(t, t)
    }

    fun add(t: B, o: B) {
        if (t.registryName!!.path.contains("air"))
            HardcoreDungeons.LOGGER.warn("Block $t has suspicious name")

        blocks[t] = o
    }

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for ((block, appearance) in blocks) {
            process(block, appearance, gen)
        }
    }

    /**
     * Generate a model for block [block].
     */
    abstract fun process(block: B, appearance: B, gen: ModelGenerator)
}