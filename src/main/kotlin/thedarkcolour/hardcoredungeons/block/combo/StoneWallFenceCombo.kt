package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.tags.BlockTags
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker
import java.util.function.Consumer

// Assumes this is a stone block
class StoneWallFenceCombo(name: String, properties: HProperties) : StoneWallCombo(name, properties) {
    val fence by BlockMaker.registerFence(BlockMaker.getComboName(name) + "_fence", properties)

    init {
        ItemMaker.blockItem(BlockMaker.getComboName(name) + "_fence", ItemModelType.WALL_FENCE_ITEM, ::fence)
    }

    override fun addTags(tags: DataTags) {
        super.addTags(tags)

        tags.block(BlockTags.FENCES, Tags.Items.FENCES, fence)
        tags.pickaxe(block)
        tags.pickaxe(stairs)
        tags.pickaxe(slab)
        tags.pickaxe(wall)
        tags.pickaxe(fence)
    }

    override fun addRecipes(writer: Consumer<FinishedRecipe>, recipes: RecipesHolder) {
        super.addRecipes(writer, recipes)

        recipes.apply {
            shapedCrafting(RecipeCategory.BUILDING_BLOCKS, fence, 6) { builder ->
                builder.pattern("xix")
                builder.pattern("xix")
                builder.define('x', block)
                builder.define('i', slab)
            }
        }
    }
}