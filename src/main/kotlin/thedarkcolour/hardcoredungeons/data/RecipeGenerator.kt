package thedarkcolour.hardcoredungeons.data

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.WallBlock
import net.minecraft.data.*
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.tags.ITag
import net.minecraft.tags.ItemTags
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.IForgeRegistryEntry
import thedarkcolour.hardcoredungeons.block.decoration.SlabBlock
import thedarkcolour.hardcoredungeons.block.decoration.StairsBlock
import thedarkcolour.hardcoredungeons.block.misc.CompressedBlock
import thedarkcolour.hardcoredungeons.compat.getBiomesOPlentyCompat
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tags.HItemTags
import thedarkcolour.hardcoredungeons.util.modLoc
import java.util.function.Consumer

class RecipeGenerator(generatorIn: DataGenerator) : RecipeProvider(generatorIn) {
    /**
     * Registers all recipes to the given consumer.
     *
     * Uses several extension functions declared later on in the file.
     */
    override fun registerRecipes(consumer: Consumer<IFinishedRecipe>) {
        // lumlight blocks
        consumer.shapeless(HBlocks.LUMLIGHT_PLANKS, 4) { builder ->
            builder.addIngredient(HItemTags.LUMLIGHT_LOGS)
            builder.setGroup("planks")
            builder.addCriterion("has_log", hasItem(HItemTags.LUMLIGHT_LOGS))
        }
        consumer.slab(HBlocks.LUMLIGHT_SLAB, HBlocks.LUMLIGHT_PLANKS)
        consumer.stairs(HBlocks.LUMLIGHT_STAIRS, HBlocks.LUMLIGHT_PLANKS)
        consumer.shapeless(HBlocks.SHROOMY_COBBLESTONE, 1) { builder ->
            builder.addIngredient(Tags.Items.COBBLESTONE)
            builder.addIngredient(Tags.Items.MUSHROOMS)
            builder.addCriterion("has_mushroom", hasItem(Tags.Items.MUSHROOMS))
        }


        // shroomy cobblestone + stone brick
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
            builder.key('B', Items.GLASS_BOTTLE)
            builder.key('I', Items.IRON_INGOT)
            builder.key('N', Items.IRON_NUGGET)
            builder.patternLine("IBI")
            builder.patternLine(" I ")
            builder.patternLine(" N ")
            builder.addCriterion("has_item", hasItem(Items.GLASS_BOTTLE))
        }


        // ammunition
        consumer.shaped(HItems.BULLET, 8) { builder ->
            builder.key('I', Items.IRON_NUGGET)
            builder.key('G', Items.GUNPOWDER)
            builder.patternLine("III")
            builder.patternLine("IGI")
            builder.patternLine("III")
            builder.addCriterion("has_item", hasItem(Items.GUNPOWDER))
        }
        consumer.shaped(HItems.INCENDIARY_BULLET, 8) { builder ->
            builder.key('I', HItems.BULLET)
            builder.key('G', Items.BLAZE_POWDER)
            builder.patternLine("III")
            builder.patternLine("IGI")
            builder.patternLine("III")
            builder.addCriterion("has_item", hasItem(Items.GUNPOWDER))
        }


        // tools
        consumer.sword(HItems.MALACHITE_SWORD, HItems.MALACHITE_CRYSTAL)
        consumer.shovel(HItems.MALACHITE_SHOVEL, HItems.MALACHITE_CRYSTAL)
        consumer.pickaxe(HItems.MALACHITE_PICKAXE, HItems.MALACHITE_CRYSTAL)
        consumer.axe(HItems.MALACHITE_AXE, HItems.MALACHITE_CRYSTAL)
        consumer.hoe(HItems.MALACHITE_HOE, HItems.MALACHITE_CRYSTAL)

        consumer.sword(HItems.RAINBOWSTONE_SWORD, HItems.RAINBOWSTONE_GEM)
        consumer.shovel(HItems.RAINBOWSTONE_SHOVEL, HItems.RAINBOWSTONE_GEM)
        consumer.pickaxe(HItems.RAINBOWSTONE_PICKAXE, HItems.RAINBOWSTONE_GEM)
        consumer.axe(HItems.RAINBOWSTONE_AXE, HItems.RAINBOWSTONE_GEM)
        consumer.hoe(HItems.RAINBOWSTONE_HOE, HItems.RAINBOWSTONE_GEM)


        // stonecutter recipes for HCD blocks
        consumer.stonecutterRecipes(HBlocks.SHROOMY_COBBLESTONE, HBlocks.SHROOMY_COBBLESTONE_STAIRS, HBlocks.SHROOMY_COBBLESTONE_WALL, slab = HBlocks.SHROOMY_COBBLESTONE_SLAB)
        consumer.stonecutterRecipes(HBlocks.SHROOMY_STONE_BRICKS, HBlocks.SHROOMY_STONE_BRICK_STAIRS, HBlocks.SHROOMY_STONE_BRICK_WALL, slab = HBlocks.SHROOMY_STONE_BRICK_SLAB)
        consumer.stonecutterRecipes(HBlocks.CHOCOLATE_BLOCK, HBlocks.CHOCOLATE_STAIRS, slab = HBlocks.CHOCOLATE_SLAB)
        consumer.stonecutterRecipes(HBlocks.CASTLETON_STONE, HBlocks.CASTLETON_BRICKS, HBlocks.CASTLETON_BRICK_STAIRS, HBlocks.CASTLETON_BRICK_FENCE, HBlocks.CASTLETON_BRICK_WALL, slab = HBlocks.CASTLETON_BRICK_SLAB)
        consumer.stonecutterRecipes(HBlocks.CASTLETON_BRICKS, HBlocks.CASTLETON_BRICK_STAIRS, HBlocks.CASTLETON_BRICK_FENCE, HBlocks.CASTLETON_BRICK_WALL, slab = HBlocks.CASTLETON_BRICK_SLAB)
        consumer.stonecutterRecipes(HBlocks.CHARGED_CASTLETON_BRICKS, HBlocks.CHARGED_CASTLETON_BRICK_STAIRS, HBlocks.CHARGED_CASTLETON_BRICK_FENCE, HBlocks.CHARGED_CASTLETON_BRICK_WALL, slab = HBlocks.CHARGED_CASTLETON_BRICK_SLAB)
        consumer.stonecutterRecipes(Blocks.DIAMOND_BLOCK, HBlocks.CHISELED_DIAMOND_BLOCK)
        consumer.stonecutterRecipes(HBlocks.RAINBOW_BRICKS, HBlocks.RAINBOW_BRICK_STAIRS, HBlocks.RAINBOW_BRICK_WALL, HBlocks.RAINBOW_BRICK_FENCE, slab = HBlocks.RAINBOW_BRICK_SLAB)
        consumer.stonecutterRecipes(HBlocks.RAINBOW_ROCK, HBlocks.RAINBOW_BRICKS, HBlocks.RAINBOW_BRICK_STAIRS, HBlocks.RAINBOW_BRICK_WALL, HBlocks.RAINBOW_BRICK_FENCE, slab = HBlocks.RAINBOW_BRICK_SLAB)


        // food
        consumer.smeltingRecipe(HItems.VENISON, HItems.COOKED_VENISON, 0.35f, smokingRecipe = true, campfireRecipe = true)
        consumer.smeltingRecipe(HBlocks.RAINBOWSTONE_ORE, HItems.RAINBOWSTONE_GEM, 0.85f, blastingRecipe = true)
        //consumer.campfireRecipe(HItems.VENISON, HItems.COOKED_VENISON, 0.35f)


        // convert slabs to full block
        consumer.slabs2Full(Blocks.OAK_SLAB, Blocks.OAK_PLANKS)
        consumer.slabs2Full(Blocks.SPRUCE_SLAB, Blocks.SPRUCE_PLANKS)
        consumer.slabs2Full(Blocks.BIRCH_SLAB, Blocks.BIRCH_PLANKS)
        consumer.slabs2Full(Blocks.JUNGLE_SLAB, Blocks.JUNGLE_PLANKS)
        consumer.slabs2Full(Blocks.ACACIA_SLAB, Blocks.ACACIA_PLANKS)
        consumer.slabs2Full(Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_PLANKS)
        consumer.slabs2Full(Blocks.CRIMSON_SLAB, Blocks.CRIMSON_PLANKS)
        consumer.slabs2Full(Blocks.WARPED_SLAB, Blocks.WARPED_PLANKS)
        consumer.slabs2Full(Blocks.STONE_SLAB, Blocks.STONE)
        consumer.slabs2Full(Blocks.SMOOTH_STONE_SLAB, Blocks.SMOOTH_STONE)
        consumer.slabs2Full(Blocks.CUT_SANDSTONE_SLAB, Blocks.CUT_SANDSTONE)
        consumer.slabs2Full(Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE)
        consumer.slabs2Full(Blocks.BRICK_SLAB, Blocks.BRICKS)
        consumer.slabs2Full(Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.CUT_RED_SANDSTONE)
        consumer.slabs2Full(Blocks.PURPUR_SLAB, Blocks.PURPUR_BLOCK)
        consumer.slabs2Full(Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE)
        consumer.slabs2Full(Blocks.PRISMARINE_BRICK_SLAB, Blocks.PRISMARINE_BRICKS)
        consumer.slabs2Full(Blocks.DARK_PRISMARINE_SLAB, Blocks.DARK_PRISMARINE)
        consumer.slabs2Full(Blocks.POLISHED_GRANITE_SLAB, Blocks.POLISHED_GRANITE)
        consumer.slabs2Full(Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.SMOOTH_RED_SANDSTONE)
        consumer.slabs2Full(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICKS)
        consumer.slabs2Full(Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_DIORITE)
        consumer.slabs2Full(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE)
        consumer.slabs2Full(Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE_BRICKS)
        consumer.slabs2Full(Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE)
        consumer.slabs2Full(Blocks.SMOOTH_QUARTZ_SLAB, Blocks.SMOOTH_QUARTZ)
        consumer.slabs2Full(Blocks.GRANITE_SLAB, Blocks.GRANITE)
        consumer.slabs2Full(Blocks.ANDESITE_SLAB, Blocks.ANDESITE)
        consumer.slabs2Full(Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICKS)
        consumer.slabs2Full(Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_ANDESITE)
        consumer.slabs2Full(Blocks.DIORITE_SLAB, Blocks.DIORITE)
        consumer.slabs2Full(Blocks.BLACKSTONE_SLAB, Blocks.BLACKSTONE)
        consumer.slabs2Full(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICKS)
        // can't do these because of chiseled blocks
        // consumer.slabs2Full(Blocks.NETHER_BRICK_SLAB, Blocks.NETHER_BRICKS)
        // consumer.slabs2Full(Blocks.QUARTZ_SLAB, Blocks.QUARTZ_BLOCK)
        // consumer.slabs2Full(Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE)
        // consumer.slabs2Full(Blocks.STONE_BRICK_SLAB, Blocks.STONE_BRICKS)
        // consumer.slabs2Full(Blocks.SANDSTONE_SLAB, Blocks.SANDSTONE)
        // consumer.slabs2Full(Blocks.RED_SANDSTONE_SLAB, Blocks.RED_SANDSTONE)


        // convert stairs to full block
        consumer.stairs2Full(Blocks.OAK_STAIRS, Blocks.OAK_PLANKS)
        consumer.stairs2Full(Blocks.SPRUCE_STAIRS, Blocks.SPRUCE_PLANKS)
        consumer.stairs2Full(Blocks.BIRCH_STAIRS, Blocks.BIRCH_PLANKS)
        consumer.stairs2Full(Blocks.JUNGLE_STAIRS, Blocks.JUNGLE_PLANKS)
        consumer.stairs2Full(Blocks.ACACIA_STAIRS, Blocks.ACACIA_PLANKS)
        consumer.stairs2Full(Blocks.DARK_OAK_STAIRS, Blocks.DARK_OAK_PLANKS)
        consumer.stairs2Full(Blocks.CRIMSON_STAIRS, Blocks.CRIMSON_PLANKS)
        consumer.stairs2Full(Blocks.WARPED_STAIRS, Blocks.WARPED_PLANKS)
        consumer.stairs2Full(Blocks.STONE_STAIRS, Blocks.STONE)
        consumer.stairs2Full(Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE)
        consumer.stairs2Full(Blocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE)
        consumer.stairs2Full(Blocks.BRICK_STAIRS, Blocks.BRICKS)
        consumer.stairs2Full(Blocks.STONE_BRICK_STAIRS, Blocks.STONE_BRICKS)
        consumer.stairs2Full(Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_BRICKS)
        consumer.stairs2Full(Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE)
        consumer.stairs2Full(Blocks.PURPUR_STAIRS, Blocks.PURPUR_BLOCK)
        consumer.stairs2Full(Blocks.PRISMARINE_STAIRS, Blocks.PRISMARINE)
        consumer.stairs2Full(Blocks.PRISMARINE_BRICK_STAIRS, Blocks.PRISMARINE_BRICKS)
        consumer.stairs2Full(Blocks.DARK_PRISMARINE_STAIRS, Blocks.DARK_PRISMARINE)
        consumer.stairs2Full(Blocks.POLISHED_GRANITE_STAIRS, Blocks.POLISHED_GRANITE)
        consumer.stairs2Full(Blocks.SMOOTH_RED_SANDSTONE_STAIRS, Blocks.SMOOTH_RED_SANDSTONE)
        consumer.stairs2Full(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICKS)
        consumer.stairs2Full(Blocks.POLISHED_DIORITE_STAIRS, Blocks.POLISHED_DIORITE)
        consumer.stairs2Full(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE)
        consumer.stairs2Full(Blocks.END_STONE_BRICK_STAIRS, Blocks.END_STONE_BRICKS)
        consumer.stairs2Full(Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SMOOTH_SANDSTONE)
        consumer.stairs2Full(Blocks.SMOOTH_QUARTZ_STAIRS, Blocks.SMOOTH_QUARTZ)
        consumer.stairs2Full(Blocks.GRANITE_STAIRS, Blocks.GRANITE)
        consumer.stairs2Full(Blocks.ANDESITE_STAIRS, Blocks.ANDESITE)
        consumer.stairs2Full(Blocks.RED_NETHER_BRICK_STAIRS, Blocks.RED_NETHER_BRICKS)
        consumer.stairs2Full(Blocks.POLISHED_ANDESITE_STAIRS, Blocks.POLISHED_ANDESITE)
        consumer.stairs2Full(Blocks.DIORITE_STAIRS, Blocks.DIORITE)
        consumer.stairs2Full(Blocks.BLACKSTONE_STAIRS, Blocks.BLACKSTONE)
        consumer.stairs2Full(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICKS)
        consumer.stairs2Full(Blocks.POLISHED_BLACKSTONE_STAIRS, Blocks.POLISHED_BLACKSTONE)


        // compressed (storage) block recipes
        consumer.compressionRecipes(HBlocks.COMPRESSED_COBBLESTONE)

        consumer.storage(HBlocks.CHOCOLATE_BLOCK, Items.COCOA_BEANS)
        consumer.slab(HBlocks.CHOCOLATE_SLAB, HBlocks.CHOCOLATE_BLOCK)
        consumer.stairs(HBlocks.CHOCOLATE_STAIRS, HBlocks.CHOCOLATE_BLOCK)

        consumer.storage(HBlocks.MALACHITE_BLOCK, HItems.MALACHITE_CRYSTAL, HItemTags.GEMS_MALACHITE)
        consumer.storage(HBlocks.RAINBOWSTONE_BLOCK, HItems.RAINBOWSTONE_GEM)

        getBiomesOPlentyCompat()?.genRecipes(consumer)
    }

     companion object {
         fun path(entry: IForgeRegistryEntry<*>): String {
             return entry.registryName!!.path
         }

         private fun ShapedRecipeBuilder.lines(first: String, second: String? = null, third: String? = null) {
             patternLine(first)
             patternLine(second ?: return)
             patternLine(third ?: return)
         }

         private fun CookingRecipeBuilder.buildNullable(consumer: Consumer<IFinishedRecipe>, recipeName: String?) {
             if (recipeName != null) {
                 build(consumer, recipeName)
             } else {
                 build(consumer)
             }
         }

         // 3x3 storage recipe (to and from)
         private fun Consumer<IFinishedRecipe>.storage(storage: Block, regular: Item, regularTag: ITag<Item>? = null) {
             val item = regular.asItem()
             // compress
             shaped(storage, 1) { builder ->
                 if (regularTag != null) {
                     builder.key('F', regularTag)
                 } else {
                     builder.key('F', item)
                 }
                 builder.lines("FFF", "FFF", "FFF")
                 builder.addCriterion("has_item", if (regularTag != null) hasItem(regularTag) else hasItem(item))
             }
             // decompress
             shapeless(item, 9, id = modLoc(path(item) + "_from_" + path(storage))) { builder ->
                 builder.addIngredient(storage)
                 builder.addCriterion("has_item", hasItem(storage))
             }
         }

         fun Consumer<IFinishedRecipe>.slabs2Full(slab: Block, full: Block) {
             shaped(full, 1, modLoc(path(full) + "_from_slabs")) { builder ->
                 builder.key('#', slab)
                 builder.lines("#", "#")
                 builder.addCriterion("has_item", hasItem(slab))
             }
         }

         private fun Consumer<IFinishedRecipe>.campfireRecipe(input: IItemProvider, output: IItemProvider, experience: Float, duration: Int = 600) {
             val inputItem = input.asItem()
             val outputItem = output.asItem()

             CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(inputItem), outputItem, experience, duration, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_item", hasItem(inputItem)).build(this, path(outputItem) + "_from_campfire")
         }

         private fun Consumer<IFinishedRecipe>.smeltingRecipe(
             input: IItemProvider,
             output: IItemProvider,
             experience: Float,
             recipeName: String? = null,
             smokingRecipe: Boolean = false,
             campfireRecipe: Boolean = false,
             blastingRecipe: Boolean = false,
         ) {
             CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(input), output, experience, 200).addCriterion("has_item", hasItem(input)).buildNullable(this, recipeName)

             if (smokingRecipe)  smokingRecipe (input, output, experience)
             if (campfireRecipe) campfireRecipe(input, output, experience)
             if (blastingRecipe) blastingRecipe(input, output, experience)
         }

         private fun Consumer<IFinishedRecipe>.blastingRecipe(
             input: IItemProvider,
             output: IItemProvider,
             experience: Float,
             recipeName: String? = null,
         ) {
             val item = input.asItem()

             CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(item), output, experience, 100).addCriterion("has_item", hasItem(input)).build(this, recipeName ?: path(item) + "_from_blasting")
         }

         private fun Consumer<IFinishedRecipe>.smokingRecipe(
             input: IItemProvider,
             output: IItemProvider,
             experience: Float,
             recipeName: String? = null,
         ) {
             val item = input.asItem()

             CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(item), output, experience, 100, IRecipeSerializer.SMOKING).addCriterion("has_item", hasItem(item)).build(this, recipeName ?: path(item) + "_from_smoking")
         }

         private fun Consumer<IFinishedRecipe>.stonecutterRecipes(
             base: IItemProvider,
             vararg others: IItemProvider,
             slab: IItemProvider? = null,
         ) {
             for (other in others) {
                 stonecutting(base, other)
             }

             stonecutting(base, slab ?: return, 2)
         }

         private fun Consumer<IFinishedRecipe>.stonecutting(
             input: IItemProvider,
             output: IItemProvider,
             outputCount: Int = 1,
         ) = SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(input), output, outputCount)
             .addCriterion("has_item", hasItem(input))
             .build(this, path(input.asItem()) + "_from_" + path(output.asItem()))

         private fun Consumer<IFinishedRecipe>.compressionRecipes(compressedBlock: CompressedBlock) {
             val variants = compressedBlock.blockVariants

             variants.forEachIndexed { i, block ->
                 val previous = if (i == 0) compressedBlock.block() else variants[i - 1]

                 shaped(block, 1) { builder ->
                     builder.key('#', previous)
                     builder.lines("###", "###", "###")
                     builder.addCriterion("has_previous", if (i == 0) hasItem(Tags.Items.COBBLESTONE) else hasItem(previous))
                 }

                 shapeless(previous, 9, modLoc(path(previous) + "_from_" + path(block))) { builder ->
                     builder.addIngredient(block)
                     builder.addCriterion("has_item", if (i == 0) hasItem(Tags.Items.COBBLESTONE) else hasItem(previous))
                 }
             }
         }

         private fun Consumer<IFinishedRecipe>.pickaxe(
             result: IItemProvider,
             material: IItemProvider,
             rod: IItemProvider = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.key('I', rod)
                 builder.key('#', material)
                 builder.lines("###", " I ", " I ")
                 builder.addCriterion("has_item", hasItem(material))
             }
         }

         private fun Consumer<IFinishedRecipe>.shovel(
             result: IItemProvider,
             material: IItemProvider,
             rod: IItemProvider = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.key('I', rod)
                 builder.key('#', material)
                 builder.lines("#", "I", "I")
                 builder.addCriterion("has_item", hasItem(material))
             }
         }

         private fun Consumer<IFinishedRecipe>.axe(
             result: IItemProvider,
             material: IItemProvider,
             rod: IItemProvider = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.key('I', rod)
                 builder.key('#', material)
                 builder.lines("##", "#I", " I")
                 builder.addCriterion("has_item", hasItem(material))
             }
         }

         private fun Consumer<IFinishedRecipe>.hoe(
             result: IItemProvider,
             material: IItemProvider,
             rod: IItemProvider = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.key('I', rod)
                 builder.key('#', material)
                 builder.lines("##", " I", " I")
                 builder.addCriterion("has_item", hasItem(material))
             }
         }

         private fun Consumer<IFinishedRecipe>.sword(
             result: IItemProvider,
             material: IItemProvider,
             rod: Item = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.key('I', rod)
                 builder.key('#', material)
                 builder.lines("#", "#", "I")
                 builder.addCriterion("has_item", hasItem(material))
             }
         }

         private fun Consumer<IFinishedRecipe>.shapeless(
             result: IItemProvider,
             resultCount: Int,
             id: ResourceLocation = result.asItem().registryName!!,
             addIngredients: (ShapelessRecipeBuilder) -> Unit,
         ) {
             val builder = ShapelessRecipeBuilder(result, resultCount)
             addIngredients(builder)

             builder.build(this, id)
         }

         private fun Consumer<IFinishedRecipe>.shaped(
             result: IItemProvider,
             resultCount: Int,
             id: ResourceLocation = result.asItem().registryName!!,
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
             slabs2Full(slab, block)
         }

         private fun Consumer<IFinishedRecipe>.stairs(
             stairs: StairsBlock,
             block: Block,
         ) {
             shaped(stairs, 4) { builder ->
                 builder.key('#', block)
                 builder.lines("#  ", "## ", "###")
                 builder.addCriterion("has_block", hasItem(block))
             }

             stairs2Full(stairs, block) // convert back into full block
         }

         fun Consumer<IFinishedRecipe>.stairs2Full(stairs: Block, block: Block) {
             shaped(block, 3,
                 modLoc(path(block) + "_from_stairs")
             ) { builder ->
                 builder.key('#', stairs)
                 builder.lines("##", "##")
                 builder.addCriterion("has_block", hasItem(block))
             }
         }

         private fun Consumer<IFinishedRecipe>.wall(
             wall: WallBlock,
             block: Block
         ) {
             shaped(wall, 6) { builder ->
                 builder.key('#', block)
                 builder.lines("###", "###")
                 builder.addCriterion("has_block", hasItem(block))
             }
         }
     }
}