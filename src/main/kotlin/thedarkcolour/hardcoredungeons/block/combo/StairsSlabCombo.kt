package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.BlockTags
import thedarkcolour.hardcoredungeons.block.base.HBlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator

open class StairsSlabCombo(name: String, properties: HProperties) : ICombo {
    val block by HBlockMaker.cubeAllWithItem(name, properties)
    val slab by HBlockMaker.slabWithItem(HBlockMaker.getComboName(name) + "_slab", { block }, properties)
    val stairs by HBlockMaker.stairsWithItem(HBlockMaker.getComboName(name) + "_stairs", { block }, properties)

    override fun addTags(gen: BlockTagGenerator) {
        gen.tag(BlockTags.SLABS).add(slab)
        gen.tag(BlockTags.STAIRS).add(stairs)
    }
}