package thedarkcolour.hardcoredungeons.tags

import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import thedarkcolour.hardcoredungeons.registry.items.HItems

/**
 * Item tags in Hardcore Dungeons.
 *
 * @author thedarkcolour
 */
object HItemTags {
    // Wood
    val LUMLIGHT_LOGS = tag("lumlight_logs")
    val AURI_LOGS = tag("auri_logs")
    val COTTONMARSH_LOGS = tag("cottonmarsh_logs")
    val LUMSHROOM = tag("lumshroom")

    val AMMUNITION_GENERIC = tag("ammunition/generic")
    val AMMUNITION_SMALL = tag("ammunition/small")
    val AMMUNITION_INCENDIARY = tag("ammunition/incendiary")
    val GEMS_MALACHITE = tag("gems/malachite")
    val GEMS_RAINBOWSTONE = tag("gems/rainbowstone")

    private fun tag(path: String): TagKey<Item> {
        return HItems.createTag(path)
    }
}