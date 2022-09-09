package thedarkcolour.hardcoredungeons.registry

import thedarkcolour.hardcoredungeons.registry.items.HItemsNew
import thedarkcolour.hardcoredungeons.worldgen.HFeatures
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * Listens for registry events and delegates to
 * the specific object classes for each registry type.
 *
 * @author thedarkcolour
 */
object RegistryEventHandler {
    /**
     * Sets up our registries.
     */
    @Suppress("DuplicatedCode")
    fun registerEvents() {
        HBlocks.init()
        HDataSerializers.init()
        HPaintings.init()
        HCarvers.init()
        HEntities.init()
        HItemsNew.init()
        HBiomes.init()
        HTileEntities.init()
        HFeatures.init() // not HRegistry, but has same functions
        HParticles.init()

        MOD_BUS.addListener(HEntities::registerEntityAttributes)
        FORGE_BUS.addListener(HStructures::addDimensionalSpacing)
    }
}