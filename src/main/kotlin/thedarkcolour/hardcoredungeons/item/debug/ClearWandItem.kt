package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.block.Blocks
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType

/**
 * @author TheDarkColour
 */
class ClearWandItem(properties: Properties) : AbstractFillingWandItem(properties) {
    override val fillMessage = "item.hardcoredungeons.wand.clear"

    override fun useOn(context: ItemUseContext): ActionResultType {
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

        return ActionResultType.SUCCESS
    }
}