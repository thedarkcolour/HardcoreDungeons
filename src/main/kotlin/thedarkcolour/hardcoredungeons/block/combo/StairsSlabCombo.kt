package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.slab
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stairs
import java.util.function.Consumer

open class StairsSlabCombo(name: String, properties: HProperties) : ICombo {
    val block by BlockMaker.cubeAllWithItem(name, properties)
    val slab by BlockMaker.slabWithItem(BlockMaker.getComboName(name) + "_slab", { block }, properties)
    val stairs by BlockMaker.stairsWithItem(BlockMaker.getComboName(name) + "_stairs", { block }, properties)

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.SLABS, ItemTags.SLABS, slab)
        tags.block(BlockTags.STAIRS, ItemTags.STAIRS, stairs)
    }

    override fun addRecipes(consumer: Consumer<FinishedRecipe>) {
        consumer.stairs(stairs, block)
        consumer.slab(slab, block)
    }
}