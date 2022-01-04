package thedarkcolour.hardcoredungeons.block.misc

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
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

class HGrassBlock(
    private val soil: () -> BlockState,
    private val nocturnal: Boolean,
    private val plantableTag: ITag<Block>,
    properties: Properties
) : Block(properties) {
    override fun tick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val soil = soil()

        if (!canSurvive(state, world, pos)) {
            if (world.isAreaLoaded(pos, 3)) {
                world.setBlockAndUpdate(pos, soil)
            }
        } else {
            if (nocturnal || world.getMaxLocalRawBrightness(pos.above()) >= 9) {
                for (i in 0..3) {
                    val randomPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1)

                    if (world.getBlockState(randomPos) == soil) {
                        if (canSpread(state, world, randomPos)) {
                            world.setBlockAndUpdate(randomPos, defaultBlockState())
                        }
                    }
                }
            }
        }
    }

    private fun canSurvive(state: BlockState, world: ServerWorld, pos: BlockPos): Boolean {
        val abovePos = pos.above()
        val aboveState = world.getBlockState(abovePos)

        return if (state.`is`(Blocks.SNOW) && state.getValue(SnowBlock.LAYERS) == 1) {
            true
        } else if (aboveState.fluidState.amount == 8) {
            false
        } else {
            val i = LightEngine.getLightBlockInto(world, state, pos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(world, abovePos))
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