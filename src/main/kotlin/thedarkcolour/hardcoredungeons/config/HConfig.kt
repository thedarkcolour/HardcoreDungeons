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

    val goldenCarrotsCrop = OptionType.BOOL
        .configure("golden_carrots_crop")
        .description("Whether Golden Carrots can be planted as a crop.")
        .build(true)

    val malachiteCrystalGeneration = OptionType.BOOL
        .configure("malachite_crystal_generation")
        .description("Whether Malachite Crystals generate naturally.")
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