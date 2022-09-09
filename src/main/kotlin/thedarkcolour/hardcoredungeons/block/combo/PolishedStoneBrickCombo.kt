package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stonecutterRecipes
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stonecutting
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.twoByTwo
import java.util.function.Consumer

class PolishedStoneBrickCombo(name: String, props: HProperties) : ICombo {
    val stone = StairsSlabCombo(name, props)
    val brick = StairsSlabWallFenceCombo(name.removeSuffix("stone").removeSuffix("rock") + "bricks", props)
    val polished = StairsSlabWallCombo("polished_$name", props)

    override fun addTags(tags: DataTags) {
        stone.addTags(tags)
        brick.addTags(tags)
        polished.addTags(tags)

        tags.block(Tags.Blocks.STONE, Tags.Items.STONE, stone.block)
    }

    override fun addRecipes(consumer: Consumer<FinishedRecipe>) {
        consumer.stonecutterRecipes(base = stone.block, stone.stairs, brick.block, brick.stairs, brick.fence, brick.wall, polished.block, polished.stairs, polished.wall)
        consumer.stonecutterRecipes(base = brick.block, brick.stairs, brick.fence, polished.block, polished.stairs, polished.wall)
        consumer.stonecutterRecipes(base = polished.block, polished.stairs)

        consumer.stonecutting(stone.block, stone.slab, 2)
        consumer.stonecutting(stone.block, brick.slab, 2)
        consumer.stonecutting(stone.block, polished.slab, 2)

        consumer.stonecutting(brick.block, brick.slab, 2)
        consumer.stonecutting(brick.block, polished.slab, 2)

        consumer.stonecutting(polished.block, polished.slab, 2)

        consumer.twoByTwo(brick.block, 4, stone.block, trigger = "has_item")
        consumer.twoByTwo(polished.block, 4, brick.block, trigger = "has_item")

        stone.addRecipes(consumer)
        brick.addRecipes(consumer)
        polished.addRecipes(consumer)
    }
}