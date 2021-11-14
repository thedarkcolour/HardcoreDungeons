package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.world.level.block.Block
import net.minecraft.item.Food
import net.minecraft.tags.BlockTags
import thedarkcolour.hardcoredungeons.block.HBlocks
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator

class CropsCombo(name: String, food: Food, cropSupplier: () -> Block) : ICombo {
    val crop by HBlocks.register(name, cropSupplier)
    val item by ItemMaker.foodBlockItem(name, ::crop, food)

    override fun addTags(gen: BlockTagGenerator) {
        gen.tag(BlockTags.CROPS).add(crop)
        gen.tag(BlockTags.BEE_GROWABLES).add(crop)
    }
}