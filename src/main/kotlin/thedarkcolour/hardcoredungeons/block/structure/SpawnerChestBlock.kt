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
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.HBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties

// todo re evaluate cause this is just a copy paste
class SpawnerChestBlock(properties: HProperties) : HBlock(properties), IWaterLoggable {
    init {
        defaultState = defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(CHEST_TYPE, ChestType.SINGLE).with(WATERLOGGED, false)
    }

    override fun getRenderType(state: BlockState) = BlockRenderType.ENTITYBLOCK_ANIMATED

    override fun updatePostPlacement(
        stateIn: BlockState,
        facing: Direction,
        facingState: BlockState,
        worldIn: IWorld,
        currentPos: BlockPos,
        facingPos: BlockPos
    ): BlockState {
        if (facingState.block == this && facing.axis.isHorizontal) {
            val chestType = facingState.get(CHEST_TYPE)
            if (stateIn.get(CHEST_TYPE) == ChestType.SINGLE &&
                chestType != ChestType.SINGLE &&
                stateIn.get(HORIZONTAL_FACING) == facingState.get(HORIZONTAL_FACING) &&
                ChestBlock.getDirectionToAttached(facingState) == facing.opposite
            ) {
                return stateIn.with(CHEST_TYPE, chestType.opposite())
            }
        } else if (ChestBlock.getDirectionToAttached(stateIn) == facing) {
            return stateIn.with(CHEST_TYPE, ChestType.SINGLE)
        }

        return stateIn
    }

    override fun getShape(
        state: BlockState?,
        worldIn: IBlockReader?,
        pos: BlockPos?,
        ctx: ISelectionContext?
    ): VoxelShape {
        return if (state!!.get(ChestBlock.TYPE) == ChestType.SINGLE) {
            ChestBlock.SHAPE_SINGLE
        } else {
            when (ChestBlock.getDirectionToAttached(state)) {
                Direction.NORTH -> ChestBlock.SHAPE_NORTH
                Direction.SOUTH -> ChestBlock.SHAPE_SOUTH
                Direction.WEST -> ChestBlock.SHAPE_WEST
                Direction.EAST -> ChestBlock.SHAPE_EAST
                else -> ChestBlock.SHAPE_NORTH
            }
        }
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        var chestType = ChestType.SINGLE
        var direction = context.placementHorizontalFacing.opposite
        val state = context.world.getFluidState(context.pos)
        val flag = context.hasSecondaryUseForPlayer()
        val direction1 = context.face
        if (direction1.axis.isHorizontal && flag) {
            val direction2 = getDirectionToAttach(context, direction1.opposite)
            if (direction2 != null && direction2.axis !== direction1.axis) {
                direction = direction2
                chestType = if (direction2.rotateYCCW() == direction1.opposite) ChestType.RIGHT else ChestType.LEFT
            }
        }

        if (chestType == ChestType.SINGLE && !flag) {
            if (direction == getDirectionToAttach(context, direction.rotateY())) {
                chestType = ChestType.LEFT
            } else if (direction == getDirectionToAttach(context, direction.rotateYCCW())) {
                chestType = ChestType.RIGHT
            }
        }

        return defaultState.with(ChestBlock.FACING, direction).with(ChestBlock.TYPE, chestType).with(ChestBlock.WATERLOGGED, state.fluid == Fluids.WATER)
    }

    private fun getDirectionToAttach(context: BlockItemUseContext, direction: Direction): Direction? {
        val state = context.world.getBlockState(context.pos.offset(direction))

        return if (state.block == this && state.get(ChestBlock.TYPE) == ChestType.SINGLE) {
            state.get(ChestBlock.FACING)
        } else null
    }

    override fun getFluidState(state: BlockState): FluidState {
        return if (state.get(WATERLOGGED)) Fluids.WATER.getStillFluidState(false) else Fluids.EMPTY.defaultState
    }

    override fun onBlockActivated(
        state: BlockState,
        worldIn: World,
        pos: BlockPos,
        playerIn: PlayerEntity,
        handIn: Hand,
        hit: BlockRayTraceResult
    ): ActionResultType {
        return if (worldIn.isRemote()) {
            ActionResultType.SUCCESS
        } else {
            //HContainers.DUNGEON_LOOT.openContainer(playerIn)
            ActionResultType.SUCCESS
        }
    }

    override fun allowsMovement(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType): Boolean {
        return false
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING, CHEST_TYPE, WATERLOGGED)
    }

    companion object {
        val NAME = TranslationTextComponent("hardcoredungeons.container.dungeon_loot")
    }
}