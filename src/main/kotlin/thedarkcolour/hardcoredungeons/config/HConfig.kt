@file:Suppress("MemberVisibilityCanBePrivate")

package thedarkcolour.hardcoredungeons.config

import thedarkcolour.hardcoredungeons.config.option.OptionType

object HConfig {

    // Overworld options
    val overworld = OptionType.CATEGORY
        .configure("overworld")
        .description("Change what Hardcore Dungeons adds to the overworld.")
        .build(Unit)

    val thickForestGenerates = OptionType.BOOL
        .configure("thick_forest_generates")
        .description("Whether the Thick Forest biome generates naturally. Changing this will affect world generation.")
        .build(true)

    // Setup the options here and create the config option hierarchy
    private fun configure() {
        overworld.add(thickForestGenerates)
    }

    private var setup = false

    fun setup() {
        if (!setup) {
            configure()

            setup = true
        }
    }
}