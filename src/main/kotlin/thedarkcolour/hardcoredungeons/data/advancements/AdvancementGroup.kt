package thedarkcolour.hardcoredungeons.data.advancements

import net.minecraft.advancements.Advancement

/**
 * Group could be "overworld", "candyland", "nether", etc.
 */
abstract class AdvancementGroup(protected val group: String) : ((Advancement) -> Unit) -> Unit {
}