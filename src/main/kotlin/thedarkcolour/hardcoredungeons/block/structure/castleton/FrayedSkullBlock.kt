package thedarkcolour.hardcoredungeons.block.structure.castleton

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.Direction
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.BlockHitResult
import thedarkcolour.hardcoredungeons.block.decoration.HorizontalBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.items.HItemsNew

class FrayedSkullBlock(properties: HProperties) : HorizontalBlock(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(EYES, 0))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING, EYES)
    }

    override fun use(
        state: BlockState, worldIn: Level, pos: BlockPos,
        player: Player, hand: InteractionHand, hit: BlockHitResult
    ): InteractionResult {
        return if (player.mayBuild()) {
            if (!worldIn.isClientSide) {
                val stack = player.getItemInHand(hand)
                val eyes = state.getValue(EYES)

                if (stack.item == HItemsNew.CASTLE_GEM && eyes < 2) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(EYES, eyes + 1))
                    //worldIn.playSound()
                    stack.shrink(1)
                }
            }
            InteractionResult.SUCCESS
        } else {
            InteractionResult.PASS
        }

    }

    companion object {
        private val EYES = IntegerProperty.create("eyes", 0, 2)
    }
}