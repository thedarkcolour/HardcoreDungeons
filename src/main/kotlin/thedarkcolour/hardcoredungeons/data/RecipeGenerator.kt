package thedarkcolour.hardcoredungeons.data

import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.data.*
import net.minecraft.data.recipes.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.*
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.*
import thedarkcolour.hardcoredungeons.tags.HItemTags
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.hardcoredungeons.util.registryName
import java.util.function.Consumer
import java.util.function.Supplier

class RecipeGenerator(generatorIn: DataGenerator) : RecipeProvider(generatorIn) {
    /**
     * Registers all recipes to the given consumer.
     *
     * Uses several extension functions declared later on in the file.
     */
    override fun buildCraftingRecipes(consumer: Consumer<FinishedRecipe>) {
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
        consumer.shapeless(HBlocks.SHROOMY_COBBLESTONE.block, 8) { builder ->
            for (i in 0..7) {
                builder.requires(Tags.Items.COBBLESTONE)
            }
            builder.requires(Tags.Items.MUSHROOMS)
            builder.unlockedBy("has_mushroom", RecipeProvider.has(Tags.Items.MUSHROOMS))
        }
        consumer.slab(HBlocks.SHROOMY_COBBLESTONE.slab, HBlocks.SHROOMY_COBBLESTONE.block)
        consumer.stairs(HBlocks.SHROOMY_COBBLESTONE.stairs, HBlocks.SHROOMY_COBBLESTONE.block)
        consumer.shapeless(HBlocks.SHROOMY_STONE_BRICKS.block, 8) { builder ->
            for (i in 0..7) {
                builder.requires(ItemTags.STONE_BRICKS)
            }
            builder.requires(Tags.Items.MUSHROOMS)
            builder.unlockedBy("has_mushroom", has(Tags.Items.MUSHROOMS))
        }
        consumer.slab(HBlocks.SHROOMY_STONE_BRICKS.slab, HBlocks.SHROOMY_STONE_BRICKS.block)
        consumer.stairs(HBlocks.SHROOMY_STONE_BRICKS.stairs, HBlocks.SHROOMY_STONE_BRICKS.block)
        consumer.shaped(SYRINGE_ITEM, 2) { builder ->
            builder.define('B', Items.GLASS_BOTTLE)
            builder.define('I', Items.IRON_INGOT)
            builder.define('N', Items.IRON_NUGGET)
            builder.pattern("IBI")
            builder.pattern(" I ")
            builder.pattern(" N ")
            builder.unlockedBy("has_item", has(Items.GLASS_BOTTLE))
        }


        // ammunition
        consumer.shaped(BULLET_ITEM, 8) { builder ->
            builder.define('I', Items.IRON_NUGGET)
            builder.define('G', Items.GUNPOWDER)
            builder.pattern("III")
            builder.pattern("IGI")
            builder.pattern("III")
            builder.unlockedBy("has_item", has(Items.GUNPOWDER))
        }
        consumer.shaped(INCENDIARY_BULLET_ITEM, 8) { builder ->
            builder.define('I', BULLET_ITEM)
            builder.define('G', Items.BLAZE_POWDER)
            builder.pattern("III")
            builder.pattern("IGI")
            builder.pattern("III")
            builder.unlockedBy("has_item", has(Items.GUNPOWDER))
        }


        // tools
        consumer.sword(MALACHITE_SWORD_ITEM, HBlocks.MALACHITE_CRYSTAL.item)
        consumer.shovel(MALACHITE_SHOVEL_ITEM, HBlocks.MALACHITE_CRYSTAL.item)
        consumer.pickaxe(MALACHITE_PICKAXE_ITEM, HBlocks.MALACHITE_CRYSTAL.item)
        consumer.axe(MALACHITE_AXE_ITEM, HBlocks.MALACHITE_CRYSTAL.item)
        consumer.hoe(MALACHITE_HOE_ITEM, HBlocks.MALACHITE_CRYSTAL.item)

        consumer.sword(RAINBOWSTONE_SWORD_ITEM, RAINBOWSTONE_GEM_ITEM)
        consumer.shovel(RAINBOWSTONE_SHOVEL_ITEM, RAINBOWSTONE_GEM_ITEM)
        consumer.pickaxe(RAINBOWSTONE_PICKAXE_ITEM, RAINBOWSTONE_GEM_ITEM)
        consumer.axe(RAINBOWSTONE_AXE_ITEM, RAINBOWSTONE_GEM_ITEM)
        consumer.hoe(RAINBOWSTONE_HOE_ITEM, RAINBOWSTONE_GEM_ITEM)


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

        consumer.stonecutterRecipes(Blocks.SPRUCE_PLANKS, *HBlocks.SPRUCE_PLANKS.variants.map(Supplier<out Block>::get).toTypedArray())


        // food
        consumer.smeltingRecipe(VENISON_ITEM, COOKED_VENISON_ITEM, 0.35f, smokingRecipe = true, campfireRecipe = true)
        consumer.smeltingRecipe(HBlocks.RAINBOWSTONE_ORE, RAINBOWSTONE_GEM_ITEM, 0.85f, blastingRecipe = true)
        //consumer.campfireRecipe(VENISON, COOKED_VENISON, 0.35f)

        // compressed (storage) block recipes
        //HBlocks.COMPRESSED_COBBLESTONE.addRecipes(consumer)

        consumer.storage(HBlocks.CHOCOLATE_BLOCK.block, Items.COCOA_BEANS)
        consumer.slab(HBlocks.CHOCOLATE_BLOCK.slab, HBlocks.CHOCOLATE_BLOCK.block)
        consumer.stairs(HBlocks.CHOCOLATE_BLOCK.stairs, HBlocks.CHOCOLATE_BLOCK.block)

        consumer.storage(HBlocks.MALACHITE_BLOCK, HBlocks.MALACHITE_CRYSTAL.item, HItemTags.GEMS_MALACHITE)
        consumer.storage(HBlocks.RAINBOWSTONE_BLOCK, RAINBOWSTONE_GEM_ITEM)
    }

     companion object {
         fun path(entry: ItemLike): String {
             val item = entry.asItem()
             return ForgeRegistries.ITEMS.getKey(item)!!.path
         }

         fun has(item: ItemLike): InventoryChangeTrigger.TriggerInstance {
             return inventoryTrigger(ItemPredicate.Builder.item().of(item).build())
         }

         fun has(tag: TagKey<Item>): InventoryChangeTrigger.TriggerInstance {
             return inventoryTrigger(ItemPredicate.Builder.item().of(tag).build())
         }

         private fun inventoryTrigger(vararg predicates: ItemPredicate): InventoryChangeTrigger.TriggerInstance {
             return InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, predicates)
         }

         fun ShapedRecipeBuilder.lines(first: String, second: String? = null, third: String? = null) {
             pattern(first)
             pattern(second ?: return)
             pattern(third ?: return)
         }


         // Don't have to call two different methods if the recipeName is null
         private fun RecipeBuilder.saveN(consumer: Consumer<FinishedRecipe>, recipeName: String?) {
             if (recipeName != null) {
                 save(consumer, recipeName)
             } else {
                 save(consumer)
             }
         }

         // 3x3 storage recipe (to and from)
         private fun Consumer<FinishedRecipe>.storage(storage: Block, regular: Item, regularTag: TagKey<Item>? = null) {
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

         private fun Consumer<FinishedRecipe>.slabs2Full(slab: Block, full: Block, group: String? = null) {
             shaped(full, 1, modLoc(path(full) + "_from_slabs")) { builder ->
                 builder.define('#', slab)
                 if (group != null) builder.group(group)
                 builder.lines("#", "#")
                 builder.unlockedBy("has_item", has(slab))
             }
         }

         private fun Consumer<FinishedRecipe>.campfireRecipe(input: ItemLike, output: ItemLike, experience: Float, duration: Int = 600) {
             val inputItem = input.asItem()
             val outputItem = output.asItem()

             SimpleCookingRecipeBuilder.cooking(Ingredient.of(inputItem), outputItem, experience, duration, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_item", has(inputItem)).save(this, path(outputItem) + "_from_campfire")
         }

         private fun Consumer<FinishedRecipe>.smeltingRecipe(
             input: ItemLike,
             output: ItemLike,
             experience: Float,
             recipeName: String? = null,
             smokingRecipe: Boolean = false,
             campfireRecipe: Boolean = false,
             blastingRecipe: Boolean = false,
         ) {
             SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), output, experience, 200).unlockedBy("has_item", has(input)).saveN(this, recipeName)

             if (smokingRecipe)  smokingRecipe (input, output, experience)
             if (campfireRecipe) campfireRecipe(input, output, experience)
             if (blastingRecipe) blastingRecipe(input, output, experience)
         }

         private fun Consumer<FinishedRecipe>.blastingRecipe(
             input: ItemLike,
             output: ItemLike,
             experience: Float,
             recipeName: String? = null,
         ) {
             val item = input.asItem()

             SimpleCookingRecipeBuilder.blasting(Ingredient.of(item), output, experience, 100).unlockedBy("has_item", has(input)).save(this, recipeName ?: (path(item) + "_from_blasting"))
         }

         private fun Consumer<FinishedRecipe>.smokingRecipe(
             input: ItemLike,
             output: ItemLike,
             experience: Float,
             recipeName: String? = null,
         ) {
             val item = input.asItem()

             SimpleCookingRecipeBuilder.cooking(Ingredient.of(item), output, experience, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_item", has(item)).save(this, recipeName ?: (path(item) + "_from_smoking"))
         }

         fun Consumer<FinishedRecipe>.stonecutterRecipes(
             base: ItemLike,
             vararg others: ItemLike,
             slab: ItemLike? = null,
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

         fun Consumer<FinishedRecipe>.stonecutting(
             input: ItemLike,
             output: ItemLike,
             outputCount: Int = 1,
         ) = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output, outputCount)
             .unlockedBy("has_item", has(input))
             .save(this, modLoc(path(output.asItem()) + "_from_" + path(input.asItem()) + "_stonecutting"))

         private fun Consumer<FinishedRecipe>.pickaxe(
             result: ItemLike,
             material: ItemLike,
             rod: ItemLike = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("###", " I ", " I ")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         private fun Consumer<FinishedRecipe>.shovel(
             result: ItemLike,
             material: ItemLike,
             rod: ItemLike = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("#", "I", "I")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         private fun Consumer<FinishedRecipe>.axe(
             result: ItemLike,
             material: ItemLike,
             rod: ItemLike = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("##", "#I", " I")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         private fun Consumer<FinishedRecipe>.hoe(
             result: ItemLike,
             material: ItemLike,
             rod: ItemLike = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("##", " I", " I")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         private fun Consumer<FinishedRecipe>.sword(
             result: ItemLike,
             material: ItemLike,
             rod: Item = Items.STICK,
         ) {
             shaped(result, 1) { builder ->
                 builder.define('I', rod)
                 builder.define('#', material)
                 builder.lines("#", "#", "I")
                 builder.unlockedBy("has_item", has(material))
             }
         }

         fun Consumer<FinishedRecipe>.shapeless(
             result: ItemLike,
             resultCount: Int,
             id: ResourceLocation = result.asItem().registryName!!,
             addIngredients: (ShapelessRecipeBuilder) -> Unit,
         ) {
             val builder = ShapelessRecipeBuilder(result, resultCount)
             addIngredients(builder)

             builder.save(this, id)
         }

         fun Consumer<FinishedRecipe>.shaped(
             result: ItemLike,
             resultCount: Int,
             id: ResourceLocation = result.asItem().registryName!!,
             addIngredients: (ShapedRecipeBuilder) -> Unit,
         ) {
             val builder = ShapedRecipeBuilder(result, resultCount)
             addIngredients(builder)

             builder.save(this, id)
         }

         fun Consumer<FinishedRecipe>.slab(
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

         fun Consumer<FinishedRecipe>.stairs(
             stairs: StairBlock,
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

         fun Consumer<FinishedRecipe>.wall(wall: WallBlock, block: Block) {
             shaped(wall, 6) { builder ->
                 builder.define('#', block)
                 builder.pattern("###")
                 builder.pattern("###")
                 builder.unlockedBy("has_block", has(block))
             }
         }

         fun Consumer<FinishedRecipe>.twoByTwo(
             result: ItemLike,
             resultCount: Int,
             from: ItemLike,
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