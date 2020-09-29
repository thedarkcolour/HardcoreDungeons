package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.item.ItemUseContext
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.ActionResultType
import net.minecraft.util.text.StringTextComponent

/**
 * @author TheDarkColour
 */
class FillWandItem(properties: Properties) : AbstractFillingWand(properties) {
    override val fillMessage = "item.hardcoredungeons.wand.fill"

    override fun onItemUse(context: ItemUseContext): ActionResultType {
        val worldIn = context.world

        if (!worldIn.isRemote) {
            val stack = context.item
            val pos = context.pos
            val playerIn = context.player

            if (playerIn?.isSneaking == true) {
                val state = worldIn.getBlockState(pos)
                stack.setTagInfo("FillBlock", NBTUtil.writeBlockState(state))
                playerIn.sendStatusMessage(StringTextComponent("Set block to $state"), true)
            } else {
                if (!hasStartPos(stack)) {
                    saveStartPosition(stack, pos, playerIn)
                } else {
                    val state = try {
                        NBTUtil.readBlockState(stack.getChildTag("FillBlock")!!)
                    } catch (e: NullPointerException) {
                        playerIn?.sendStatusMessage(StringTextComponent("No filler block"), true)
                        return ActionResultType.SUCCESS
                    }
                    place(stack, state, pos, worldIn, playerIn)
                }
            }
        }

        return ActionResultType.SUCCESS
    }
}