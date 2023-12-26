package thedarkcolour.hardcoredungeons.data.modelgen.block

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.ButtonBlock
import net.minecraft.world.level.block.state.properties.AttachFace
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.block.WallModelType.Companion.HORIZONTALS
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.hardcoredungeons.util.registryName

class ButtonModelType : BlockModelType<ButtonBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: ButtonBlock, appearance: Block, gen: ModelGenerator) {
        val path = block.registryName!!.path
        val builder = gen.getVariantBuilder(block)

        // planks, stone, etc.
        val originalTexture = if (appearance.registryName!!.path.endsWith("planks"))  {
            path.removeSuffix("_button") + "_planks"
        } else {
            path.removeSuffix("_button")
        }

        val buttonModel = gen.blockModel(path)
            .parent(gen.mcModel("block/button"))
            .texture("texture", "block/$originalTexture")
        val buttonPressedModel = gen.blockModel(path + "_pressed")
            .parent(gen.mcModel("block/button_pressed"))
            .texture("texture", "block/$originalTexture")
        val inventoryModel = gen.blockModel(path + "_inventory")
            .parent(gen.mcModel("block/button_inventory"))
            .texture("texture", "block/$originalTexture")

        for (face in AttachFace.entries) {
            for (direction in HORIZONTALS) {
                builder.partialState()
                    .with(BlockStateProperties.ATTACH_FACE, face)
                    .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .with(BlockStateProperties.POWERED, false)
                    .addModels(modelWithRotation(gen, buttonModel, rotateX = FACE_2_ROT.getInt(face), rotateY = DIR_2_ROT.getInt(direction)))
                builder.partialState()
                    .with(BlockStateProperties.ATTACH_FACE, face)
                    .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .with(BlockStateProperties.POWERED, true)
                    .addModels(modelWithRotation(gen, buttonPressedModel, rotateX = FACE_2_ROT.getInt(face), rotateY = DIR_2_ROT.getInt(direction)))
            }
        }

        // add the item model
        gen.itemModels().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(modLoc("block/${path}_inventory")))
    }

    private fun modelWithRotation(gen: ModelGenerator, model: BlockModelBuilder, rotateX: Int = 0, rotateY: Int = 0, uvLock: Boolean = false): ConfiguredModel {
        return ConfiguredModel(gen.getModelFile(model), rotateX, rotateY, uvLock, ConfiguredModel.DEFAULT_WEIGHT)
    }

    companion object {
        val FACE_2_ROT = Object2IntOpenHashMap<AttachFace>(3)
        val DIR_2_ROT = Object2IntOpenHashMap<Direction>(4)

        init {
            FACE_2_ROT.put(AttachFace.FLOOR, 0)
            FACE_2_ROT.put(AttachFace.WALL, 90)
            FACE_2_ROT.put(AttachFace.CEILING, 180)

            DIR_2_ROT.put(Direction.NORTH,   0)
            DIR_2_ROT.put(Direction.EAST,   90)
            DIR_2_ROT.put(Direction.SOUTH, 180)
            DIR_2_ROT.put(Direction.WEST,  270)
        }
    }
}