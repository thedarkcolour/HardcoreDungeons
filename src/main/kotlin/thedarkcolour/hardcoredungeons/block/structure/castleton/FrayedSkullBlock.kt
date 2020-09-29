package thedarkcolour.hardcoredungeons.block.structure.castleton

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.IntegerProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.decoration.HorizontalBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HItems

class FrayedSkullBlock(properties: HProperties) : HorizontalBlock(properties) {
    init {
        defaultState = defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(EYES, 0)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING, EYES)
    }

    override fun onBlockActivated(
        state: BlockState, worldIn: World, pos: BlockPos,
        player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult
    ): ActionResultType {
        return if (player.isAllowEdit) {
            if (!worldIn.isRemote) {
                val stack = player.getHeldItem(hand)
                val eyes = state[EYES]

                if (stack.item == HItems.CASTLE_GEM && eyes < 2) {
                    worldIn.setBlockState(pos, state.with(EYES, eyes + 1))
                    //worldIn.playSound()
                    stack.shrink(1)
                }
            }
            ActionResultType.SUCCESS
        } else {
            ActionResultType.PASS
        }

    }

    companion object {
        private val EYES = IntegerProperty.create("eyes", 0, 2)
    }
}