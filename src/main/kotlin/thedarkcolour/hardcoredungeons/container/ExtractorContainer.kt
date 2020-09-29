package thedarkcolour.hardcoredungeons.container

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import thedarkcolour.hardcoredungeons.registry.HContainers
import thedarkcolour.hardcoredungeons.util.DarkInventory
import thedarkcolour.hardcoredungeons.util.IDarkInventory
import thedarkcolour.hardcoredungeons.util.SlotDarkInventory


class ExtractorContainer constructor(
    id: Int,
    playerInv: PlayerInventory,
) : HContainer(HContainers.EXTRACTOR, id, playerInv.player), Extractor {
    private val inventory: IDarkInventory = DarkInventory(3)

    init {
        addOwnSlots()
        addPlayerSlots(playerInv)
    }

    private fun addOwnSlots() {
        addSlot(SlotDarkInventory(inventory, 0,79, 10))
        addSlot(SlotDarkInventory(inventory, 1,132, 30))
        addSlot(SlotDarkInventory(inventory, 2,79, 57))
    }

    override fun transferStackInSlot(playerIn: PlayerEntity, index: Int): ItemStack {
        var stack = ItemStack.EMPTY
        val slot = inventorySlots[index]

        if (slot.hasStack) {
            val stack1 = slot.stack
            stack = stack1.copy()
            if (index == 2) {
                if (!mergeItemStack(stack1, 3, 39, true)) {
                    return ItemStack.EMPTY
                }
                slot.onSlotChange(stack1, stack)
            } else if (index != 1 && index != 0) {
                if (isValidInput(playerIn.world, stack1)) {
                    if (!mergeItemStack(stack1, 0, 1, false)) {
                        return ItemStack.EMPTY
                    }
                } else if (isValidFuel(playerIn.world, stack1)) {
                    if (!mergeItemStack(stack1, 1, 2, false)) {
                        return ItemStack.EMPTY
                    }
                } else if (index in 3..29) {
                    if (!mergeItemStack(stack1, 30, 39, false)) {
                        return ItemStack.EMPTY
                    }
                } else if (index in 30..38 && !mergeItemStack(stack1, 3, 30, false)) {
                    return ItemStack.EMPTY
                }
            } else if (!mergeItemStack(stack1, 3, 39, false)) {
                return ItemStack.EMPTY
            }
            if (stack1.isEmpty) {
                slot.putStack(ItemStack.EMPTY)
            } else {
                slot.onSlotChanged()
            }
            if (stack1.count == stack.count) {
                return ItemStack.EMPTY
            }
            slot.onTake(playerIn, stack1)
        }

        return stack
    }

    override fun canInteractWith(playerIn: PlayerEntity): Boolean {
        return inventory.canInteractWith(playerIn)
    }
}
