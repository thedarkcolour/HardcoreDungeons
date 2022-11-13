package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraftforge.data.loading.DatagenModLoader
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.ItemTagGenerator
import thedarkcolour.hardcoredungeons.data.LootGenerator
import java.util.function.Consumer

@Suppress("LeakingThis")
open class BlockCombo {
    init {
        if (DatagenModLoader.isRunningDataGen()) {
            // Leaking this doesn't matter cuz the list gets used later
            ALL_COMBOS.add(this)
        }
    }

    open fun addLoot(gen: LootGenerator) {}
    open fun addRecipes(consumer: Consumer<FinishedRecipe>) {}
    open fun setRenderLayers() {}
    open fun addTags(tags: DataTags) {}

    fun addTags(gen: BlockTagGenerator) {
        addTags(DataTags.Blocks(gen))
    }

    fun addTags(gen: ItemTagGenerator) {
        addTags(DataTags.Items(gen))
    }

    companion object {
        val ALL_COMBOS = arrayListOf<BlockCombo>()
    }
}