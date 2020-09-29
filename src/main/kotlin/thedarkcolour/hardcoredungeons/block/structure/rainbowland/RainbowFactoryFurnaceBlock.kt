package thedarkcolour.hardcoredungeons.block.structure.rainbowland

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.BooleanProperty
import net.minecraft.state.DirectionProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction
import net.minecraft.util.Hand
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader

class RainbowFactoryFurnaceBlock(properties: Properties) : Block(properties) {
    init {
        defaultState = defaultState.with(FACING, Direction.NORTH).with(LIT, false)
    }

    override fun getLightValue(state: BlockState, world: IBlockReader, pos: BlockPos): Int {
        return if (state.get(LIT)) 15 else 0
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(FACING, LIT)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState {
        return defaultState.with(FACING, context.placementHorizontalFacing.opposite)
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return state.with(FACING, rot.rotate(state.get(FACING)))
    }

    companion object {
        val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
        val LIT: BooleanProperty = BlockStateProperties.LIT
    }
}