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
    val WHITE_WINE = makeWrapper("white_wine")
    val RED_WINE = makeWrapper("red_wine")
    val LUMLIGHT_LOGS = makeWrapper("lumlight_logs")

    private fun makeWrapper(tag: String): ITag.INamedTag<Item> {
        return ItemTags.makeWrapperTag(HardcoreDungeons.ID + ":" + tag)
    }
}