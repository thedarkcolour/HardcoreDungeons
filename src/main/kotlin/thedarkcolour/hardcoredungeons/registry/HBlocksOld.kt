
package thedarkcolour.hardcoredungeons.registry

import com.google.common.collect.ImmutableSet
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.HoeItem
import net.minecraft.tileentity.TileEntityType

/**
 * Handles block registry and contains references to every `Block` added by HCD.
 *
 * @author TheDarkColour, genericrandom64
 */
@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType", "SpellCheckingInspection", "ReplacePutWithAssignment")
object HBlocksOld {
    /** todo move
     * Adds the [blocks] as valid blocks for this `TileEntityType`.
     *
     * @param blocks The valid blocks to be added
     */
    fun addValidBlocks(type: TileEntityType<*>, vararg blocks: Block) {
        type.validBlocks = ImmutableSet.builder<Block>().addAll(type.validBlocks).addAll(blocks.iterator()).build()
    }

    fun registerHoeInteraction(original: Block, new: Block) = registerHoeInteraction(original, new.defaultBlockState())

    fun registerHoeInteraction(original: Block, new: BlockState) {
        HoeItem.TILLABLES[original] = new
    }
}