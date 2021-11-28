package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.BlockItemUseContext
import net.minecraft.pathfinding.PathType
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties.*
import net.minecraft.state.properties.ChestType
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.Hand
import net.minecraft.core.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

// todo re evaluate cause this is just a copy paste
class SpawnerChestBlock(properties: HProperties) : HBlock(properties), IWaterLoggable {
    init {
        registerDefaultState(stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(CHEST_TYPE, ChestType.SINGLE).setValue(WATERLOGGED, false))
    }

    override fun getRenderShape(state: BlockState) = BlockRenderType.ENTITYBLOCK_ANIMATED

    override fun updateShape(
        stateIn: BlockState,
        facing: Direction,
        facingState: BlockState,
        worldIn: IWorld,
        currentPos: BlockPos,
        facingPos: BlockPos
    ): BlockState {
        if (facingState.block == this && facing.axis.isHorizontal) {
            val chestType = facingState.getValue(CHEST_TYPE)
            if (stateIn.getValue(CHEST_TYPE) == ChestType.SINGLE &&
                chestType != ChestType.SINGLE &&
                stateIn.getValue(HORIZONTAL_FACING) == facingState.getValue(HORIZONTAL_FACING) &&
                ChestBlock.getConnectedDirection(facingState) == facing.opposite
            ) {
                return stateIn.setValue(CHEST_TYPE, chestType.opposite)
            }
        } else if (ChestBlock.getConnectedDirection(stateIn) == facing) {
            return stateIn.setValue(CHEST_TYPE, ChestType.SINGLE)
        }

        return stateIn
    }

    override fun getShape(
        state: BlockState?,
        worldIn: IBlockReader?,
        pos: BlockPos?,
        ctx: ISelectionContext?
    ): VoxelShape {
        return if (state!!.getValue(ChestBlock.TYPE) == ChestType.SINGLE) {
            ChestBlock.AABB
        } else {
            when (ChestBlock.getConnectedDirection(state)) {
                Direction.NORTH -> ChestBlock.NORTH_AABB
                Direction.SOUTH -> ChestBlock.SOUTH_AABB
                Direction.WEST -> ChestBlock.WEST_AABB
                Direction.EAST -> ChestBlock.EAST_AABB
                else -> ChestBlock.NORTH_AABB
            }
        }
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        var chestType = ChestType.SINGLE
        var direction = context.horizontalDirection.opposite
        val state = context.level.getFluidState(context.clickedPos)
        val flag = context.isSecondaryUseActive
        val direction1 = context.clickedFace
        if (direction1.axis.isHorizontal && flag) {
            val direction2 = getDirectionToAttach(context, direction1.opposite)
            if (direction2 != null && direction2.axis != direction1.axis) {
                direction = direction2
                chestType = if (direction2.counterClockWise == direction1.opposite) ChestType.RIGHT else ChestType.LEFT
            }
        }

        if (chestType == ChestType.SINGLE && !flag) {
            if (direction == getDirectionToAttach(context, direction.clockWise)) {
                chestType = ChestType.LEFT
            } else if (direction == getDirectionToAttach(context, direction.counterClockWise)) {
                chestType = ChestType.RIGHT
            }
        }

        return defaultBlockState().setValue(ChestBlock.FACING, direction).setValue(ChestBlock.TYPE, chestType).setValue(ChestBlock.WATERLOGGED, state.type == Fluids.WATER)
    }

    private fun getDirectionToAttach(context: BlockItemUseContext, direction: Direction): Direction? {
        val state = context.level.getBlockState(context.clickedPos.relative(direction))

        return if (state.block == this && state.getValue(ChestBlock.TYPE) == ChestType.SINGLE) {
            state.getValue(ChestBlock.FACING)
        } else null
    }

    override fun getFluidState(state: BlockState): FluidState {
        return if (state.getValue(WATERLOGGED)) Fluids.WATER.getSource(false) else Fluids.EMPTY.defaultFluidState()
    }

    override fun use(
        state: BlockState,
        worldIn: World,
        pos: BlockPos,
        playerIn: PlayerEntity,
        handIn: Hand,
        hit: BlockRayTraceResult
    ): ActionResultType {
        return if (worldIn.isClientSide()) {
            ActionResultType.SUCCESS
        } else {
            //HContainers.DUNGEON_LOOT.openContainer(playerIn)
            ActionResultType.SUCCESS
        }
    }

    override fun isPathfindable(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType): Boolean {
        return false
    }

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING, CHEST_TYPE, WATERLOGGED)
    }

    companion object {
        val NAME = TranslationTextComponent("hardcoredungeons.container.dungeon_loot")
    }
}