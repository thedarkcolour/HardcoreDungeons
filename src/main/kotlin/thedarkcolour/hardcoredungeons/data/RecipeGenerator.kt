package thedarkcolour.hardcoredungeons.data

import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.advancements.criterion.InventoryChangeTrigger
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.block.*
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
import thedarkcolour.hardcoredungeons.compat.getBiomesOPlentyCompat
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.registry.HItemsNew
import thedarkcolour.hardcoredungeons.tags.HItemTags
import thedarkcolour.hardcoredungeons.util.modLoc
import java.util.function.Consumer
import java.util.function.Supplier

class RecipeGenerator(generatorIn: DataGenerator) : RecipeProvider(generatorIn) {
    /**
     * Registers all recipes to the given consumer.
     *
     * Uses several extension functions declared later on in the file.
     */
    override fun buildShapelessRecipes(consumer: Consumer<IFinishedRecipe>) {
        // lumlight blocks
        HBlocks.LUMLIGHT_WOOD.addRecipes(consumer)
        HBlocks.AURI_WOOD.addRecipes(consumer)
        HBlocks.COTTONMARSH_WOOD.addRecipes(consumer)

        consumer.shaped(HBlocks.LUMLIGHT_CAMPFIRE, 1) { builder ->
            builder.pattern(" s ")
            builder.pattern("sms")
            builder.pattern("lll")
            builder.define('s', Tags.Items.RODS_WOODEN)
            builder.define('m', HItemTags.LUMSHROOM)
            builder.define('l', HItemTags.LUMLIGHT_LOGS)
            builder.unlockedBy("has_item", has(HBlocks.LUMLIGHT_WOOD.log))
        }

        // shroomy cobblestone + stone brick
        consumer.shapeless(HBlocks.SHROOMY_COBBLESTONE.block, 1) { builder ->
            builder.requires(Tags.Items.COBBLESTONE)
            builder.requires(Tags.Items.MUSHROOMS)
            builder.unlockedBy("has_mushroom", RecipeProvider.has(Tags.Items.MUSHROOMS))
        }
        consumer.slab(HBlocks.SHROOMY_COBBLESTONE.slab, HBlocks.SHROOMY_COBBLESTONE.block)
        consumer.stairs(HBlocks.SHROOMY_COBBLESTONE.stairs, HBlocks.SHROOMY_COBBLESTONE.block)
        consumer.shapeless(HBlocks.SHROOMY_STONE_BRICKS.block, 1) { builder ->
            builder.requires(ItemTags.STONE_BRICKS)
            builder.requires(Tags.Items.MUSHROOMS)
            builder.unlockedBy("has_mushroom", has(Tags.Items.MUSHROOMS))
        }
        consumer.slab(HBlocks.SHROOMY_STONE_BRICKS.slab, HBlocks.SHROOMY_STONE_BRICKS.block)
        consumer.stairs(HBlocks.SHROOMY_STONE_BRICKS.stairs, HBlocks.SHROOMY_STONE_BRICKS.block)
        consumer.shaped(HItems.SYRINGE, 2) { builder ->
            builder.define('B', Items.GLASS_BOTTLE)
            builder.define('I', Items.IRON_INGOT)
            builder.define('N', Items.IRON_NUGGET)
            builder.pattern("IBI")
            builder.pattern(" I ")
            builder.pattern(" N ")
            builder.unlockedBy("has_item", has(Items.GLASS_BOTTLE))
        }


        // ammunition
        consumer.shaped(HItems.BULLET, 8) { builder ->
            builder.define('I', Items.IRON_NUGGET)
            builder.define('G', Items.GUNPOWDER)
            builder.pattern("III")
            builder.pattern("IGI")
            builder.pattern("III")
            builder.unlockedBy("has_item", has(Items.GUNPOWDER))
        }
        consumer.shaped(HItems.INCENDIARY_BULLET, 8) { builder ->
            builder.define('I', HItems.BULLET)
            builder.define('G', Items.BLAZE_POWDER)
            builder.pattern("III")
            builder.pattern("IGI")
            builder.pattern("III")
            builder.unlockedBy("has_item", has(Items.GUNPOWDER))
        }


        // tools
        consumer.sword(HItemsNew.MALACHITE_SWORD, HBlocks.MALACHITE_CRYSTAL.item)
        consumer.shovel(HItems.MALACHITE_SHOVEL, HBlocks.MALACHITE_CRYSTAL.item)
        consumer.pickaxe(HItems.MALACHITE_PICKAXE, HBlocks.MALACHITE_CRYSTAL.item)
        consumer.axe(HItems.MALACHITE_AXE, HBlocks.MALACHITE_CRYSTAL.item)
        consumer.hoe(HItems.MALACHITE_HOE, HBlocks.MALACHITE_CRYSTAL.item)

        consumer.sword(HItems.RAINBOWSTONE_SWORD, HItems.RAINBOWSTONE_GEM)
        consumer.shovel(HItems.RAINBOWSTONE_SHOVEL, HItems.RAINBOWSTONE_GEM)
        consumer.pickaxe(HItems.RAINBOWSTONE_PICKAXE, HItems.RAINBOWSTONE_GEM)
        consumer.axe(HItems.RAINBOWSTONE_AXE, HItems.RAINBOWSTONE_GEM)
        consumer.hoe(HItems.RAINBOWSTONE_HOE, HItems.RAINBOWSTONE_GEM)


        // stonecutter recipes for HCD blocks
        consumer.stonecutterRecipes(HBlocks.SHROOMY_COBBLESTONE.block, HBlocks.SHROOMY_COBBLESTONE.stairs, HBlocks.SHROOMY_COBBLESTONE.wall, slab = HBlocks.SHROOMY_COBBLESTONE.slab)
        consumer.stonecutterRecipes(HBlocks.SHROOMY_STONE_BRICKS.block, HBlocks.SHROOMY_STONE_BRICKS.stairs, HBlocks.SHROOMY_STONE_BRICKS.wall, slab = HBlocks.SHROOMY_STONE_BRICKS.slab)
        consumer.stonecutterRecipes(HBlocks.CHOCOLATE_BLOCK.block, HBlocks.CHOCOLATE_BLOCK.stairs, slab = HBlocks.CHOCOLATE_BLOCK.slab)
        //consumer.stonecutterRecipes(HBlocks.CHARGED_CASTLETON_BRICKS.block, HBlocks.CHARGED_CASTLETON_BRICKS.stairs, HBlocks.CHARGED_CASTLETON_BRICKS.fence, HBlocks.CHARGED_CASTLETON_BRICKS.wall, slab = HBlocks.CHARGED_CASTLETON_BRICKS.slab)
        //consumer.stonecutterRecipes(HBlocksNew.RAINBOW_BRICKS, HBlocksNew.RAINBOW_BRICK_STAIRS, HBlocksNew.RAINBOW_BRICK_WALL, HBlocksNew.RAINBOW_BRICK_FENCE, slab = HBlocksNew.RAINBOW_BRICK_SLAB)
        //consumer.stonecutterRecipes(HBlocksNew.RAINBOW_ROCK, HBlocksNew.RAINBOW_BRICKS, HBlocksNew.RAINBOW_BRICK_STAIRS, HBlocksNew.RAINBOW_BRICK_WALL, HBlocksNew.RAINBOW_BRICK_FENCE, slab = HBlocksNew.RAINBOW_BRICK_SLAB)

        HBlocks.CASTLETON_STONE.addRecipes(consumer)
        HBlocks.RAINBOW_ROCK.addRecipes(consumer)
        HBlocks.CHARGED_CASTLETON_BRICKS.addRecipes(consumer)
        HBlocks.RAINBOW_FACTORY_BRICKS.addRecipes(consumer)

        consumer.stonecutterRecipes(Blocks.DIAMOND_BLOCK, HBlocks.CHISELED_DIAMOND_BLOCK)
        consumer.stonecutterRecipes(Blocks.SPRUCE_PLANKS, *HBlocks.SPRUCE_PLANKS.variants.map(Supplier<out Block>::get).toTypedArray())


        // food
        consumer.smeltingRecipe(HItems.VENISON, HItems.COOKED_VENISON, 0.35f, smokingRecipe = true, campfireRecipe = true)
        consumer.smeltingRecipe(HBlocks.RAINBOWSTONE_ORE, HItems.RAINBOWSTONE_GEM, 0.85f, blastingRecipe = true)
        //consumer.campfireRecipe(HItems.VENISON, HItems.COOKED_VENISON, 0.35f)

        // compressed (storage) block recipes
        HBlocks.COMPRESSED_COBBLESTONE.addRecipes(consumer)

        consumer.storage(HBlocks.CHOCOLATE_BLOCK.block, Items.COCOA_BEANS)
        consumer.slab(HBlocks.CHOCOLATE_BLOCK.slab, HBlocks.CHOCOLATE_BLOCK.block)
        consumer.stairs(HBlocks.CHOCOLATE_BLOCK.stairs, HBlocks.CHOCOLATE_BLOCK.block)

        consumer.storage(HBlocks.MALACHITE_BLOCK, HBlocks.MALACHITE_CRYSTAL.item, HItemTags.GEMS_MALACHITE)
        consumer.storage(HBlocks.RAINBOWSTONE_BLOCK, HItems.RAINBOWSTONE_GEM)

        getBiomesOPlentyCompat()?.genRecipes(consumer)
    }

     companion object {
         fun path(entry: IForgeRegistryEntry<*>): String {
             return entry.registryName!!.path
         }

         fun has(item: IItemProvider): InventoryChangeTrigger.Instance {
             return inventoryTrigger(ItemPredicate.Builder.item().of(item).build())
         }

         fun has(tag: ITag<Item>): InventoryChangeTrigger.Instance {
             return inventoryTrigger(ItemPredicate.Builder.item().of(tag).build())
         }

         private fun inventoryTrigger(vararg predicates: ItemPredicate): InventoryChangeTrigger.Instance {
             return InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, predicates)
         }

         fun ShapedRecipeBuilder.lines(first: String, second: String? = null, third: String? = null) {
             pattern(first)
             pattern(second ?: return)
             pattern(third ?: return)
         }

         private fun CookingRecipeBuilder.saveNullable(consumer: Consumer<IFinishedRecipe>, recipeName: String?) {
             if (recipeName != null) {
                 save(consumer, recipeName)
             } else {
                 save(consumer)
             }
         }

         // 3x3 storage recipe (to and from)
         private fun Consumer<IFinishedRecipe>.storage(storage: Block, regular: Item, regularTag: ITag<Item>? = null) {
             val item = regular.asItem()
             // compress
             shaped(storage, 1) { builder ->
                 if (regularTag != null) {
                     builder.define('F', regularTag)
                 } else {
                     builder.define('F', item)
                 }
                 builder.lines("FFF", "FFF", "FFF")
                 builder.unlockedBy("has_item", if (regularTag != null) has(regularTag) else has(item))
             }
             // decompress
             shapeless(item, 9, id = modLoc(path(item) + "_from_" + path(storage))) { builder ->
                 builder.requires(storage)
                 builder.unlockedBy("has_item", has(storage))
             }
         }

         fun Consumer<IFinishedRecipe>.slabs2Full(slab: Block, full: Block, group: String? = null) {
             shaped(full, 1, modLoc(path(full) + "_from_slabs")) { builder ->
                 builder.define('#', slab)
                 if (group != null) builder.group(group)
                 builder.lines("#", "#")
                 builder.unlockedBy("has_item", has(slab))
             }
         }

         private fun Consumer<IFinishedRecipe>.campfireRecipe(input: IItemProvider, output: IItemProvider, experience: Float, duration: Int = 600) {
             val inputItem = input.asItem()
             val outputItem = output.asItem()

             CookingRecipeBuilder.cooking(Ingredient.of(inputItem), outputItem, experience, duration, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_item", has(inputItem)).save(this, path(outputItem) + "_from_campfire")
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
             CookingRecipeBuilder.smelting(Ingredient.of(input), output, experience, 200).unlockedBy("has_item", has(input)).saveNullable(this, recipeName)

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

             CookingRecipeBuilder.blasting(Ingredient.of(item), output, experience, 100).unlockedBy("has_item", has(input)).save(this, recipeName ?: path(item) + "_from_blasting")
         }

         private fun Consumer<IFinishedRecipe>.smokingRecipe(
             input: IItemProvider,
             output: IItemProvider,
             experience: Float,
             recipeName: String? = null,
         ) {
             val item = input.asItem()

             CookingRecipeBuilder.cooking(Ingredient.of(item), output, experience, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_item", has(item)).save(this, recipeName ?: path(item) + "_from_smoking")
         }

         fun Consumer<IFinishedRecipe>.stonecutterRecipes(
             base: IItemProvider,
             vararg others: IItemProvider,
             slab: IItemProvider? = null,
         ) {
             for (other in others) {
                 try {
                     stonecutting(base, other)
                 } catch (e: Exception) {
                     println("Error stonecutting recipe: $base")
                     e.printStackTrace()
                     return
                 }
             }

             stonecutting(base, slab ?: return, 2)
         }

         fun Consumer<IFinishedRecipe>.stonecutting(
             input: IItemProvider,
             output: IItemProvider,
             outputCount: Int = 1,
         ) = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output, outputCount)
             .unlocks("has_item", has(input))
             .save(this, path(output.asItem()) + "_from_" + path(input.asItem()) + "_stonecutting")

         private fun Consumer<IFinishedRecipe>.pickaxe(
             result: IItemProvider,
             material: IItemProvider,
             rod: IItemProvider = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("###", " I ", " I ")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         private fun Consumer<IFinishedRecipe>.shovel(
             result: IItemProvider,
             material: IItemProvider,
             rod: IItemProvider = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("#", "I", "I")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         private fun Consumer<IFinishedRecipe>.axe(
             result: IItemProvider,
             material: IItemProvider,
             rod: IItemProvider = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("##", "#I", " I")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         private fun Consumer<IFinishedRecipe>.hoe(
             result: IItemProvider,
             material: IItemProvider,
             rod: IItemProvider = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("##", " I", " I")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         private fun Consumer<IFinishedRecipe>.sword(
             result: IItemProvider,
             material: IItemProvider,
             rod: Item = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("#", "#", "I")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         fun Consumer<IFinishedRecipe>.shapeless(
             result: IItemProvider,
             resultCount: Int,
             id: ResourceLocation = result.asItem().registryName!!,
             addIngredients: (ShapelessRecipeBuilder) -> Unit,
         ) {
             val builder = ShapelessRecipeBuilder(result, resultCount)
             addIngredients(builder)

             builder.save(this, id)
         }

         fun Consumer<IFinishedRecipe>.shaped(
             result: IItemProvider,
             resultCount: Int,
             id: ResourceLocation = result.asItem().registryName!!,
             addIngredients: (ShapedRecipeBuilder) -> Unit,
         ) {
             val builder = ShapedRecipeBuilder(result, resultCount)
             addIngredients(builder)

             builder.save(this, id)
         }

         fun Consumer<IFinishedRecipe>.slab(
             slab: SlabBlock,
             block: Block,
             group: String? = null,
         ) {
             // slab recipe
             shaped(slab, 6) { builder ->
                 builder.define('#', block)
                 if (group != null) builder.group(group)
                 builder.pattern("###")
                 builder.unlockedBy("has_block", has(block))
             }
             // convert from slab to full block
             slabs2Full(slab, block)
         }

         fun Consumer<IFinishedRecipe>.stairs(
             stairs: StairsBlock,
             block: Block,
             group: String? = null,
         ) {
             shaped(stairs, 4) { builder ->
                 builder.define('#', block)
                 if (group != null) builder.group(group)
                 builder.lines("#  ", "## ", "###")
                 builder.unlockedBy("has_block", has(block))
             }
         }

         fun Consumer<IFinishedRecipe>.wall(wall: WallBlock, block: Block) {
             shaped(wall, 6) { builder ->
                 builder.define('#', block)
                 builder.pattern("###")
                 builder.pattern("###")
                 builder.unlockedBy("has_block", has(block))
             }
         }

         fun Consumer<IFinishedRecipe>.twoByTwo(
             result: IItemProvider,
             resultCount: Int,
             from: IItemProvider,
             trigger: String,
             id: ResourceLocation = result.asItem().registryName!!,
             group: String? = null,
         ) {
             shaped(result, resultCount, id) { builder ->
                 builder.pattern("xx")
                 builder.pattern("xx")
                 builder.define('x', from)
                 if (group != null) builder.group(group)
                 builder.unlockedBy(trigger, has(from))
             }
         }
     }
}