package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import java.util.function.Consumer

open class StairsSlabCombo(name: String, properties: HProperties) : BlockCombo() {
    val block by BlockMaker.cubeAllWithItem(name, properties)
    val slab by BlockMaker.slabWithItem(BlockMaker.getComboName(name) + "_slab", { block }, properties)
    val stairs by BlockMaker.stairsWithItem(BlockMaker.getComboName(name) + "_stairs", { block }, properties)

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.SLABS, ItemTags.SLABS, slab)
        tags.block(BlockTags.STAIRS, ItemTags.STAIRS, stairs)
    }

    override fun addRecipes(writer: Consumer<FinishedRecipe>, recipes: RecipesHolder) {
        recipes.apply {
            stairs(stairs, block)
            slab(slab, block)
        }
    }
}