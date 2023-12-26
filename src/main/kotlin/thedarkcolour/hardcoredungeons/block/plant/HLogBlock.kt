package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.ToolAction
import net.minecraftforge.common.ToolActions

class HLogBlock(val stripped: () -> Block, properties: Properties) : RotatedPillarBlock(properties) {
    override fun getToolModifiedState(
        state: BlockState,
        context: UseOnContext,
        toolAction: ToolAction,
        simulate: Boolean
    ): BlockState? {
        if (toolAction == ToolActions.AXE_STRIP) {
            return stripped().defaultBlockState().setValue(AXIS, state.getValue(AXIS))
        } else {
            return null
        }
    }
}