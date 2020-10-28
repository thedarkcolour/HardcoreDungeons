package thedarkcolour.hardcoredungeons.data.modelgen

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.block.AbstractButtonBlock
import net.minecraft.block.material.Material
import net.minecraft.state.properties.AttachFace
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.ConfiguredModel
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.WallModelType.Companion.HORIZONTALS

class ButtonModelType : BlockModelType<AbstractButtonBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: AbstractButtonBlock, gen: ModelGenerator) {
        val path = block.registryName!!.path
        val builder = gen.getVariantBuilder(block)

        // planks, stone, etc.
        val originalTexture = if (block.material == Material.WOOD || block.material == Material.NETHER_WOOD)  {
            path.removeSuffix("_button") + "_planks"
        } else {
            path.removeSuffix("_button")
        }

        val buttonModel = gen.blockModel(path)
            .parent(gen.mcFile("block/button"))
            .texture("texture", originalTexture)
        val buttonPressedModel = gen.blockModel(path + "_pressed")
            .parent(gen.mcFile("block/button_pressed"))
            .texture("texture", originalTexture)

        for (face in AttachFace.values()) {
            for (direction in HORIZONTALS) {
                builder.partialState()
                    .with(BlockStateProperties.FACE, face)
                    .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .with(BlockStateProperties.POWERED, false)
                    .addModels(modelWithRotation(gen, buttonModel, rotateX = FACE_2_ROT.getInt(face), rotateY = DIR_2_ROT.getInt(direction)))
                builder.partialState()
                    .with(BlockStateProperties.FACE, face)
                    .with(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .with(BlockStateProperties.POWERED, false)
                    .addModels(modelWithRotation(gen, buttonPressedModel, rotateX = FACE_2_ROT.getInt(face), rotateY = DIR_2_ROT.getInt(direction)))
            }
        }
    }

    fun modelWithRotation(gen: ModelGenerator, model: BlockModelBuilder, rotateX: Int = 0, rotateY: Int = 0, uvLock: Boolean = false): ConfiguredModel {
        return ConfiguredModel(gen.getModelFile(model), rotateX, rotateY, uvLock, ConfiguredModel.DEFAULT_WEIGHT)
    }

    companion object {
        val FACE_2_ROT = Object2IntOpenHashMap<AttachFace>(3)
        val DIR_2_ROT = Object2IntOpenHashMap<Direction>(4)

        init {
            FACE_2_ROT.put(AttachFace.FLOOR, 0)
            FACE_2_ROT.put(AttachFace.WALL, 90)
            FACE_2_ROT.put(AttachFace.CEILING, 180)

            DIR_2_ROT.put(Direction.SOUTH,   0)
            DIR_2_ROT.put(Direction.WEST,   90)
            DIR_2_ROT.put(Direction.NORTH, 180)
            DIR_2_ROT.put(Direction.EAST,  270)
        }
    }
}