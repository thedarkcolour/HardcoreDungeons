package thedarkcolour.hardcoredungeons.block.decoration

import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.PropertiesView

/**
 * Rotatable block in all six directions.
 * Look at [HorizontalBlock] if you only want horizontal rotations.
 */
open class RotatableBlock(properties: PropertiesView) : HBlock(properties) {
    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)))
    }

    override fun mirror(state: BlockState, mirrorIn: Mirror): BlockState {
        return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)))
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        return defaultBlockState().setValue(FACING, context.nearestLookingDirection.opposite)
    }
}