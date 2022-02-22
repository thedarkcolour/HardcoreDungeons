package thedarkcolour.hardcoredungeons.tags

import net.minecraft.block.Block
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ITag
import thedarkcolour.hardcoredungeons.HardcoreDungeons

/**
 * Block tags in Hardcore Dungeons.
 *
 * @author TheDarkColour
 */
object HBlockTags {
    val LUMLIGHT_LOGS = tag("lumlight_logs")
    val COTTONMARSH_LOGS = tag("cottonmarsh_logs")
    val AURI_LOGS = tag("auri_logs")
    val LUMSHROOM = tag("lumshroom")

    val GLASS_RAINBOW = tag("glass/rainbow")
    val GLASS_PANES_RAINBOW = tag("glass_panes/rainbow")

    val CASTLETON_GRASS_PLANTABLE = tag("castleton_grass_plantable")
    val RAINBOW_GRASS_PLANTABLE = tag("rainbow_grass_plantable")
    val AURIGRASS_PLANTABLE = tag("aurigrass_plantable")
    val SUGARY_GRASS_PLANTABLE = tag("sugary_grass_plantable")

    private fun tag(name: String): ITag.INamedTag<Block> {
        return BlockTags.bind(HardcoreDungeons.ID + ":" + name)
    }
}