package thedarkcolour.hardcoredungeons.enchantment

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.PickaxeItem
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentCategory
import net.minecraft.world.item.enchantment.Enchantments

// todo add random gold nuggets
// Gives tools a chance to mine "pristine" minerals instead of regular ones
class ProspectingEnchantment : Enchantment(Rarity.VERY_RARE, PICKAXE, arrayOf(EquipmentSlot.MAINHAND)) {
    override fun checkCompatibility(ench: Enchantment): Boolean {
        return this != ench && this != Enchantments.SILK_TOUCH
    }

    // from silk touch
    override fun getMinCost(enchantmentLevel: Int) = 15
    override fun getMaxCost(enchantmentLevel: Int) = 65

    companion object {
        // use alternative name to avoid conflicting enum names
        // todo should this just be digger?
        val PICKAXE: EnchantmentCategory = EnchantmentCategory.create("HCD_PICKAXE") { item ->
            item is PickaxeItem //  || TODO figure out how to test if item is pickaxe
        }
    }
}