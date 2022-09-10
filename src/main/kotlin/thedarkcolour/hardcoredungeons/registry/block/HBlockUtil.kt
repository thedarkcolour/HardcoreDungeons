
package thedarkcolour.hardcoredungeons.registry.block

import com.google.common.collect.ImmutableSet
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.common.ToolActions
import net.minecraftforge.event.level.BlockEvent
import net.minecraftforge.fml.util.ObfuscationReflectionHelper

/**
 * Reflection stuff for blocks
 *
 * @author thedarkcolour
 */
object HBlockUtil {
    private val validBlocksField = ObfuscationReflectionHelper.findField(BlockEntityType::class.java, "f_58915_")
    private val strippables = HashMap<Block, Block>()
    private val tillables = HashMap<Block, BlockState>()

    /**
     * Adds the [blocks] as valid blocks for this `TileEntityType`.
     *
     * @param blocks The valid blocks to be added
     */
    fun addValidBlocks(type: BlockEntityType<*>, vararg blocks: Block) {
        validBlocksField.set(type, ImmutableSet.builder<Block>().addAll(validBlocksField.get(type) as Set<Block>).addAll(blocks.iterator()).build())
    }

    fun blockModificationEvent(event: BlockEvent.BlockToolModificationEvent) {
        if (event.toolAction == ToolActions.AXE_STRIP) {
            val state = event.state

            strippables[state.block]?.let { stripped ->
                event.finalState = stripped.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS))
            }
        } else if (event.toolAction == ToolActions.HOE_TILL) {
            val state = event.state

            tillables[state.block]?.let { tilled ->
                event.finalState = tilled
            }
        }
    }

    fun registerHoeInteraction(original: Block, new: BlockState) {
        tillables[original] = new
    }

    fun registerAxeStripInteraction(original: Block, new: Block) {
        strippables[original] = new
    }
}