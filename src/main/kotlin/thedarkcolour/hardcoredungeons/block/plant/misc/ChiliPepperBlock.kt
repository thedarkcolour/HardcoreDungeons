package thedarkcolour.hardcoredungeons.block.plant.misc

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.DoublePlantBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.PlantType
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class ChiliPepperBlock(properties: HProperties) : DoublePlantBlock(properties.build()), BonemealableBlock {
    init {
        registerDefaultState(stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER))
    }

    override fun getShape(state: BlockState, worldIn: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            if (isTall(state)) {
                SHAPE_TOP
            } else {
                Shapes.empty()
            }
        } else {
            SHAPE_BOTTOM
        }
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(HALF, AGE)
    }

    override fun mayPlaceOn(state: BlockState, worldIn: BlockGetter, pos: BlockPos): Boolean {
        return state.block == Blocks.FARMLAND
    }

    /**
     * Determines if this crop is below maximum age.
     */
    private fun isNotMaxAge(state: BlockState) = state.getValue(AGE) < 10

    /**
     * Determines whether the upper part of the plant has grown past 1 block height.
     *
     * @return if the plant is visually taller than 1 block.
     */
    private fun isTall(state: BlockState): Boolean {
        return state.getValue(AGE) > 4
    }

    override fun isValidBonemealTarget(worldIn: BlockGetter, pos: BlockPos, state: BlockState, isClient: Boolean) = isNotMaxAge(state)

    override fun isBonemealSuccess(worldIn: Level, rand: RandomSource, pos: BlockPos, state: BlockState): Boolean {
        return true
    }

    override fun performBonemeal(worldIn: ServerLevel, rand: RandomSource, pos: BlockPos, state: BlockState) {
        val growth = (state.getValue(AGE) + Mth.nextInt(rand, 3, 6)).coerceAtMost(10)

        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            worldIn.setBlockAndUpdate(pos, defaultBlockState().setValue(AGE, growth))
            worldIn.setBlockAndUpdate(pos.above(), defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, growth))
        } else {
            worldIn.setBlockAndUpdate(pos.below(), defaultBlockState().setValue(AGE, growth))
            worldIn.setBlockAndUpdate(pos, defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, growth))
        }
    }

    override fun randomTick(state: BlockState, worldIn: ServerLevel, pos: BlockPos, rand: RandomSource) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if (isNotMaxAge(state)) {
                val chance = getGrowthChance(state, worldIn, pos.mutable())
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((25.0f / chance).toInt() + 1) == 0)) {
                    worldIn.setBlockAndUpdate(pos, defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, state.getValue(AGE) + 1))
                    worldIn.setBlockAndUpdate(pos.above(), defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, state.getValue(AGE) + 1))
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state)
                }
            }
        }
    }

    private fun getGrowthChance(blockState: BlockState, worldIn: ServerLevel, pos: BlockPos.MutableBlockPos): Float {
        var f = 1.0f

        val state = if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            worldIn.getBlockState(pos.move(Direction.DOWN))
        } else {
            blockState
        }
        for (x in -1..1) {
            for (z in -1..1) {
                var i = 0.0f
                val blockPos = pos.offset(x, -1, z)
                val state1 = worldIn.getBlockState(blockPos)

                if (state1.canSustainPlant(worldIn, blockPos, Direction.UP, state.block as IPlantable)) {
                    i = if (state1.isFertile(worldIn, blockPos)) {
                        3.0f
                    } else {
                        1.0f
                    }
                }

                if (x != 0 || z != 0) {
                    i /= 4
                }

                f += i
            }
        }
        val north = pos.north()
        val south = pos.south()
        val east = pos.east()
        val west = pos.west()
        val block = state.block

        val flag = block == worldIn.getBlockState(north) || block == worldIn.getBlockState(south)
        val flag1 = block == worldIn.getBlockState(east) || block == worldIn.getBlockState(west)
        if (flag && flag1) {
            f /= 2
        } else {
            if (block == worldIn.getBlockState(east.north()) || block == worldIn.getBlockState(west.north()) ||
                    block == worldIn.getBlockState(east.south()) || block == worldIn.getBlockState(west.south())) {
                f /= 2
            }
        }

        return f
    }

    override fun getPlantType(world: BlockGetter?, pos: BlockPos?): PlantType {
        return PlantType.CROP
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val pos = context.clickedPos.above()

        return if (context.level.getBlockState(pos).isAir) {
            super.getStateForPlacement(context)
        } else {
            null
        }
    }

    companion object {
        private val SHAPE_BOTTOM = box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)
        private val SHAPE_TOP = box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0)
        val AGE = IntegerProperty.create("age", 0, 10)
        val HALF = BlockStateProperties.DOUBLE_BLOCK_HALF
    }
}