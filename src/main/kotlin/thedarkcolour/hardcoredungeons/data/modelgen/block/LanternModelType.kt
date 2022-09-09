package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.block.LanternBlock
import net.minecraft.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class LanternModelType : BlockModelType<LanternBlock>() {
    override fun process(block: LanternBlock, appearance: Block, gen: ModelGenerator) {
        val path = block.registryName!!.path
        val texture = gen.textureLoc(path)

        val sitting = gen.blockModel(path)
            .parent(gen.mcModel("block/template_lantern"))
            .texture("lantern", texture)
        val hanging = gen.blockModel(path + "_hanging")
            .parent(gen.mcModel("block/template_hanging_lantern"))
            .texture("lantern", texture)

        gen.getVariantBuilder(block)
            .partialState()
            .with(BlockStateProperties.HANGING, false)
            .addModels(ConfiguredModel(gen.getModelFile(sitting)))
            .partialState()
            .with(BlockStateProperties.HANGING, true)
            .addModels(ConfiguredModel(gen.getModelFile(hanging)))
    }
}