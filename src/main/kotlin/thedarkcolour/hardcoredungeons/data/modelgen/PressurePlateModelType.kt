package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.PressurePlateBlock
import net.minecraft.block.WoodButtonBlock
import net.minecraft.state.properties.AttachFace
import net.minecraft.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.ConfiguredModel
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class PressurePlateModelType : BlockModelType<PressurePlateBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: PressurePlateBlock, gen: ModelGenerator) {
        val path = block.registryName!!.path
        val builder = gen.getVariantBuilder(block)

        // planks, stone, etc.
        val originalTexture = if (block is WoodButtonBlock)  {
            path.removeSuffix("_button") + "_planks"
        } else {
            path.removeSuffix("_button")
        }

        val buttonModel = gen.blockModel(path)
            .parent(gen.mcFile("block/button"))
            .texture("texture", "block/$originalTexture")
        val buttonPressedModel = gen.blockModel(path + "_pressed")
            .parent(gen.mcFile("block/button_pressed"))
            .texture("texture", "block/$originalTexture")

        for (face in AttachFace.values()) {
            for (direction in WallModelType.HORIZONTALS) {
                builder.partialState()
                    .with(BlockStateProperties.FACE, face)
                    .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .with(BlockStateProperties.POWERED, false)
                    .addModels(modelWithRotation(gen, buttonModel, rotateX = ButtonModelType.FACE_2_ROT.getInt(face), rotateY = ButtonModelType.DIR_2_ROT.getInt(direction)))
                builder.partialState()
                    .with(BlockStateProperties.FACE, face)
                    .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .with(BlockStateProperties.POWERED, true)
                    .addModels(modelWithRotation(gen, buttonPressedModel, rotateX = ButtonModelType.FACE_2_ROT.getInt(face), rotateY = ButtonModelType.DIR_2_ROT.getInt(direction)))
            }
        }
    }

    private fun modelWithRotation(gen: ModelGenerator, model: BlockModelBuilder, rotateX: Int = 0, rotateY: Int = 0, uvLock: Boolean = false): ConfiguredModel {
        return ConfiguredModel(gen.getModelFile(model), rotateX, rotateY, uvLock, ConfiguredModel.DEFAULT_WEIGHT)
    }
}