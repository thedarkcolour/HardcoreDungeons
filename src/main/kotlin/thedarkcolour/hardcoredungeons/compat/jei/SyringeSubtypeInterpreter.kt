package thedarkcolour.hardcoredungeons.compat.jei

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter
import mezz.jei.api.ingredients.subtypes.UidContext
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionUtils

// From JEI's PotionSubtypeInterpreter class
object SyringeSubtypeInterpreter : IIngredientSubtypeInterpreter<ItemStack> {

    override fun apply(stack: ItemStack, context: UidContext?): String {
        return if (!stack.hasTag()) {
            ""
        } else {
            val potionType = PotionUtils.getPotion(stack)
            val potionTypeString = potionType.getName("")
            val stringBuilder = StringBuilder(potionTypeString)
            val effects = PotionUtils.getMobEffects(stack)

            for (effect in effects) {
                stringBuilder.append(";").append(effect)
            }

            stringBuilder.toString()
        }
    }
}