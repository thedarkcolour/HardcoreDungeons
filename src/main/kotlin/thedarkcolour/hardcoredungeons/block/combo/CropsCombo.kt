package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.world.level.block.Block
import net.minecraft.tags.BlockTags
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.level.ItemLike
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.registry.HBlocks

class CropsCombo(name: String, food: FoodProperties, cropSupplier: () -> Block) : ICombo, ItemLike {
    val crop by HBlocks.register(name, cropSupplier)
    val item by ItemMaker.foodBlockItem(name, ::crop, food)

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.CROPS, crop)
        tags.block(BlockTags.BEE_GROWABLES, crop)
    }

    override fun asItem() = item
}