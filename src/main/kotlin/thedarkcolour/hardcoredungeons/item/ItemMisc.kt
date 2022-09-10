package thedarkcolour.hardcoredungeons.item

import net.minecraft.world.food.FoodProperties

/**
 * @author thedarkcolour
 */
object ItemMisc {
    // foods
    val WILD_BERROOK: FoodProperties = FoodProperties.Builder().nutrition(2).saturationMod(0.4f).fast().build()
    val CANDY_CANE: FoodProperties = FoodProperties.Builder().nutrition(3).saturationMod(0.1f).build()
}