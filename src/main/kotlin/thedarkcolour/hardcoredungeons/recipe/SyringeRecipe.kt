package thedarkcolour.hardcoredungeons.recipe

import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionBrewing
import net.minecraftforge.common.brewing.IBrewingRecipe
import thedarkcolour.hardcoredungeons.registry.HItems

object SyringeRecipe : IBrewingRecipe {
    override fun isInput(input: ItemStack): Boolean {
        return input.item == HItems.POTION_SYRINGE
    }

    override fun isIngredient(ingredient: ItemStack): Boolean {
        return PotionBrewing.isIngredient(ingredient)
    }

    override fun getOutput(input: ItemStack, ingredient: ItemStack): ItemStack {
        if (!input.isEmpty && !ingredient.isEmpty && isIngredient(ingredient)) {
            val result = PotionBrewing.mix(ingredient, input)
            return if (result != input) {
                result
            } else ItemStack.EMPTY
        }

        return ItemStack.EMPTY
    }
}