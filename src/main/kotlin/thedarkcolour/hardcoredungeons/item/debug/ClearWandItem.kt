package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.block.Blocks
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType

/**
 * @author TheDarkColour
 */
class ClearWandItem(properties: Properties) : AbstractFillingWand(properties) {
    override val fillMessage = "item.hardcoredungeons.wand.clear"

    override fun onItemUse(context: ItemUseContext): ActionResultType {
        val worldIn = context.world

        if (!worldIn.isRemote) {
            val stack = context.item
            val pos = context.pos
            val playerIn = context.player

            if (!hasStartPos(stack)) {
                saveStartPosition(stack, pos, playerIn)
            } else {
                place(stack, Blocks.AIR.defaultState, pos, worldIn, playerIn)
            }
        }

        return ActionResultType.SUCCESS
    }
}