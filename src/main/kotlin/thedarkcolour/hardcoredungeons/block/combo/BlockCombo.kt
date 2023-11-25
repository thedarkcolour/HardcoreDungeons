package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.data.loading.DatagenModLoader
import thedarkcolour.hardcoredungeons.data.BlockLoot
import thedarkcolour.modkit.data.MKRecipeProvider
import thedarkcolour.modkit.data.MKTagsProvider
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
    open fun addRecipes(writer: Consumer<FinishedRecipe>, recipes: MKRecipeProvider) {}
    open fun setRenderLayers() {}
    open fun addTags(tags: DataTags) {}

    fun addTags(gen: MKTagsProvider<Block>) {
        addTags(DataTags.Blocks(gen))
    }

    fun addTags(gen: MKTagsProvider<Item>) {
        addTags(DataTags.Items(gen))
    }

    companion object {
        val ALL_COMBOS = arrayListOf<BlockCombo>()
    }
}