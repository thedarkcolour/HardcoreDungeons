package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.IFinishedRecipe
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stonecutterRecipes
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stonecutting
import java.util.function.Consumer

class PolishedStoneBrickCombo(name: String, props: HProperties) : ICombo {
    val stone = StairsSlabCombo(name, props)
    val brick = StairsSlabWallFenceCombo(name.removeSuffix("stone").removeSuffix("rock") + "bricks", props)
    val polished = StairsSlabWallCombo("polished_$name", props)

    override fun addTags(gen: BlockTagGenerator) {
        stone.addTags(gen)
        brick.addTags(gen)
        polished.addTags(gen)

        // Add anything we didn't already add
    }

    override fun addRecipes(consumer: Consumer<IFinishedRecipe>) {
        consumer.stonecutterRecipes(base = stone.block, stone.stairs, brick.block, brick.stairs, brick.fence, brick.wall, polished.block, polished.stairs, polished.wall)
        consumer.stonecutterRecipes(base = brick.block, brick.stairs, brick.fence, brick.wall, polished.block, polished.stairs, polished.wall)
        consumer.stonecutterRecipes(base = polished.block, polished.stairs, polished.wall)

        consumer.stonecutting(stone.block, stone.slab, 2)
        consumer.stonecutting(stone.block, brick.slab, 2)
        consumer.stonecutting(stone.block, polished.slab, 2)

        consumer.stonecutting(brick.block, brick.slab, 2)
        consumer.stonecutting(brick.block, polished.slab, 2)

        consumer.stonecutting(polished.block, polished.slab, 2)
    }
}