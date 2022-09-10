package thedarkcolour.hardcoredungeons.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurioItem

class PendantItem(properties: Properties) : Item(properties), ICurioItem {
    override fun canEquipFromUse(slotContext: SlotContext?, stack: ItemStack?): Boolean {
        return true
    }
}