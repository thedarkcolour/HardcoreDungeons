package thedarkcolour.hardcoredungeons.data.modelgen

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.block.WallBlock
import net.minecraft.block.WallHeight
import net.minecraft.state.EnumProperty
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.util.modLoc

/**
 * Model type that produces the following:
 *
 *  - BlockState json file for wall
 *  - Low & Tall wall, pillar model json files
 *  - **Item model** and inventory model json files
 *
 * You do not need to add an item model yourself.
 *
 * @author TheDarkColour
 */
class WallModelType : BlockModelType<WallBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: WallBlock, gen: ModelGenerator) {
        // Path of the wall ("cobblestone_wall")
        val path = block.registryName!!.path

        // The actual block of this wall (cobblestone, granite)
        var base = path.removeSuffix("_wall")
        // Corrects "castleton_brick" to "castleton_bricks"
        if (base.endsWith("brick")) base += "s"

        val baseLoc = gen.blockLoc(base)

        // The wall post model
        gen.blockModel(base + "_post")
            .parent(gen.mcFile("block/template_wall_post"))
            .texture("wall", baseLoc)

        // Low & Tall wall models
        gen.blockModel(path + "_side")
            .parent(gen.mcFile("block/template_wall_side"))
            .texture("wall", baseLoc)
        gen.blockModel(path + "_side_tall")
            .parent(gen.mcFile("block/template_wall_side_tall"))
            .texture("wall", baseLoc)

        // item form of the wall
        gen.blockModel(path + "_inventory")
            .parent(gen.mcFile("block/wall_inventory"))
            .texture("wall", baseLoc)

        val postFile = ModelFile.UncheckedModelFile(modLoc("block/" + base + "_post"))
        val lowWallFile = ModelFile.UncheckedModelFile(modLoc("block/" + path + "_side"))
        val tallWallFile = ModelFile.UncheckedModelFile(modLoc("block/" + path + "_side_tall"))

        // pillar
        val builder = gen.getMultipartBuilder(block)
        builder.part().modelFile(postFile).addModel().condition(BlockStateProperties.UP, true).end()

        // wall sides for low and high
        for (direction in HORIZONTALS) {
            builder.part().modelFile(lowWallFile).rotationY(DIR_TO_DEGREE.getInt(direction)).uvLock(true).addModel().condition(DIR_TO_PROP[direction], WallHeight.LOW).end()
            builder.part().modelFile(tallWallFile).rotationY(DIR_TO_DEGREE.getInt(direction)).uvLock(true).addModel().condition(DIR_TO_PROP[direction], WallHeight.TALL).end()
        }

        // item model
        gen.itemModels().getBuilder(path)
            .parent(gen.modFile("block/" + path + "_inventory"))
    }

    @Suppress("ReplacePutWithAssignment")
    companion object {
        val DIR_TO_DEGREE = Object2IntOpenHashMap<Direction>()
        val DIR_TO_PROP = HashMap<Direction, EnumProperty<WallHeight>>()
        val HORIZONTALS = arrayListOf(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)

        init {
            DIR_TO_DEGREE.put(Direction.NORTH,   0)
            DIR_TO_DEGREE.put(Direction.EAST,   90)
            DIR_TO_DEGREE.put(Direction.SOUTH, 180)
            DIR_TO_DEGREE.put(Direction.WEST,  270)

            DIR_TO_PROP.put(Direction.NORTH, BlockStateProperties.WALL_HEIGHT_NORTH)
            DIR_TO_PROP.put(Direction.EAST,  BlockStateProperties.WALL_HEIGHT_EAST )
            DIR_TO_PROP.put(Direction.SOUTH, BlockStateProperties.WALL_HEIGHT_SOUTH)
            DIR_TO_PROP.put(Direction.WEST,  BlockStateProperties.WALL_HEIGHT_WEST )
        }
    }
}