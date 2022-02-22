package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.Block
import net.minecraft.tags.ITag
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.block.misc.HGrassBlock
import thedarkcolour.hardcoredungeons.data.LootGenerator

/**
 * Soil, Grass, Loam
 *
 * @param prefix Underscores must be included in the prefix
 * @param tag The plantable blocks tag
 */
class SoilCombo(prefix: String, nocturnal: Boolean, tag: ITag<Block>, soilProps: HProperties, grassProps: HProperties) : ICombo {
    val soil by BlockMaker.cubeAllWithItem(prefix + "soil", soilProps)
    val loam by BlockMaker.cubeAllWithItem(prefix + "loam", soilProps)
    val grass by BlockMaker.blockWithItem(prefix + "grass_block") {
        HGrassBlock({ soil.defaultBlockState() }, nocturnal, tag, grassProps.build())
    }

    override fun addTags(tags: DataTags) {
        tags.block(Tags.Blocks.DIRT, soil, grass)
    }

    override fun addLoot(gen: LootGenerator) {
        gen.dropSilk(block = grass, normal = gen.item(soil), silk = gen.item(grass))
    }
}