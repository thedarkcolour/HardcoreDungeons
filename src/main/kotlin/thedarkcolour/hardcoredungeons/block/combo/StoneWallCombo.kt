package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.stonecutting
import thedarkcolour.hardcoredungeons.data.wall
import thedarkcolour.modkit.data.MKRecipeProvider
import java.util.function.Consumer

// Assumes this is a stone block
open class StoneWallCombo(name: String, properties: HProperties) : StairsSlabCombo(name, properties) {
    val wall by BlockMaker.wallWithItem(BlockMaker.getComboName(name) + "_wall", ::block)

    override fun addTags(tags: DataTags) {
        super.addTags(tags)

        tags.block(BlockTags.WALLS, ItemTags.WALLS, wall)
        tags.pickaxe(block)
        tags.pickaxe(stairs)
        tags.pickaxe(slab)
        tags.pickaxe(wall)
    }

    override fun addRecipes(writer: Consumer<FinishedRecipe>, recipes: MKRecipeProvider) {
        super.addRecipes(writer, recipes)

        recipes.wall(wall, block)
        writer.stonecutting(block, wall)
    }
}