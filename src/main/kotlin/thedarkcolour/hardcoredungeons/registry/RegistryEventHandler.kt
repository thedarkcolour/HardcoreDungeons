package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.StructureFeature
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.EventPriority
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * Listens for registry events and delegates to
 * the specific object classes for each registry type.
 *
 * @author TheDarkColour
 */
object RegistryEventHandler {
    /**
     * Sets up our registries.
     */
    @Suppress("DuplicatedCode")
    fun registerEvents() {
        // todo re-enable once forge is not broken
        //RegistryFixer.init()

        HBlocks.init()
        HItemsNew.init()
        HBiomes.init()
        HBlockEntities.init()
        HDataSerializers.DATA_SERIALIZERS.register(MOD_BUS)
        MOD_BUS.addGenericListener(::registerEnchantments)
        MOD_BUS.addGenericListener(::registerEntities)
        MOD_BUS.addGenericListener(::registerFeatures)
        MOD_BUS.addGenericListener(::registerStructures)
        MOD_BUS.addGenericListener(::registerItems)
        HParticles.PARTICLE_TYPES.register(MOD_BUS)
        HSounds.SOUND_EVENTS.register(MOD_BUS)
        HSurfaceBuilders.SURFACE_BUILDERS.register(MOD_BUS)

        MOD_BUS.addListener(HEntities::registerEntityAttributes)
        FORGE_BUS.addListener(HStructures::addDimensionalSpacing)
        // Write as lambda to avoid classloading HBiomes early
        FORGE_BUS.addListener(EventPriority.HIGH) { event: BiomeLoadingEvent -> HBiomes.biomeLoading(event) }
    }

    private fun registerEntities(event: Register<EntityType<*>>) = HEntities.registerEntities(event.registry)

    private fun registerFeatures(event: Register<Feature<*>>) = HFeatures.registerFeatures(event.registry)

    private fun registerStructures(event: Register<StructureFeature<*>>) = HStructures.registerStructures(event.registry)

    private fun registerItems(event: Register<Item>) = HItems.registerItems(event.registry)
}