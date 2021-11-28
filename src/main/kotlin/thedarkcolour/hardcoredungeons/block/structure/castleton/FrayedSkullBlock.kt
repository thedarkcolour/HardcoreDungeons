package thedarkcolour.hardcoredungeons.block.structure.castleton

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.IntegerProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.Hand
import net.minecraft.core.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.block.decoration.HorizontalBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HItems

class FrayedSkullBlock(properties: HProperties) : HorizontalBlock(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(EYES, 0))
    }

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING, EYES)
    }

    override fun use(
        state: BlockState, worldIn: World, pos: BlockPos,
        player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult
    ): ActionResultType {
        return if (player.mayBuild()) {
            if (!worldIn.isClientSide) {
                val stack = player.getItemInHand(hand)
                val eyes = state.getValue(EYES)

                if (stack.item == HItems.CASTLE_GEM && eyes < 2) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(EYES, eyes + 1))
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