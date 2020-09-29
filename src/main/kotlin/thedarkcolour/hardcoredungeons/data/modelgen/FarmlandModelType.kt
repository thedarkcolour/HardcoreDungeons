package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel
import thedarkcolour.hardcoredungeons.block.misc.BonusFarmlandBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class FarmlandModelType : BlockModelType<BonusFarmlandBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: BonusFarmlandBlock, gen: ModelGenerator) {
        gen.blockItemModel(block)
        farmland(gen, block)
    }

    private fun farmland(gen: ModelGenerator, block: BonusFarmlandBlock) {
        val state = gen.getVariantBuilder(block)
        val soil = gen.blockLoc(block.getSoilState().block)

        val path = block.registryName!!.path

        val normal = gen.blockModel(path)
            .parent(gen.mcFile("block/template_farmland"))
            .texture("particle", soil)
            .texture("dirt", soil)
            .texture("top", gen.blockLoc(block))
        val moist = gen.blockModel(path + "_moist")
            .parent(gen.mcFile("block/template_farmland"))
            .texture("particle", soil)
            .texture("dirt", soil)
            .texture("top", gen.blockLoc(block, suffix = "_moist"))

        val configuredNormal = ConfiguredModel(normal)

        for (i in 0..6) {
            state.partialState()
                .with(BlockStateProperties.MOISTURE_0_7, i)
                .addModels(configuredNormal)
        }

        state.partialState()
            .with(BlockStateProperties.MOISTURE_0_7, 7)
            .addModels(ConfiguredModel(moist))
    }
}