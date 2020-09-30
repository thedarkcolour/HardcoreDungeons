package thedarkcolour.hardcoredungeons.tags

import net.minecraft.block.Block
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ITag
import thedarkcolour.hardcoredungeons.HardcoreDungeons

/**
 * Contains references to block tags in Hardcore Dungeons.
 *
 * @author TheDarkColour
 */
object HBlockTags {
    val DUNGEON_LOOT_CONTAINERS = tag("dungeon_loot_containers")
    val LUMLIGHT_LOGS = tag("lumlight_logs")
    val COTTONMARSH_LOGS = tag("cottonmarsh_logs")
    val GLASS_RAINBOW = tag("glass/rainbow")
    val GLASS_PANES_RAINBOW = tag("glass_panes/rainbow")
    val FENCES_BRICK = tag("fences/rainbow")
    val CASTLETON_GRASS_PLANTABLE = tag("castleton_grass_plantable")

    private fun tag(name: String): ITag.INamedTag<Block> {
        return BlockTags.makeWrapperTag(HardcoreDungeons.ID + ":" + name)
    }
}