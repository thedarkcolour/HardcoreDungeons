package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.IFinishedRecipe
import net.minecraft.tags.BlockTags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shaped
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stonecutting
import java.util.function.Consumer

open class StairsSlabWallCombo(name: String, properties: HProperties) : StairsSlabCombo(name, properties) {
    val wall by BlockMaker.wallWithItem(BlockMaker.getComboName(name) + "_wall", ::block)

    override fun addTags(gen: BlockTagGenerator) {
        super.addTags(gen)

        gen.tag(BlockTags.WALLS).add(wall)
    }

    override fun addRecipes(consumer: Consumer<IFinishedRecipe>) {
        super.addRecipes(consumer)

        consumer.shaped(wall, 6) { builder ->
            builder.pattern("xxx")
            builder.pattern("xxx")
            builder.define('x', block)
            builder.unlockedBy("has_item", RecipeGenerator.has(block))
        }
        consumer.stonecutting(block, wall)
    }
}