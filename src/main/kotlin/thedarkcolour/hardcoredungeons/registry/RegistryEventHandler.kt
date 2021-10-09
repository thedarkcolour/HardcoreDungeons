package thedarkcolour.hardcoredungeons.registry

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.potion.Effect
import net.minecraft.util.SoundEvent
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.registries.DataSerializerEntry
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
        HTileEntities.init()
        //MOD_BUS.addGenericListener(::registerBlocks)
        //MOD_BUS.addGenericListener(::registerContainerTypes)
        MOD_BUS.addGenericListener(::registerDataSerializers)
        //MOD_BUS.addGenericListener(::registerEffects)
        MOD_BUS.addGenericListener(::registerEnchantments)
        MOD_BUS.addGenericListener(::registerEntities)
        MOD_BUS.addGenericListener(::registerFeatures)
        MOD_BUS.addGenericListener(::registerStructures)
        MOD_BUS.addGenericListener(::registerItems)
        HParticles.PARTICLE_TYPES.register(MOD_BUS)
        MOD_BUS.addGenericListener(::registerSounds)
        MOD_BUS.addGenericListener(::registerSurfaceBuilders)
        //MOD_BUS.addGenericListener(::registerTileEntities)

        MOD_BUS.addListener(HEntities::registerEntityAttributes)
        FORGE_BUS.addListener(HStructures::addDimensionalSpacing)
        // Write as lambda to avoid classloading HBiomes early
        FORGE_BUS.addListener(EventPriority.HIGH) { event: BiomeLoadingEvent -> HBiomes.biomeLoading(event) }
    }
    
    //private fun registerBiomes(event: Register<Biome>) = HBiomes.registerBiomes(event.registry)

    //private fun registerBlocks(event: Register<Block>) = HBlocks.registerBlocks(event.registry)

    //private fun registerContainerTypes(event: Register<ContainerType<*>>) = HContainers.registerContainerTypes(event.registry)

    private fun registerDataSerializers(event: Register<DataSerializerEntry>) = HDataSerializers.registerDataSerializers(event.registry)

    private fun registerEffects(event: Register<Effect>) = HEffects.registerEffects(event.registry)

    private fun registerEnchantments(event: Register<Enchantment>) = HEnchantments.registerEnchantments(event.registry)

    private fun registerEntities(event: Register<EntityType<*>>) = HEntities.registerEntities(event.registry)

    private fun registerFeatures(event: Register<Feature<*>>) = HFeatures.registerFeatures(event.registry)

    private fun registerStructures(event: Register<Structure<*>>) = HStructures.registerStructures(event.registry)

    private fun registerItems(event: Register<Item>) = HItems.registerItems(event.registry)

    private fun registerSounds(event: Register<SoundEvent>) = HSounds.registerSounds(event.registry)

    private fun registerSurfaceBuilders(event: Register<SurfaceBuilder<*>>) = HSurfaceBuilders.registerSurfaceBuilders(event.registry)

    //private fun registerTileEntities(event: Register<TileEntityType<*>>) = HTileEntities.registerTileEntities(event.registry)
}