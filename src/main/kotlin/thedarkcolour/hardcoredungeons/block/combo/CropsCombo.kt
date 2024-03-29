package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.BlockTags
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker

class CropsCombo(name: String, food: FoodProperties, cropSupplier: () -> Block) : BlockCombo(), ItemLike {
    val crop by HBlocks.register(name, cropSupplier)
    val item by ItemMaker.foodBlockItem(name, ::crop, food)

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.CROPS, crop)
        tags.block(BlockTags.BEE_GROWABLES, crop)
    }

    override fun asItem() = item
}