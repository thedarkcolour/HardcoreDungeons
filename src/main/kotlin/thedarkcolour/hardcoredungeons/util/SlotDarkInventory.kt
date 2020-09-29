package thedarkcolour.hardcoredungeons.util

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraftforge.items.SlotItemHandler

/**
 * @author TheDarkColour
 */
open class SlotDarkInventory(private val darkInventory: IDarkInventory, index: Int, xPosition: Int, yPosition: Int) : SlotItemHandler(darkInventory, index, xPosition, yPosition) {
    override fun canTakeStack(playerIn: PlayerEntity): Boolean {
        return darkInventory.canTakeStack(slotIndex, playerIn)
    }

    override fun onTake(playerIn: PlayerEntity, stack: ItemStack): ItemStack {
        return darkInventory.onTake(slotIndex, playerIn, stack)
    }
}