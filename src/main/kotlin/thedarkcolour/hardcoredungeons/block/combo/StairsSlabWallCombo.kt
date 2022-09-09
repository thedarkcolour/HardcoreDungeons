package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stonecutting
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.wall
import java.util.function.Consumer

open class StairsSlabWallCombo(name: String, properties: HProperties) : StairsSlabCombo(name, properties) {
    val wall by BlockMaker.wallWithItem(BlockMaker.getComboName(name) + "_wall", ::block)

    override fun addTags(tags: DataTags) {
        super.addTags(tags)

        tags.block(BlockTags.WALLS, ItemTags.WALLS, wall)
    }

    override fun addRecipes(consumer: Consumer<FinishedRecipe>) {
        super.addRecipes(consumer)

        consumer.wall(wall, block)
        consumer.stonecutting(block, wall)
    }
}