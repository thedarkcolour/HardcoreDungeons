package thedarkcolour.hardcoredungeons.enchantment

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentCategory
import kotlin.math.roundToInt

// Sword enchantment that inflicts wither damage
class WitheringEnchantment : Enchantment(Rarity.RARE, EnchantmentCategory.WEAPON, arrayOf(EquipmentSlot.MAINHAND)) {
    override fun getMaxLevel() = 3

    override fun doPostAttack(user: LivingEntity, target: Entity, level: Int) {
        if (target is LivingEntity && target.getEffect(MobEffects.WITHER) == null) {
            target.addEffect(MobEffectInstance(MobEffects.WITHER, (1.5 + level * 14).roundToInt(), 3))
        }
    }

    override fun getMinCost(enchantmentLevel: Int): Int {
        return 10 + 20 * (enchantmentLevel - 1)
    }

    override fun getMaxCost(enchantmentLevel: Int): Int {
        return getMinCost(enchantmentLevel) + 55
    }
}