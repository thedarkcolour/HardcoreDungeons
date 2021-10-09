package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.block.Block
import net.minecraft.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel
import thedarkcolour.hardcoredungeons.block.misc.BonusFarmlandBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class FarmlandModelType : BlockModelType<BonusFarmlandBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: BonusFarmlandBlock, appearance: Block, gen: ModelGenerator) {
        gen.blockItemModel(block)
        farmland(gen, block)
    }

    // todo appearance
    private fun farmland(gen: ModelGenerator, block: BonusFarmlandBlock) {
        val state = gen.getVariantBuilder(block)
        val soil = gen.textureLoc(block.getSoilState().block)

        val path = block.registryName!!.path

        val normal = gen.blockModel(path)
            .parent(gen.mcModel("block/template_farmland"))
            .texture("particle", soil)
            .texture("dirt", soil)
            .texture("top", gen.textureLoc(block))
        val moist = gen.blockModel(path + "_moist")
            .parent(gen.mcModel("block/template_farmland"))
            .texture("particle", soil)
            .texture("dirt", soil)
            .texture("top", gen.textureLoc(block, suffix = "_moist"))

        val configuredNormal = ConfiguredModel(normal)

        for (i in 0..6) {
            state.partialState()
                .with(BlockStateProperties.MOISTURE, i)
                .addModels(configuredNormal)
        }

        state.partialState()
            .with(BlockStateProperties.MOISTURE, 7)
            .addModels(ConfiguredModel(moist))
    }
}