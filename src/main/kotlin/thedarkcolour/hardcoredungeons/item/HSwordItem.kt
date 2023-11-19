package thedarkcolour.hardcoredungeons.item

import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker

/**
 * Sword without a tier
 * @author TheDarkColour
 */
class HSwordItem(
    damage: Float,
    swingSpeed: Float,
    enchantability: Int,
    durability: Int,
    properties: Properties = ItemMaker.props().stacksTo(1),
) : SwordItem(SwordTier(damage, enchantability, durability), 0, swingSpeed, properties) {
    @Suppress("OVERRIDE_DEPRECATION", "HasPlatformType")
    private class SwordTier(private val damage: Float, private val enchantability: Int, private val durability: Int) : Tier {
        override fun getUses() = durability
        override fun getSpeed() = 0.0f
        override fun getAttackDamageBonus() = damage
        override fun getLevel() = 0
        override fun getEnchantmentValue() = enchantability
        override fun getRepairIngredient() = Ingredient.EMPTY
    }
}