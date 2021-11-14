package thedarkcolour.hardcoredungeons.block.misc

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.Blocks
import net.minecraft.block.SnowBlock
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ITag
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.lighting.LightEngine
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.Tags
import java.util.*

class GrassBlock(
    private val soil: () -> BlockState,
    private val nocturnal: Boolean,
    private val plantableTag: ITag<Block>,
    properties: Properties
) : Block(properties) {
    override fun tick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val abovePos = pos.above()

        if (!canSurvive(world.getBlockState(abovePos), world, abovePos)) {
            if (world.isAreaLoaded(pos, 3)) {
                world.setBlockAndUpdate(pos, soil())
            }
        } else {
            if (world.getLightEmission(abovePos) >= 9 || (nocturnal && world.canSeeSkyFromBelowWater(pos))) {
                for (i in 0..3) {
                    val randomPos = abovePos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1)
                    if (world.getBlockState(randomPos) == soil() && canSpread(state, world, abovePos)) {
                        world.setBlockAndUpdate(randomPos, defaultBlockState())
                    }
                }
            }
        }
    }

    private fun canSurvive(state: BlockState, world: ServerWorld, abovePos: BlockPos): Boolean {
        return if (state.block == Blocks.SNOW && state.getValue(SnowBlock.LAYERS) == 1) {
            true
        } else {
            val i = LightEngine.getLightBlockInto(world, state, abovePos, state, abovePos, Direction.UP, state.getLightBlock(world, abovePos))
            i < world.maxLightLevel
        }
    }

    private fun canSpread(state: BlockState, world: ServerWorld, abovePos: BlockPos): Boolean {
        return canSurvive(state, world, abovePos) && !world.getFluidState(abovePos).`is`(FluidTags.WATER)
    }

    // replace with correct soil when a tree grows on this grass
    override fun onPlantGrow(state: BlockState, world: IWorld, pos: BlockPos, source: BlockPos) {
        if (state.`is`(Tags.Blocks.DIRT)) {
            world.setBlock(pos, soil(), 2)
        }
    }

    override fun canSustainPlant(
        state: BlockState,
        world: IBlockReader,
        pos: BlockPos,
        facing: Direction,
        plantable: IPlantable
    ): Boolean {
        return plantable.getPlant(world, pos.relative(facing)).`is`(plantableTag)
    }
}