package thedarkcolour.hardcoredungeons.block.combo

import net.minecraftforge.data.loading.DatagenModLoader
import thedarkcolour.modkit.data.MKRecipeProvider

// We assume that "recipes" is of type MKRecipeProvider
// This code doesn't get used outside of dev, so correctness isn't too important
class RecipesHolder(val recipes: Any) {
    inline fun apply(action: MKRecipeProvider.() -> Unit) {
        // guard classloading behind data generation
        if (DatagenModLoader.isRunningDataGen()) {
            val recipes = this.recipes as MKRecipeProvider

            recipes.action()
        }
    }
}