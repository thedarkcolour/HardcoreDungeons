package thedarkcolour.hardcoredungeons.tags

import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.util.modLoc

/**
 * Block tags in Hardcore Dungeons.
 *
 * @author thedarkcolour
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

    val CASTLETON_CARVER_REPLACEABLES = tag("castleton_carver_replaceables")

    private fun tag(name: String): TagKey<Block> {
        return TagKey.create(Registries.BLOCK, modLoc(name))
    }
}