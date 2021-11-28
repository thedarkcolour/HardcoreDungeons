package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.BlockTags
import thedarkcolour.hardcoredungeons.block.base.HBlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator

open class StairsSlabWallCombo(name: String, properties: HProperties) : StairsSlabCombo(name, properties) {
    val wall by HBlockMaker.wallWithItem(HBlockMaker.getComboName(name) + "_wall", ::block)

    override fun addTags(gen: BlockTagGenerator) {
        super.addTags(gen)

        gen.tag(BlockTags.WALLS).add(wall)
    }
}