package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class ColumnModelType : ModelType<Pair<Block, Block>>() {
    val pairs = arrayListOf<Pair<Block, Block>>()

    fun add(sideEnd: Pair<Block, Block>) {
        pairs.add(sideEnd)
    }

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for ((side, end) in pairs) {
            column(gen, side, end)
        }
    }

    private fun column(gen: ModelGenerator, side: Block, end: Block) {
        gen.blockItemModel(side)

        val path = side.registryName!!.path

        val m = gen.models().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(gen.mcLoc("block/cube_column")))
            .texture("end", gen.blockLoc(end))
            .texture("side", gen.blockLoc(path))

        gen.getVariantBuilder(side)
            .partialState()
            .setModels(gen.configure(m))
    }
}