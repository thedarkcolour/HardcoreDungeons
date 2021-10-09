package thedarkcolour.hardcoredungeons.item

import net.minecraft.item.Food
import net.minecraft.item.Rarity
import net.minecraft.util.text.TextFormatting

/**
 * @author TheDarkColour
 */
object ItemMisc {

    // foods
    val WILD_BERROOK: Food = Food.Builder().nutrition(2).saturationMod(0.4f).fast().build()
    val CANDY_CANE: Food = Food.Builder().nutrition(3).saturationMod(0.1f).build()
    val CUM_CHALICE: Food = Food.Builder().nutrition(4).saturationMod(0.8f).build()

    // rarity
    val LEGENDARY: Rarity = Rarity.create("HARDCOREDUNGEONS\$LEGENDARY", TextFormatting.RED)
    val MYTHIC: Rarity = Rarity.create("HARDCOREDUNGEONS\$MYTHIC", TextFormatting.GREEN)
}