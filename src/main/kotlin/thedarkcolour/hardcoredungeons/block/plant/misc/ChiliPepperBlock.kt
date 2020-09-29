package thedarkcolour.hardcoredungeons.block.plant.misc

import net.minecraft.block.*
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.IntegerProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.DoubleBlockHalf
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.PlantType
import java.util.*

class ChiliPepperBlock(properties: Properties) : DoublePlantBlock(properties), IGrowable {
    init {
        defaultState = defaultState.with(HALF, DoubleBlockHalf.LOWER)
    }

    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape {
        return if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            if (isTall(worldIn, pos, state)) {
                SHAPE_TOP
            } else {
                VoxelShapes.empty()
            }
        } else {
            SHAPE_BOTTOM
        }
    }

    override fun getOffsetType(): OffsetType {
        return OffsetType.NONE
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HALF, AGE)
    }

    override fun isValidGround(state: BlockState, worldIn: IBlockReader, pos: BlockPos): Boolean {
        return state.block == Blocks.FARMLAND
    }

    /**
     * Determines if this crop is below maximum age.
     */
    private fun isNotMaxAge(state: BlockState) = state.get(AGE) < 10

    /**
     * Determines whether the upper part of the plant has grown past 1 block height.
     *
     * @return if the plant is visually taller than 1 block.
     */
    private fun isTall(worldIn: IBlockReader, pos: BlockPos, state: BlockState): Boolean {
        return if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            worldIn.getBlockState(pos.up()).get(AGE) < 4
        } else {
            state.get(AGE) < 4
        }
    }

    override fun canGrow(worldIn: IBlockReader, pos: BlockPos, state: BlockState, isClient: Boolean) = isNotMaxAge(state)

    override fun canUseBonemeal(worldIn: World, rand: Random, pos: BlockPos, state: BlockState): Boolean {
        return true
    }

    override fun grow(worldIn: ServerWorld, rand: Random, pos: BlockPos, state: BlockState) {
        val growth = (state.get(AGE) + MathHelper.nextInt(rand, 3, 6)).coerceAtMost(10)

        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            worldIn.setBlockState(pos, defaultState.with(AGE, growth))
            worldIn.setBlockState(pos.up(), defaultState.with(HALF, DoubleBlockHalf.UPPER).with(AGE, growth))
        } else {
            worldIn.setBlockState(pos.down(), defaultState.with(AGE, growth))
            worldIn.setBlockState(pos, defaultState.with(HALF, DoubleBlockHalf.UPPER).with(AGE, growth))
        }
    }

    override fun randomTick(state: BlockState, worldIn: ServerWorld, pos: BlockPos, rand: Random) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            if (isNotMaxAge(state)) {
                val chance = getGrowthChance(state, worldIn, pos.toMutable())
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((25.0f / chance).toInt() + 1) == 0)) {
                    worldIn.setBlockState(pos, defaultState.with(HALF, DoubleBlockHalf.LOWER).with(AGE, state.get(AGE) + 1))
                    worldIn.setBlockState(pos.up(), defaultState.with(HALF, DoubleBlockHalf.UPPER).with(AGE, state.get(AGE) + 1))
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state)
                }
            }
        }
    }

    private fun getGrowthChance(blockState: BlockState, worldIn: ServerWorld, pos: BlockPos.Mutable): Float {
        var f = 1.0f

        val state = if (blockState.get(HALF) == DoubleBlockHalf.UPPER) {
            worldIn.getBlockState(pos.move(Direction.DOWN))
        } else {
            blockState
        }
        for (x in -1..1) {
            for (z in -1..1) {
                var i = 0.0f
                val blockPos = pos.add(x, -1, z)
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

    override fun getPlantType(world: IBlockReader?, pos: BlockPos?): PlantType {
        return PlantType.CROP
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        val pos = context.pos.up()

        return if (context.world.getBlockState(pos).isAir(context.world, pos)) {
            super.getStateForPlacement(context)
        } else {
            null
        }
    }

    companion object {
        private val SHAPE_BOTTOM = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)
        private val SHAPE_TOP = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0)
        val AGE = IntegerProperty.create("age", 0, 10)
        val HALF = BlockStateProperties.DOUBLE_BLOCK_HALF
    }
}