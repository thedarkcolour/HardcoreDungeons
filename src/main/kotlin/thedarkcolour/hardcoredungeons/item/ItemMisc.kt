package thedarkcolour.hardcoredungeons.item

import net.minecraft.item.Food
import net.minecraft.item.Rarity
import net.minecraft.util.text.TextFormatting

/**
 * @author TheDarkColour
 */
object ItemMisc {

    // foods
    val WILD_BERROOK: Food = Food.Builder().hunger(2).saturation(0.4f).fastToEat().build()
    val CANDY_CANE: Food = Food.Builder().hunger(3).saturation(0.1f).build()

    // rarity
    val LEGENDARY: Rarity = Rarity.create("HARDCOREDUNGEONS\$LEGENDARY", TextFormatting.RED)
}