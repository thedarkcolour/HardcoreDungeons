package thedarkcolour.hardcoredungeons.container

import net.minecraft.world.item.ItemStack
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.SpawnEggItem
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.SlotItemHandler

class EditDungeonSpawnerContainer(type: MenuType<*>, id: Int, playerIn: Player) : HContainer(type, id, playerIn) {
    val inventory = ItemStackHandler(5)

    init {
        addOwnSlots()
        addPlayerSlots(playerIn.inventory)
    }

    private fun addOwnSlots() {
        addSlot(ArmorSlot(EquipmentSlot.HEAD, playerIn, inventory, 0, 7, 7))
        addSlot(ArmorSlot(EquipmentSlot.CHEST, playerIn, inventory, 1, 7, 25))
        addSlot(ArmorSlot(EquipmentSlot.LEGS, playerIn, inventory, 2, 7, 43))
        addSlot(ArmorSlot(EquipmentSlot.FEET, playerIn, inventory, 3, 7, 61))

        addSlot(object : SlotItemHandler(inventory, 4, 151, 7) {
            override fun mayPlace(stack: ItemStack): Boolean {
                return SpawnEggItem.BY_ID.containsValue(stack.item)
            }

            override fun getMaxStackSize() = 1

            // what is this for??
            override fun onQuickCraft(oldStackIn: ItemStack, newStackIn: ItemStack) {
                super.onQuickCraft(oldStackIn, newStackIn)
            }
        })
    }

    override fun stillValid(playerIn: Player): Boolean {
        return true // creative only screen so this shouldn't matter
    }

    class ArmorSlot(
        private val armorType: EquipmentSlot,
        private val playerIn: Player,
        inventory: IItemHandler,
        index: Int,
        x: Int,
        y: Int,
    ) : SlotItemHandler(inventory, index, x, y) {
        override fun getMaxStackSize() = 1

        override fun mayPlace(stack: ItemStack): Boolean {
            return stack.canEquip(armorType, playerIn)
        }
    }
}