package thedarkcolour.hardcoredungeons.data

import net.minecraft.block.Block
import net.minecraft.block.WallBlock
import net.minecraft.data.*
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import net.minecraft.tags.ItemTags
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.decoration.SlabBlock
import thedarkcolour.hardcoredungeons.block.decoration.StairsBlock
import thedarkcolour.hardcoredungeons.block.misc.CompressedBlock
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems
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
        consumer.shaped(HItems.SYRINGE, 2) { builder ->
            builder.key('I', Items.IRON_INGOT)
            builder.key('B', Items.GLASS_BOTTLE)
            builder.key('N', Items.IRON_NUGGET)
            builder.patternLine("IBI")
            builder.patternLine(" I ")
            builder.patternLine(" N ")
            builder.addCriterion("has_item", hasItem(Items.GLASS_BOTTLE))
        }
        consumer.compressionRecipes(HBlocks.COMPRESSED_COBBLESTONE)
    }

    // use item whenever possible but it doesn't really matter
    // since we're only running data gen outside of minecraft game
    private fun Consumer<IFinishedRecipe>.stonecutterRecipes(
        base: IItemProvider,
        slab: IItemProvider? = null,
        vararg others: IItemProvider,
    ) {
        val basePath = base.asItem().registryName!!.path

        if (slab != null) {
            stonecutting(base, slab, 2)
        }

        for (other in others) {
            stonecutting(base, other)
        }
    }

    private fun Consumer<IFinishedRecipe>.stonecutting(
        input: IItemProvider,
        output: IItemProvider,
        outputCount: Int = 1,
    ) = SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(input), output, outputCount)
        .build(this, input.asItem().registryName!!.path + "_from_" + output.asItem().registryName!!.path)

    private fun Consumer<IFinishedRecipe>.compressionRecipes(compressedBlock: CompressedBlock) {
        val variants = compressedBlock.blockVariants

        variants.forEachIndexed { i, block ->
            val previous = if (i == 0) compressedBlock.block() else variants[i - 1]

            shaped(block, 1) { builder ->
                builder.key('#', previous)
                builder.patternLine("###")
                builder.patternLine("###")
                builder.patternLine("###")
                builder.addCriterion("has_previous", if (i == 0) hasItem(Tags.Items.COBBLESTONE) else hasItem(previous))
            }

            shapeless(previous, 9, modLoc(previous.registryName!!.path + "from_" + block.registryName!!.path)) { builder ->
                builder.addIngredient(block)
                builder.addCriterion("has_item", if (i == 0) hasItem(Tags.Items.COBBLESTONE) else hasItem(previous))
            }
        }
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