package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class PillarModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        pillar(block, appearance, gen)
    }

    fun pillar(pillar: Block, side: Block, gen: ModelGenerator) {
        gen.blockItemModel(pillar)

        val path = pillar.registryName!!.path
        val texture = gen.textureLoc(path.replace("wood", "log"))
        val file = ModelFile.UncheckedModelFile(texture)
        val x = ConfiguredModel.builder().rotationX(90).rotationY(90).modelFile(file).buildLast()
        val y = ConfiguredModel.builder().modelFile(file).buildLast()
        val z = ConfiguredModel.builder().rotationX(90).modelFile(file).buildLast()

        val model = gen.blockModel(path)
            .parent(gen.mcModel("block/cube_column"))
            .texture("side", texture)

        // Create a new texture or reuse an existing one
        when {
            path.endsWith("wood") -> model.texture("end", texture)
            pillar == side -> model.texture("end", gen.textureLoc(pillar, suffix = "_end"))
            else -> model.texture("end", gen.textureLoc(side))
        }

        gen.getVariantBuilder(pillar)
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
