package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.world.level.block.Block
import net.minecraft.tags.ITag
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.block.base.HBlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.block.misc.GrassBlock
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.LootGenerator

/**
 * Soil, Grass, Loam
 *
 * @param prefix Underscores must be included in the prefix
 * @param tag The plantable blocks tag
 */
class SoilCombo(prefix: String, nocturnal: Boolean, tag: ITag<Block>, soilProps: HProperties, grassProps: HProperties) : ICombo {
    val soil by HBlockMaker.cubeAllWithItem(prefix + "soil", soilProps)
    val loam by HBlockMaker.cubeAllWithItem(prefix + "loam", soilProps)
    val grass by HBlockMaker.blockWithItem(prefix + "grass_block") {
        GrassBlock({ soil.defaultBlockState() }, nocturnal, tag, grassProps.build())
    }

    override fun addTags(gen: BlockTagGenerator) {
        // todo add more tags
        gen.tag(Tags.Blocks.DIRT).add(soil, grass)
    }

    override fun addLoot(gen: LootGenerator) {
        gen.dropSilk(block = grass, normal = gen.item(soil), silk = gen.item(grass))
    }
}