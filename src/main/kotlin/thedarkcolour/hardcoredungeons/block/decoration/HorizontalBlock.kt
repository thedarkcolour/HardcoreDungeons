package thedarkcolour.hardcoredungeons.block.decoration

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

/**
 * Base block class that has support for horizontal rotations.
 */
open class HorizontalBlock(properties: HProperties) : HBlock(properties) {
    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING)
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return state.setValue(HORIZONTAL_FACING, rot.rotate(state.getValue(HORIZONTAL_FACING)))
    }

    override fun mirror(state: BlockState, mirrorIn: Mirror): BlockState {
        // can't do anything here because we don't have position or level
        return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)))
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState {
        return defaultBlockState().setValue(HORIZONTAL_FACING, context.horizontalDirection.opposite)
    }
}