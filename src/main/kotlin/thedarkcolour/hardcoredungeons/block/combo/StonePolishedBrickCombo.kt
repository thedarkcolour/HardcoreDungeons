package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.stonecutterRecipes
import thedarkcolour.hardcoredungeons.data.stonecutting
import java.util.function.Consumer

class StonePolishedBrickCombo(name: String, props: HProperties) : BlockCombo() {
    val stone = StairsSlabCombo(name, props)
    val brick = StoneWallFenceCombo(name.removeSuffix("stone").removeSuffix("rock") + "bricks", props)
    val polished = StoneWallCombo("polished_$name", props)

    override fun addTags(tags: DataTags) {
        tags.block(Tags.Blocks.STONE, Tags.Items.STONE, stone.block)

        tags.pickaxe(stone.block)
        tags.pickaxe(stone.stairs)
        tags.pickaxe(stone.slab)
    }

    override fun addRecipes(writer: Consumer<FinishedRecipe>, recipes: RecipesHolder) {
        writer.stonecutterRecipes(base = stone.block, stone.stairs, brick.block, brick.stairs, brick.fence, brick.wall, polished.block, polished.stairs, polished.wall)
        writer.stonecutterRecipes(base = brick.block, brick.stairs, brick.fence, polished.block, polished.stairs, polished.wall)
        writer.stonecutterRecipes(base = polished.block, polished.stairs)

        writer.stonecutting(stone.block, stone.slab, 2)
        writer.stonecutting(stone.block, brick.slab, 2)
        writer.stonecutting(stone.block, polished.slab, 2)

        writer.stonecutting(brick.block, brick.slab, 2)
        writer.stonecutting(brick.block, polished.slab, 2)

        writer.stonecutting(polished.block, polished.slab, 2)

        recipes.apply {
            grid2x2(RecipeCategory.BUILDING_BLOCKS, brick.block, Ingredient.of(stone.block))
            grid2x2(RecipeCategory.BUILDING_BLOCKS, polished.block, Ingredient.of(brick.block))
        }
    }
}