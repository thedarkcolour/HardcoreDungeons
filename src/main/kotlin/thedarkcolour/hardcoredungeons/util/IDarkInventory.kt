package thedarkcolour.hardcoredungeons.util

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.items.IItemHandlerModifiable

// todo redo this system its so shit
interface IDarkInventory : IItemHandlerModifiable, INBTSerializable<CompoundNBT>, Iterable<ItemStack> {
    /**
     * Only use for tile entities
     */
    fun canInteractWith(playerIn: PlayerEntity) = false

    fun canTakeStack(slot: Int, playerIn: PlayerEntity): Boolean

    fun onTake(slot: Int, playerIn: PlayerEntity, stack: ItemStack): ItemStack

    fun onContentsChanged(slot: Int)

    /**
     * @return an instance of [Inventory] with the contents of this inventory.
     */
    fun getVanillaInventory(range: IntRange = stacks.indices): Inventory

    var stacks: Array<ItemStack>

    val size: Int

    fun anyMatch(test: (ItemStack) -> Boolean): Boolean {
        return firstOrNull(test) != null
    }

    operator fun get(index: Int): ItemStack {
        return getStackInSlot(index)
    }

    operator fun set(index: Int, stack: ItemStack) {
        setStackInSlot(index, stack)
    }

    fun setSize(size: Int)

    /**
     * Implement this in your [TileEntity]
     */
    interface Tile : IDarkInventory {
        /**
         * Return this tile entity's [DarkInventory]
         */
        val inventory: DarkInventory

        override fun canInteractWith(playerIn: PlayerEntity): Boolean

        /**
         * Use for tile entities and [canInteractWith]
         */
        fun isTileWithinDistance(te: TileEntity, playerIn: PlayerEntity): Boolean {
            return playerIn.position.withinDistance(te.pos, 8.0)
        }

        override fun iterator(): Iterator<ItemStack> {
            return inventory.iterator()
        }

        override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
            return inventory.insertItem(slot, stack, simulate)
        }

        override fun getStackInSlot(slot: Int): ItemStack {
            return inventory[slot]
        }

        override fun getSlotLimit(slot: Int): Int {
            return inventory.getSlotLimit(slot)
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            return inventory.isItemValid(slot, stack)
        }

        override fun setStackInSlot(slot: Int, stack: ItemStack) {
            inventory[slot] = stack
        }

        override fun getSlots(): Int {
            return inventory.slots
        }

        override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
            return inventory.extractItem(slot, amount, simulate)
        }

        // override that fixes conflicts with TileEntity
        // just return the methods within TileEntity if these do not overlap with your mappings
        // TODO Check if this works without mappings
        override fun deserializeNBT(nbt: CompoundNBT)
        override fun serializeNBT(): CompoundNBT

        override fun canTakeStack(slot: Int, playerIn: PlayerEntity): Boolean {
            return inventory.canTakeStack(slot, playerIn)
        }

        override fun onTake(slot: Int, playerIn: PlayerEntity, stack: ItemStack): ItemStack {
            return inventory.onTake(slot, playerIn, stack)
        }

        override fun onContentsChanged(slot: Int) {
            inventory.onContentsChanged(slot)
        }

        override fun getVanillaInventory(range: IntRange): Inventory {
            return inventory.getVanillaInventory(range)
        }

        override var stacks: Array<ItemStack>
            get() = inventory.stacks
            set(value) {
                inventory.stacks = value
            }
        override val size: Int
            get() = inventory.size

        override fun setSize(size: Int) {
            inventory.setSize(size)
        }
    }
}