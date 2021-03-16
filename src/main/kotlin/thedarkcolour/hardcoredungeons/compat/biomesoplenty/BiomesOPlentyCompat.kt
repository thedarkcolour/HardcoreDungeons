package thedarkcolour.hardcoredungeons.compat.biomesoplenty

import biomesoplenty.api.block.BOPBlocks
import net.minecraft.data.IFinishedRecipe
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.slabs2Full
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stairs2Full
import java.util.function.Consumer

object BiomesOPlentyCompat {

    fun genRecipes(consumer: Consumer<IFinishedRecipe>) {
        // init here in case of missing mod
        val planks = arrayOf(
            BOPBlocks.fir_planks,
            BOPBlocks.redwood_planks,
            BOPBlocks.cherry_planks,
            BOPBlocks.mahogany_planks,
            BOPBlocks.jacaranda_planks,
            BOPBlocks.palm_planks,
            BOPBlocks.willow_planks,
            BOPBlocks.dead_planks,
            BOPBlocks.magic_planks,
            BOPBlocks.umbran_planks,
            BOPBlocks.hellbark_planks,
        )
        val plankSlabs = arrayOf(
            BOPBlocks.fir_slab,
            BOPBlocks.redwood_slab,
            BOPBlocks.cherry_slab,
            BOPBlocks.mahogany_slab,
            BOPBlocks.jacaranda_slab,
            BOPBlocks.palm_slab,
            BOPBlocks.willow_slab,
            BOPBlocks.dead_slab,
            BOPBlocks.magic_slab,
            BOPBlocks.umbran_slab,
            BOPBlocks.hellbark_slab,
        )
        val plankStairs = arrayOf(
            BOPBlocks.fir_stairs,
            BOPBlocks.redwood_stairs,
            BOPBlocks.cherry_stairs,
            BOPBlocks.mahogany_stairs,
            BOPBlocks.jacaranda_stairs,
            BOPBlocks.palm_stairs,
            BOPBlocks.willow_stairs,
            BOPBlocks.dead_stairs,
            BOPBlocks.magic_stairs,
            BOPBlocks.umbran_stairs,
            BOPBlocks.hellbark_stairs,
        )

        for (i in planks.indices) {
            consumer.slabs2Full(plankSlabs[i], planks[i])
            consumer.stairs2Full(plankStairs[i], planks[i])
        }
    }
}