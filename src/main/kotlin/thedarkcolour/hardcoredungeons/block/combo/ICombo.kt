package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.ItemTagGenerator
import thedarkcolour.hardcoredungeons.data.LootGenerator
import java.util.function.Consumer

interface ICombo {
    fun addLoot(gen: LootGenerator) {}
    fun addRecipes(consumer: Consumer<FinishedRecipe>) {}
    fun setRenderLayers() {}
    fun addTags(tags: DataTags) {}

    fun addTags(gen: BlockTagGenerator) {
        addTags(DataTags.Blocks(gen))
    }

    fun addTags(gen: ItemTagGenerator) {
        addTags(DataTags.Items(gen))
    }
}