package thedarkcolour.hardcoredungeons.data

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.WallBlock
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.block.combo.BlockCombo
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.*
import thedarkcolour.hardcoredungeons.tags.HItemTags
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.modkit.data.MKRecipeProvider
import java.util.function.Consumer

fun MKRecipeProvider.addRecipes(writer: Consumer<FinishedRecipe>) {
    for (combo in BlockCombo.ALL_COMBOS) {
        combo.addRecipes(writer, this)
    }

    shapedCrafting(RecipeCategory.MISC, HBlocks.LUMLIGHT_CAMPFIRE, 1) { builder ->
        builder.pattern(" s ")
        builder.pattern("sms")
        builder.pattern("lll")
        builder.define('s', Tags.Items.RODS_WOODEN)
        builder.define('m', HItemTags.LUMSHROOM)
        builder.define('l', HItemTags.LUMLIGHT_LOGS)
    }

    // note the * on the array, to pass the array's elements into varargs instead of the single array object
    shapelessCrafting(RecipeCategory.BUILDING_BLOCKS, HBlocks.SHROOMY_COBBLESTONE.block, 8, *Array(9) { i -> if (i == 0) Tags.Items.MUSHROOMS else Tags.Items.COBBLESTONE})
    shapelessCrafting(RecipeCategory.BUILDING_BLOCKS, HBlocks.SHROOMY_COBBLESTONE.block, 8, *Array(9) { i -> if (i == 0) Tags.Items.MUSHROOMS else ItemTags.STONE_BRICKS})

    shapedCrafting(RecipeCategory.COMBAT, BULLET_ITEM, 8) { builder ->
        builder.define('I', Items.IRON_NUGGET)
        builder.define('G', Items.GUNPOWDER)
        builder.pattern("III")
        builder.pattern("IGI")
        builder.pattern("III")
    }
    shapedCrafting(RecipeCategory.COMBAT, INCENDIARY_BULLET_ITEM, 8) { builder ->
        builder.define('I', BULLET_ITEM)
        builder.define('G', Items.BLAZE_POWDER)
        builder.pattern("III")
        builder.pattern("IGI")
        builder.pattern("III")
    }

    foodCooking(VENISON_ITEM, COOKED_VENISON_ITEM, 0.35f)
    // todo some oreSmelting function that does both of these in one?
    smelting(HBlocks.RAINBOWSTONE_ORE, RAINBOWSTONE_GEM_ITEM, 0.85f)
    blasting(HBlocks.RAINBOWSTONE_ORE, RAINBOWSTONE_GEM_ITEM, 0.85f)

    smelting(HBlocks.CASTLETON_STONE.stone.block, HBlocks.CRACKED_CASTLETON_BRICKS, 0.1f)

    storage3x3(HBlocks.CHOCOLATE_BLOCK.block, Items.COCOA_BEANS)
    storage3x3(HBlocks.MALACHITE_BLOCK, HBlocks.MALACHITE_CRYSTAL.shard)
    storage3x3(HBlocks.RAINBOWSTONE_BLOCK, RAINBOWSTONE_GEM_ITEM)

    // tools
    sword(MALACHITE_SWORD_ITEM, HBlocks.MALACHITE_CRYSTAL.shard)
    shovel(MALACHITE_SHOVEL_ITEM, HBlocks.MALACHITE_CRYSTAL.shard)
    pickaxe(MALACHITE_PICKAXE_ITEM, HBlocks.MALACHITE_CRYSTAL.shard)
    axe(MALACHITE_AXE_ITEM, HBlocks.MALACHITE_CRYSTAL.shard)
    hoe(MALACHITE_HOE_ITEM, HBlocks.MALACHITE_CRYSTAL.shard)

    sword(RAINBOWSTONE_SWORD_ITEM, RAINBOWSTONE_GEM_ITEM)
    shovel(RAINBOWSTONE_SHOVEL_ITEM, RAINBOWSTONE_GEM_ITEM)
    pickaxe(RAINBOWSTONE_PICKAXE_ITEM, RAINBOWSTONE_GEM_ITEM)
    axe(RAINBOWSTONE_AXE_ITEM, RAINBOWSTONE_GEM_ITEM)
    hoe(RAINBOWSTONE_HOE_ITEM, RAINBOWSTONE_GEM_ITEM)


    // stonecutter recipes for HCD blocks
    writer.stonecutterRecipes(HBlocks.CHOCOLATE_BLOCK.block, HBlocks.CHOCOLATE_BLOCK.stairs, slab = HBlocks.CHOCOLATE_BLOCK.slab)

}

fun path(entry: ItemLike): String {
    val item = entry.asItem()
    return ForgeRegistries.ITEMS.getKey(item)!!.path
}

fun Consumer<FinishedRecipe>.stonecutterRecipes(base: ItemLike, vararg others: ItemLike, slab: ItemLike? = null) {
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

fun Consumer<FinishedRecipe>.stonecutting(input: ItemLike, output: ItemLike, outputCount: Int = 1) {
    return MKRecipeProvider.unlockedByHaving(
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.MISC, output, outputCount), input
    ).save(this, modLoc(path(output.asItem()) + "_from_" + path(input.asItem()) + "_stonecutting"))
}

private fun MKRecipeProvider.pickaxe(result: ItemLike, material: ItemLike, rod: ItemLike = Items.STICK) {
    shapedCrafting(RecipeCategory.TOOLS, result, 1) { builder ->
        builder.define('I', rod)
        builder.define('#', material)
        builder.pattern("###")
        builder.pattern(" I ")
        builder.pattern(" I ")
    }
}

private fun MKRecipeProvider.shovel(result: ItemLike, material: ItemLike, rod: ItemLike = Items.STICK) {
    shapedCrafting(RecipeCategory.TOOLS, result, 1) { builder ->
        builder.define('I', rod)
        builder.define('#', material)
        builder.pattern("#")
        builder.pattern("I")
        builder.pattern("I")
    }
}

private fun MKRecipeProvider.axe(result: ItemLike, material: ItemLike, rod: ItemLike = Items.STICK) {
    shapedCrafting(RecipeCategory.TOOLS, result, 1) { builder ->
        builder.define('I', rod)
        builder.define('#', material)
        builder.pattern("##")
        builder.pattern("#I")
        builder.pattern(" I")
    }
}

private fun MKRecipeProvider.hoe(result: ItemLike, material: ItemLike, rod: ItemLike = Items.STICK) {
    shapedCrafting(RecipeCategory.TOOLS, result, 1) { builder ->
        builder.define('I', rod)
        builder.define('#', material)
        builder.pattern("##")
        builder.pattern(" I")
        builder.pattern(" I")
    }
}

private fun MKRecipeProvider.sword(result: ItemLike, material: ItemLike, rod: ItemLike = Items.STICK) {
    shapedCrafting(RecipeCategory.COMBAT, result, 1) { builder ->
        builder.define('I', rod)
        builder.define('#', material)
        builder.pattern("#")
        builder.pattern("#")
        builder.pattern("I")
    }
}

fun MKRecipeProvider.slab(slab: SlabBlock, block: Block, group: String? = null) {
    shapedCrafting(RecipeCategory.BUILDING_BLOCKS, slab, 6) { builder ->
        builder.define('#', block)
        if (group != null) builder.group(group)
        builder.pattern("###")
    }
}

fun MKRecipeProvider.stairs(stairs: StairBlock, block: Block, group: String? = null) {
    shapedCrafting(RecipeCategory.BUILDING_BLOCKS, stairs, 4) { builder ->
        builder.define('#', block)
        if (group != null) builder.group(group)
        builder.pattern("#  ")
        builder.pattern("## ")
        builder.pattern("###")
    }
}

fun MKRecipeProvider.wall(wall: WallBlock, block: Block) {
    shapedCrafting(RecipeCategory.BUILDING_BLOCKS, wall, 6) { builder ->
        builder.define('#', block)
        builder.pattern("###")
        builder.pattern("###")
    }
}
