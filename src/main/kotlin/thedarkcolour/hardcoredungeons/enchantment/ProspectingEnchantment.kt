package thedarkcolour.hardcoredungeons.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentType
import net.minecraft.enchantment.Enchantments
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ItemStack
import net.minecraft.item.PickaxeItem
import net.minecraftforge.common.ToolType

// Gives tools a chance to mine "pristine" minerals instead of regular ones
class ProspectingEnchantment : Enchantment(Rarity.VERY_RARE, PICKAXE, arrayOf(EquipmentSlotType.MAINHAND)) {
    override fun checkCompatibility(ench: Enchantment): Boolean {
        return this != ench && this != Enchantments.SILK_TOUCH
    }

    // from silk touch
    override fun getMinCost(enchantmentLevel: Int) = 15
    override fun getMaxCost(enchantmentLevel: Int) = 65

    companion object {
        // use alternative name to avoid conflicting enum names
        // todo should this just be digger?
        val PICKAXE: EnchantmentType = EnchantmentType.create("HCD_PICKAXE") { item ->
            item is PickaxeItem || item.getToolTypes(ItemStack(item)).contains(ToolType.PICKAXE)
        }
    }
}