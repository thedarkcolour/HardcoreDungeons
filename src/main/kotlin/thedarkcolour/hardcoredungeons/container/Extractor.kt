package thedarkcolour.hardcoredungeons.container

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.registry.HRecipes

/**
 * Shared code for the extractor
 */
interface Extractor {
    fun isValidInput(world: World, stack: ItemStack): Boolean {
        return world.recipeManager.getRecipe(HRecipes.EXTRACTION, Inventory(stack), world).isPresent
    }

    fun isValidFuel(world: World, stack: ItemStack): Boolean {
        return stack.item == HItems.LUM
    }
}