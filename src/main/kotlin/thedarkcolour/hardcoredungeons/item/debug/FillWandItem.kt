package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.nbt.NbtUtils
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext

/**
 * @author thedarkcolour
 */
class FillWandItem(properties: Properties) : AbstractFillingWandItem(properties) {
    override val fillMessage = "item.hardcoredungeons.wand.fill"

    override fun useOn(context: UseOnContext): InteractionResult {
        val worldIn = context.level

        if (!worldIn.isClientSide) {
            val stack = context.itemInHand
            val pos = context.clickedPos
            val playerIn = context.player

            if (playerIn?.isShiftKeyDown == true) {
                val state = worldIn.getBlockState(pos)
                stack.addTagElement("FillBlock", NbtUtils.writeBlockState(state))
                playerIn.displayClientMessage(Component.literal("Set block to $state"), true)
            } else {
                if (!hasStartPos(stack)) {
                    saveStartPosition(stack, pos, playerIn)
                } else {
                    val state = try {
                        NbtUtils.readBlockState(stack.getTagElement("FillBlock")!!)
                    } catch (e: NullPointerException) {
                        playerIn?.displayClientMessage(Component.literal("No filler block"), true)
                        return InteractionResult.SUCCESS
                    }
                    place(stack, state, pos, worldIn, playerIn)
                }
            }
        }

        return InteractionResult.SUCCESS
    }
}