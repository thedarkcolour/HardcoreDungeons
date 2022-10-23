package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.block.HGrassBlock
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.LootGenerator

/**
 * Soil, Grass, Loam
 *
 * @param prefix Underscores must be included in the prefix
 * @param tag The plantable blocks tag
 */
class GroundCombo(prefix: String, nocturnal: Boolean, tag: TagKey<Block>, soilProps: HProperties, grassProps: HProperties) : ICombo {
    val soil by BlockMaker.cubeAllWithItem(prefix + "soil", soilProps)
    val loam by BlockMaker.cubeAllWithItem(prefix + "loam", soilProps)
    val grass by BlockMaker.blockWithItem(prefix + "grass_block") {
        HGrassBlock({ soil.defaultBlockState() }, nocturnal, tag, grassProps.build())
    }

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.DIRT, soil, grass)
    }

    override fun addLoot(gen: LootGenerator) {
        gen.dropSilk(block = grass, normal = gen.item(soil), silk = gen.item(grass))
    }
}