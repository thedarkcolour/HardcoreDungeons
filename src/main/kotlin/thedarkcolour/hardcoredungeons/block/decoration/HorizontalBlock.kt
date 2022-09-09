package thedarkcolour.hardcoredungeons.block.decoration

import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

/**
 * Base block class that has support for horizontal rotations.
 */
open class HorizontalBlock(properties: HProperties) : HBlock(properties) {
    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING)
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return state.setValue(HORIZONTAL_FACING, rot.rotate(state.getValue(HORIZONTAL_FACING)))
    }

    override fun mirror(state: BlockState, mirrorIn: Mirror): BlockState {
        // can't do anything here because we don't have position or level
        return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)))
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return defaultBlockState().setValue(HORIZONTAL_FACING, context.horizontalDirection.opposite)
    }
}