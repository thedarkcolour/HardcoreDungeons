package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.BlockTags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator

open class StairsSlabCombo(name: String, properties: HProperties) : ICombo {
    val block by BlockMaker.cubeAllWithItem(name, properties)
    val slab by BlockMaker.slabWithItem(BlockMaker.getComboName(name) + "_slab", { block }, properties)
    val stairs by BlockMaker.stairsWithItem(BlockMaker.getComboName(name) + "_stairs", { block }, properties)

    override fun addTags(gen: BlockTagGenerator) {
        gen.tag(BlockTags.SLABS).add(slab)
        gen.tag(BlockTags.STAIRS).add(stairs)
    }
}