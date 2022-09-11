package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraftforge.registries.MissingMappingsEvent
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.HItems
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
        HItems.init()

        HBiomes.init()
        HCarvers.init()
        HDataSerializers.init()
        HPaintings.init()
        HEntities.init()
        HFeatures.init() // not HRegistry, but has same functions
        HParticles.init()
        HEnchantments.init()
        //HNoiseGeneratorSettings.init()

        MOD_BUS.addListener(HEntities::registerEntityAttributes)
        FORGE_BUS.addListener(::remap)
    }

    private fun remap(event: MissingMappingsEvent) {
        for (mapping in event.getAllMappings(event.key as ResourceKey<out Registry<Any>>)) {
            if (mapping.key.namespace != HardcoreDungeons.ID) {
                mapping.warn()
            } else {
                mapping.ignore()
            }
        }
    }
}