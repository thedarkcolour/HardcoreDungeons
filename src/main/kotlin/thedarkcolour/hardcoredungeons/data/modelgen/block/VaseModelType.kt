package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class VaseModelType : BlockModelType<Block>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        vase(block, gen)
    }

    private fun vase(vase: Block, gen: ModelGenerator) {
        val texture = gen.textureLoc(vase)

        gen.singleModel(vase)
            .parent(gen.modModel("block/template_vase"))
            .texture("vase", texture)
    }
}