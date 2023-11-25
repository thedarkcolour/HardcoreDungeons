package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.block.HGrassBlock
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockLoot
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

/**
 * Soil, Grass, Loam todo remove loam
 *
 * @param prefix Underscores must be included in the prefix
 * @param tag The plantable blocks tag
 */
class GroundCombo(
    prefix: String,
    nocturnal: Boolean,
    tag: TagKey<Block>,
    soilProps: HProperties,
    grassProps: HProperties,
    soilDelegate: (String, HProperties) -> ObjectHolderDelegate<out Block> = { name, props -> BlockMaker.cubeAllWithItem(name, props) }
) : BlockCombo() {
    val soil by soilDelegate(prefix + "soil", soilProps)
    // todo consider removing because of recent world gen changes
    val loam by BlockMaker.cubeAllWithItem(prefix + "loam", soilProps)
    val grass by BlockMaker.blockWithItem(prefix + "grass_block") {
        HGrassBlock({ soil.defaultBlockState() }, nocturnal, tag, grassProps.build())
    }

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.DIRT, soil, grass)
        tags.shovel(soil)
        tags.shovel(loam)
        tags.shovel(grass)
        tags.block(BlockTags.ANIMALS_SPAWNABLE_ON, grass)
    }

    override fun addLoot(gen: BlockLoot) {
        gen.dropSilk(block = grass, normal = gen.item(soil), silk = gen.item(grass))
    }
}