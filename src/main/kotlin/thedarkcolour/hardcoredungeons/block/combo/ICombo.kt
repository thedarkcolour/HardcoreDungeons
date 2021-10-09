package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.IFinishedRecipe
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.ItemTagGenerator
import thedarkcolour.hardcoredungeons.data.LootGenerator
import java.util.function.Consumer

interface ICombo {
    fun addLoot(gen: LootGenerator) {}
    fun addTags(gen: BlockTagGenerator) {}
    fun addTags(gen: ItemTagGenerator) {}
    fun addRecipes(consumer: Consumer<IFinishedRecipe>) {}
    fun setRenderLayers() {}
}