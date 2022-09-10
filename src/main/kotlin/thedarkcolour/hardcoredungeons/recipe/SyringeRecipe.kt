package thedarkcolour.hardcoredungeons.recipe

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.PotionBrewing
import net.minecraftforge.common.brewing.IBrewingRecipe
import thedarkcolour.hardcoredungeons.registry.items.POTION_SYRINGE_ITEM

object SyringeRecipe : IBrewingRecipe {
    override fun isInput(input: ItemStack): Boolean {
        return input.item == POTION_SYRINGE_ITEM
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