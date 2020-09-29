package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class VaseModelType : BlockModelType<Block>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: Block, gen: ModelGenerator) {
        vase(block, gen)
    }

    private fun vase(vase: Block, gen: ModelGenerator) {
        gen.blockItemModel(vase)

        val path = vase.registryName!!.path
        val blockPath = gen.blockLoc(path)
        val f = ConfiguredModel.builder().modelFile(ModelFile.UncheckedModelFile(blockPath)).buildLast()

        gen.models().getBuilder(path)
            .parent(gen.modFile("block/template_vase"))
            .texture("vase", blockPath)

        gen.getVariantBuilder(vase)
            .partialState()
            .addModels(f)
    }
}