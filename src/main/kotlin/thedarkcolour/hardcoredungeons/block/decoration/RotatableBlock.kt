package thedarkcolour.hardcoredungeons.block.decoration

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.DirectionalBlock.FACING
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

/**
 * Rotatable block in all six directions.
 * Look at [HorizontalBlock] if you only want horizontal rotations.
 */
open class RotatableBlock(properties: HProperties) : HBlock(properties) {
    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)))
    }

    override fun mirror(state: BlockState, mirrorIn: Mirror): BlockState {
        return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)))
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState {
        return defaultBlockState().setValue(FACING, context.nearestLookingDirection.opposite)
    }
}