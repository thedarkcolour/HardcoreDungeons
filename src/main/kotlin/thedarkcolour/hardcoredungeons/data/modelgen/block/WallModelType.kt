package thedarkcolour.hardcoredungeons.data.modelgen.block

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.block.state.properties.WallSide
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.hardcoredungeons.util.registryName

/**
 * Model type that produces the following:
 *
 *  - BlockState json file for wall
 *  - Low, tall, pillar wall model json files
 *  - **Item model** and inventory model json files
 *
 * You do not need to add an item model yourself.
 *
 * @author thedarkcolour
 */
class WallModelType : BlockModelType<Block>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        // Path of the wall ("cobblestone_wall")
        val path = block.registryName!!.path

        // The actual texture of this wall (cobblestone, granite)
        val textureLoc = gen.textureLoc(appearance.registryName!!.path)

        // The wall post model
        gen.blockModel(path + "_post")
            .parent(gen.mcBlock("template_wall_post"))
            .texture("wall", textureLoc)

        // Low & Tall wall models
        gen.blockModel(path + "_side")
            .parent(gen.mcBlock("template_wall_side"))
            .texture("wall", textureLoc)
        gen.blockModel(path + "_side_tall")
            .parent(gen.mcBlock("template_wall_side_tall"))
            .texture("wall", textureLoc)

        // item form of the wall
        gen.blockModel(path + "_inventory")
            .parent(gen.mcBlock("wall_inventory"))
            .texture("wall", textureLoc)

        val postFile = ModelFile.UncheckedModelFile(modLoc("block/" + path + "_post"))
        val lowWallFile = ModelFile.UncheckedModelFile(modLoc("block/" + path + "_side"))
        val tallWallFile = ModelFile.UncheckedModelFile(modLoc("block/" + path + "_side_tall"))

        // pillar
        val builder = gen.getMultipartBuilder(block)
        builder.part().modelFile(postFile).addModel().condition(BlockStateProperties.UP, true).end()

        // wall sides for low and high
        for (direction in HORIZONTALS) {
            builder.part().modelFile(lowWallFile).rotationY(DIR_TO_DEGREE.getInt(direction)).uvLock(true).addModel().condition(
                DIR_TO_PROP[direction], WallSide.LOW).end()
            builder.part().modelFile(tallWallFile).rotationY(DIR_TO_DEGREE.getInt(direction)).uvLock(true).addModel().condition(
                DIR_TO_PROP[direction], WallSide.TALL).end()
        }
    }

    @Suppress("ReplacePutWithAssignment")
    companion object {
        val DIR_TO_DEGREE = Object2IntOpenHashMap<Direction>()
        val DIR_TO_PROP = HashMap<Direction, EnumProperty<WallSide>>()
        val HORIZONTALS = arrayListOf(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)

        init {
            DIR_TO_DEGREE.put(Direction.NORTH,   0)
            DIR_TO_DEGREE.put(Direction.EAST,   90)
            DIR_TO_DEGREE.put(Direction.SOUTH, 180)
            DIR_TO_DEGREE.put(Direction.WEST,  270)

            DIR_TO_PROP.put(Direction.NORTH, BlockStateProperties.NORTH_WALL)
            DIR_TO_PROP.put(Direction.EAST,  BlockStateProperties.EAST_WALL )
            DIR_TO_PROP.put(Direction.SOUTH, BlockStateProperties.SOUTH_WALL)
            DIR_TO_PROP.put(Direction.WEST,  BlockStateProperties.WEST_WALL )
        }
    }
}