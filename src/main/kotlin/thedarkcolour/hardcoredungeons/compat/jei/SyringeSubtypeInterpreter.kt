package thedarkcolour.hardcoredungeons.compat.jei

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionUtils

// From JEI's PotionSubtypeInterpreter class
object SyringeSubtypeInterpreter : ISubtypeInterpreter {
    override fun apply(stack: ItemStack): String {
        return if (!stack.hasTag()) {
            ""
        } else {
            val potionType = PotionUtils.getPotionFromItem(stack)
            val potionTypeString = potionType.getNamePrefixed("")
            val stringBuilder = StringBuilder(potionTypeString)
            val effects = PotionUtils.getEffectsFromStack(stack)

            for (effect in effects) {
                stringBuilder.append(";").append(effect)
            }

            stringBuilder.toString()
        }
    }
}