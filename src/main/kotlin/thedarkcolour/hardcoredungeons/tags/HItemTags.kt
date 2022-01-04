package thedarkcolour.hardcoredungeons.tags

import net.minecraft.item.Item
import net.minecraft.tags.ITag
import net.minecraft.tags.ItemTags
import thedarkcolour.hardcoredungeons.HardcoreDungeons

/**
 * Item tags in Hardcore Dungeons.
 *
 * @author TheDarkColour
 */
object HItemTags {
    // Wood
    val LUMLIGHT_LOGS = tag("lumlight_logs")
    val AURI_LOGS = tag("auri_logs")
    val COTTONMARSH_LOGS = tag("cottonmarsh_logs")

    val AMMUNITION_GENERIC = tag("ammunition/generic")
    val AMMUNITION_SMALL = tag("ammunition/small")
    val AMMUNITION_INCENDIARY = tag("ammunition/incendiary")
    val GEMS_MALACHITE = tag("gems/malachite")
    val GEMS_RAINBOWSTONE = tag("gems/rainbowstone")

    private fun tag(tag: String): ITag.INamedTag<Item> {
        return ItemTags.bind(HardcoreDungeons.ID + ":" + tag)
    }
}