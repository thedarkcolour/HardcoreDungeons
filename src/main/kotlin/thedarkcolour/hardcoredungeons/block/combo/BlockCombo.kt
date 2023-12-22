package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraftforge.data.loading.DatagenModLoader
import thedarkcolour.hardcoredungeons.data.BlockLoot
import java.util.function.Consumer

// todo address hard dependencies on ModKit
@Suppress("LeakingThis")
open class BlockCombo {
    init {
        if (DatagenModLoader.isRunningDataGen()) {
            // Leaking this doesn't matter cuz the list gets used later
            ALL_COMBOS.add(this)
        }
    }

    open fun addLoot(gen: BlockLoot) {}
    open fun addRecipes(writer: Consumer<FinishedRecipe>, recipes: RecipesHolder) {}
    open fun setRenderLayers() {}
    open fun addTags(tags: DataTags) {}

    companion object {
        val ALL_COMBOS = arrayListOf<BlockCombo>()
    }
}