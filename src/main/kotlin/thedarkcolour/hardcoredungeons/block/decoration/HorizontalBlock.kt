package thedarkcolour.hardcoredungeons.block.decoration

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import thedarkcolour.hardcoredungeons.block.HBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties

/**
 * Base block class that has support for horizontal rotations.
 */
open class HorizontalBlock(properties: HProperties) : HBlock(properties) {
    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState {
        return defaultState.with(HORIZONTAL_FACING, context.placementHorizontalFacing.opposite)
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)))
    }

    override fun mirror(state: BlockState, mirrorIn: Mirror): BlockState {
        return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)))
    }
}