package thedarkcolour.hardcoredungeons.data

import net.minecraft.block.Block
import net.minecraft.block.WallBlock
import net.minecraft.data.*
import net.minecraft.tags.ItemTags
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.decoration.SlabBlock
import thedarkcolour.hardcoredungeons.block.decoration.StairsBlock
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.tags.HItemTags
import java.util.function.Consumer

class RecipeGenerator(generatorIn: DataGenerator) : RecipeProvider(generatorIn) {
    /**
     * Registers all recipes to the given consumer.
     *
     * Uses several extension functions declared later on in the file.
     */
    override fun registerRecipes(consumer: Consumer<IFinishedRecipe>) {
        consumer.shapeless(HBlocks.LUMLIGHT_PLANKS, 4) { builder ->
            builder.addIngredient(HItemTags.LUMLIGHT_LOGS)
            builder.setGroup("planks")
            builder.addCriterion("has_log", hasItem(HItemTags.LUMLIGHT_LOGS))
        }
        consumer.shapeless(HBlocks.SHROOMY_COBBLESTONE, 1) { builder ->
            builder.addIngredient(Tags.Items.COBBLESTONE)
            builder.addIngredient(Tags.Items.MUSHROOMS)
            builder.addCriterion("has_mushroom", hasItem(Tags.Items.MUSHROOMS))
        }
        consumer.slab(HBlocks.SHROOMY_COBBLESTONE_SLAB, HBlocks.SHROOMY_COBBLESTONE)
        consumer.stairs(HBlocks.SHROOMY_COBBLESTONE_STAIRS, HBlocks.SHROOMY_COBBLESTONE)
        consumer.shapeless(HBlocks.SHROOMY_STONE_BRICKS, 1) { builder ->
            builder.addIngredient(ItemTags.STONE_BRICKS)
            builder.addIngredient(Tags.Items.MUSHROOMS)
            builder.addCriterion("has_mushroom", hasItem(Tags.Items.MUSHROOMS))
        }
        consumer.slab(HBlocks.SHROOMY_STONE_BRICK_SLAB, HBlocks.SHROOMY_STONE_BRICKS)
        consumer.stairs(HBlocks.SHROOMY_STONE_BRICK_STAIRS, HBlocks.SHROOMY_STONE_BRICKS)
    }

    private fun Consumer<IFinishedRecipe>.shapeless(
        result: IItemProvider,
        resultCount: Int,
        id: ResourceLocation = ForgeRegistries.ITEMS.getKey(result.asItem())!!,
        addIngredients: (ShapelessRecipeBuilder) -> Unit,
    ) {
        val builder = ShapelessRecipeBuilder(result, resultCount)
        addIngredients(builder)

        builder.build(this, id)
    }

    private fun Consumer<IFinishedRecipe>.shaped(
        result: IItemProvider,
        resultCount: Int,
        id: ResourceLocation = ForgeRegistries.ITEMS.getKey(result.asItem())!!,
        addIngredients: (ShapedRecipeBuilder) -> Unit,
    ) {
        val builder = ShapedRecipeBuilder(result, resultCount)
        addIngredients(builder)

        builder.build(this, id)
    }

    private fun Consumer<IFinishedRecipe>.slab(
        slab: SlabBlock,
        block: Block,
    ) {
        // slab recipe
        shaped(slab, 6) { builder ->
            builder.key('#', block)
            builder.patternLine("###")
            builder.addCriterion("has_block", hasItem(block))
        }
        // convert from slab to full block
        shaped(block, 1,
            modLoc(block.registryName!!.path + "_from_slabs")
        ) { builder ->
            builder.key('#', slab)
            builder.patternLine("#")
            builder.patternLine("#")
            builder.addCriterion("has_block", hasItem(block))
        }
        // stonecutter recipe
        //stonecutter(block, 2) { builder ->
        //    builder.addResult()
        //}
    }

    private fun Consumer<IFinishedRecipe>.stairs(
        stairs: StairsBlock,
        block: Block,
    ) {
        shaped(stairs, 4) { builder ->
            builder.key('#', block)
            builder.patternLine("#  ")
            builder.patternLine("## ")
            builder.patternLine("###")
            builder.addCriterion("has_block", hasItem(block))
        }

        // convert back into full block
        shaped(block, 3,
            modLoc(block.registryName!!.path + "_from_stairs")
        ) { builder ->
            builder.key('#', stairs)
            builder.patternLine("##")
            builder.patternLine("##")
            builder.addCriterion("has_block", hasItem(block))
        }
    }

    private fun Consumer<IFinishedRecipe>.wall(
        wall: WallBlock,
        block: Block
    ) {
        shaped(wall, 6) { builder ->
            builder.key('#', block)
            builder.patternLine("###")
            builder.patternLine("###")
            builder.addCriterion("has_block", hasItem(block))
        }
    }

    fun modLoc(path: String): ResourceLocation {
        return ResourceLocation(HardcoreDungeons.ID, path)
    }
}