package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.Block
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.data.ModelGenerator

// todo
class PillarModelType : ModelType<Block>() {
    private val blocks = HashMap<Block, Pair<String?, String?>>()

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for ((block, end, side) in blocks) {
            pillar(block, end, side, gen)
        }
    }

    fun pillar(pillar: Block, end: String?, side: String?, gen: ModelGenerator) {
        gen.blockItemModel(pillar)

        val path = pillar.registryName!!.path
        val blockPath = gen.blockLoc(path)
        val file = ModelFile.UncheckedModelFile(blockPath)
        val x = ConfiguredModel.builder().rotationX(90).rotationY(90).modelFile(file).buildLast()
        val y = ConfiguredModel.builder().modelFile(file).buildLast()
        val z = ConfiguredModel.builder().rotationX(90).modelFile(file).buildLast()

        gen.models().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(gen.mcLoc("block/cube_column")))
            .texture("end", if (end == null) gen.blockLoc(pillar, suffix = "_end") else gen.blockLoc(end, namespace = HardcoreDungeons.ID))
            .texture("side", if (side == null) gen.blockLoc(pillar, suffix = "_side") else gen.blockLoc(side, namespace = HardcoreDungeons.ID))

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

    fun add(block: Block, end: String?, side: String?) {
        blocks[block] = end to side
    }

    private operator fun <K, V1, V2> Map.Entry<K, Pair<V1, V2>>.component2(): V1 {
        return value.first
    }

    private operator fun <K, V1, V2> Map.Entry<K, Pair<V1, V2>>.component3(): V2 {
        return value.second
    }
}
