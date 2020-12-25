package thedarkcolour.hardcoredungeons.block.decoration

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.DirectionalBlock.FACING
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import thedarkcolour.hardcoredungeons.block.HBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties

/**
 * Rotatable block in every direction.
 *
 * @author TheDarkColour
 */
class RotatableBlock(properties: HProperties) : HBlock(properties) {
    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return state.with(FACING, rot.rotate(state.get(FACING)))
    }

    override fun mirror(state: BlockState, mirrorIn: Mirror): BlockState {
        return state.with(FACING, mirrorIn.mirror(state.get(FACING)))
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState {
        return defaultState.with(FACING, context.nearestLookingDirection.opposite)
    }
}