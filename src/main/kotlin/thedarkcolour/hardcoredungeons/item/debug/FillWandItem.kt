package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.item.ItemUseContext
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.ActionResultType
import net.minecraft.util.text.StringTextComponent

/**
 * @author TheDarkColour
 */
class FillWandItem(properties: Properties) : AbstractFillingWandItem(properties) {
    override val fillMessage = "item.hardcoredungeons.wand.fill"

    override fun useOn(context: ItemUseContext): ActionResultType {
        val worldIn = context.level

        if (!worldIn.isClientSide) {
            val stack = context.itemInHand
            val pos = context.clickedPos
            val playerIn = context.player

            if (playerIn?.isShiftKeyDown == true) {
                val state = worldIn.getBlockState(pos)
                stack.addTagElement("FillBlock", NBTUtil.writeBlockState(state))
                playerIn?.displayClientMessage(StringTextComponent("Set block to $state"), true)
            } else {
                if (!hasStartPos(stack)) {
                    saveStartPosition(stack, pos, playerIn)
                } else {
                    val state = try {
                        NBTUtil.readBlockState(stack.getTagElement("FillBlock")!!)
                    } catch (e: NullPointerException) {
                        playerIn?.displayClientMessage(StringTextComponent("No filler block"), true)
                        return ActionResultType.SUCCESS
                    }
                    place(stack, state, pos, worldIn, playerIn)
                }
            }
        }

        return ActionResultType.SUCCESS
    }
}