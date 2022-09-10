package thedarkcolour.hardcoredungeons.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.FluidTags
import net.minecraft.tags.TagKey
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SnowLayerBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
import net.minecraft.world.level.lighting.LayerLightEngine
import net.minecraftforge.common.IPlantable
import java.util.*
import java.util.function.BiConsumer

class HGrassBlock(
    private val soil: () -> BlockState,
    private val nocturnal: Boolean,
    private val plantableTag: TagKey<Block>,
    properties: Properties
) : Block(properties) {
    override fun tick(state: BlockState, world: ServerLevel, pos: BlockPos, random: RandomSource) {
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

    private fun canSurvive(state: BlockState, world: ServerLevel, pos: BlockPos): Boolean {
        val abovePos = pos.above()
        val aboveState = world.getBlockState(abovePos)

        return if (state.`is`(Blocks.SNOW) && state.getValue(SnowLayerBlock.LAYERS) == 1) {
            true
        } else if (aboveState.fluidState.amount == 8) {
            false
        } else {
            val i = LayerLightEngine.getLightBlockInto(world, state, pos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(world, abovePos))
            i < world.maxLightLevel
        }
    }

    private fun canSpread(state: BlockState, world: ServerLevel, abovePos: BlockPos): Boolean {
        return canSurvive(state, world, abovePos) && !world.getFluidState(abovePos).`is`(FluidTags.WATER)
    }

    override fun onTreeGrow(
        state: BlockState,
        level: LevelReader,
        placeFunction: BiConsumer<BlockPos, BlockState>,
        randomSource: RandomSource,
        pos: BlockPos,
        config: TreeConfiguration?
    ): Boolean {
        placeFunction.accept(pos, soil())
        return true
    }

    override fun canSustainPlant(
        state: BlockState,
        world: BlockGetter,
        pos: BlockPos,
        facing: Direction,
        plantable: IPlantable
    ): Boolean {
        return plantable.getPlant(world, pos.relative(facing)).`is`(plantableTag)
    }
}