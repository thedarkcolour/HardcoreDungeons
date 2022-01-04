package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.IFinishedRecipe
import net.minecraft.tags.BlockTags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.slab
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stairs
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stonecutting
import java.util.function.Consumer

open class StairsSlabCombo(name: String, properties: HProperties) : ICombo {
    val block by BlockMaker.cubeAllWithItem(name, properties)
    val slab by BlockMaker.slabWithItem(BlockMaker.getComboName(name) + "_slab", { block }, properties)
    val stairs by BlockMaker.stairsWithItem(BlockMaker.getComboName(name) + "_stairs", { block }, properties)

    override fun addTags(gen: BlockTagGenerator) {
        gen.tag(BlockTags.SLABS).add(slab)
        gen.tag(BlockTags.STAIRS).add(stairs)
    }

    override fun addRecipes(consumer: Consumer<IFinishedRecipe>) {
        consumer.stairs(stairs, block)
        consumer.slab(slab, block)

        consumer.stonecutting(block, stairs)
        consumer.stonecutting(block, slab, outputCount = 2)
    }
}