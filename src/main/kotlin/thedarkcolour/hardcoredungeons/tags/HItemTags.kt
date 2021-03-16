package thedarkcolour.hardcoredungeons.tags

import net.minecraft.item.Item
import net.minecraft.tags.ITag
import net.minecraft.tags.ItemTags
import thedarkcolour.hardcoredungeons.HardcoreDungeons

/**
 * Contains references to item tags in Hardcore Dungeons.
 *
 * @author TheDarkColour
 */
object HItemTags {
    val AMMUNITION_GENERIC = tag("ammunition/generic")
    val AMMUNITION_SMALL = tag("ammunition/small")
    val AMMUNITION_INCENDIARY = tag("ammunition/incendiary")
    val LUMLIGHT_LOGS = tag("lumlight_logs")
    val GEMS_MALACHITE = tag("gems/malachite")
    val GEMS_RAINBOWSTONE = tag("gems/rainbowstone")

    private fun tag(tag: String): ITag.INamedTag<Item> {
        return ItemTags.makeWrapperTag(HardcoreDungeons.ID + ":" + tag)
    }
}