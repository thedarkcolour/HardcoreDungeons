
package thedarkcolour.hardcoredungeons.registry

import com.google.common.collect.ImmutableSet
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.item.HoeItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.common.ToolAction
import net.minecraftforge.common.ToolActions
import net.minecraftforge.event.level.BlockEvent
import thedarkcolour.hardcoredungeons.block.combo.WoodCombo

/**
 * Reflection stuff for blocks
 *
 * @author thedarkcolour
 */
object HBlockUtil {
    val STRIPPABLES = HashMap<Block, Block>()

    /**
     * Adds the [blocks] as valid blocks for this `TileEntityType`.
     *
     * @param blocks The valid blocks to be added
     */
    fun addValidBlocks(type: BlockEntityType<*>, vararg blocks: Block) {
        TODO()
        //type.validBlocks = ImmutableSet.builder<Block>().addAll(type.validBlocks).addAll(blocks.iterator()).build()
    }

    fun blockModificationEvent(event: BlockEvent.BlockToolModificationEvent) {
        if (event.toolAction == ToolActions.AXE_STRIP) {
            val state = event.state

            STRIPPABLES[state.block]?.let { stripped ->
                event.finalState = stripped.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS))
            }
        }
    }

    fun registerHoeInteraction(original: Block, new: BlockState) {
        //HoeItem.TILLABLES[original] = new
    }
}