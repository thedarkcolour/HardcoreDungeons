package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.PressurePlateBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.util.registryName

class PressurePlateModelType : BlockModelType<PressurePlateBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: PressurePlateBlock, appearance: Block, gen: ModelGenerator) {
        val path = block.registryName!!.path
        val builder = gen.getVariantBuilder(block)

        // planks, stone, etc.
        val originalTexture = if (appearance.registryName!!.path.endsWith("planks"))  {
            path.removeSuffix("_pressure_plate") + "_planks"
        } else {
            path.removeSuffix("_pressure_plate")
        }

        val normal = gen.blockModel(path)
            .parent(gen.mcModel("block/pressure_plate_up"))
            .texture("texture", "block/$originalTexture")
        val pressed = gen.blockModel(path + "_pressed")
            .parent(gen.mcModel("block/pressure_plate_down"))
            .texture("texture", "block/$originalTexture")

        builder.partialState()
            .with(BlockStateProperties.POWERED, false)
            .addModels(ConfiguredModel(normal))
        builder.partialState()
            .with(BlockStateProperties.POWERED, true)
            .addModels(ConfiguredModel(pressed))

        gen.blockItemModel(block)
    }
}