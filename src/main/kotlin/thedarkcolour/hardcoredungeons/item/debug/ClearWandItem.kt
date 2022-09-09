package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks

/**
 * @author thedarkcolour
 */
class ClearWandItem(properties: Properties) : AbstractFillingWandItem(properties) {
    override val fillMessage = "item.hardcoredungeons.wand.clear"

    override fun useOn(context: UseOnContext): InteractionResult {
        val worldIn = context.level

        if (!worldIn.isClientSide) {
            val stack = context.itemInHand
            val pos = context.clickedPos
            val playerIn = context.player

            if (!hasStartPos(stack)) {
                saveStartPosition(stack, pos, playerIn)
            } else {
                place(stack, Blocks.AIR.defaultBlockState(), pos, worldIn, playerIn)
            }
        }

        return InteractionResult.SUCCESS
    }
}