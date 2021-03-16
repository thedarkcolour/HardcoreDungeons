package thedarkcolour.hardcoredungeons.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentType
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import kotlin.math.roundToInt

// Sword enchantment that inflicts wither damage
class WitheringEnchantment : Enchantment(Rarity.RARE, EnchantmentType.WEAPON, arrayOf(EquipmentSlotType.MAINHAND)) {
    override fun getMaxLevel() = 3

    override fun onEntityDamaged(user: LivingEntity, target: Entity, level: Int) {
        if (target is LivingEntity && target.getActivePotionEffect(Effects.WITHER) == null) {
            target.addPotionEffect(EffectInstance(Effects.WITHER, (1.5 + level * 14).roundToInt(), 3))
        }
    }

    override fun getMinEnchantability(enchantmentLevel: Int): Int {
        return 10 + 20 * (enchantmentLevel - 1)
    }

    override fun getMaxEnchantability(enchantmentLevel: Int): Int {
        return getMinEnchantability(enchantmentLevel) + 55
    }
}