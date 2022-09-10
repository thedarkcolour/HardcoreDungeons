package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.util.registryName

class PillarModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        pillar(block, appearance, gen)
    }

    private fun pillar(block: Block, side: Block, gen: ModelGenerator) {
        gen.blockItemModel(block)

        val path = block.registryName!!.path
        val sideTexture = gen.textureLoc(path.replace("wood", "log"))
        val file = ModelFile.UncheckedModelFile(gen.textureLoc(path))
        val x = ConfiguredModel.builder().rotationX(90).rotationY(90).modelFile(file).buildLast()
        val y = ConfiguredModel.builder().modelFile(file).buildLast()
        val z = ConfiguredModel.builder().rotationX(90).modelFile(file).buildLast()

        val model = gen.blockModel(path)
            .parent(gen.mcModel("block/cube_column"))
            .texture("side", sideTexture)

        // Create a new texture or reuse an existing one
        when {
            path.endsWith("wood") -> model.texture("end", sideTexture) // for wood blocks
            block == side -> model.texture("end", gen.textureLoc(block, suffix = "_end")) // for normal logs
            else -> model.texture("end", gen.textureLoc(side)) // is this even used?
        }

        gen.getVariantBuilder(block)
            .partialState()
            .with(BlockStateProperties.AXIS, Direction.Axis.X)
            .addModels(x)
            .partialState()
            .with(BlockStateProperties.AXIS, Direction.Axis.Y)
            .addModels(y)
            .partialState()
            .with(BlockStateProperties.AXIS, Direction.Axis.Z)
            .addModels(z)
    }

    // We can probably just get rid of the side parameter since it's always the block anyway
    // end can be moved to appearance
}
