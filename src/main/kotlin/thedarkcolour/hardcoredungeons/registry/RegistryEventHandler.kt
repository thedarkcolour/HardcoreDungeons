package thedarkcolour.hardcoredungeons.registry

import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.particles.ParticleType
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.SoundEvent
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraftforge.event.RegistryEvent.Register
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
        RegistryFixer.init()

        MOD_BUS.addGenericListener(::registerBiomes)
        MOD_BUS.addGenericListener(::registerBlocks)
        MOD_BUS.addGenericListener(::registerContainerTypes)
        MOD_BUS.addGenericListener(::registerDataSerializers)
        MOD_BUS.addGenericListener(::registerEntities)
        MOD_BUS.addGenericListener(::registerStructures)
        MOD_BUS.addGenericListener(::registerItems)
        MOD_BUS.addGenericListener(::registerParticles)
        MOD_BUS.addGenericListener(::registerRecipes)
        MOD_BUS.addGenericListener(::registerSounds)
        MOD_BUS.addGenericListener(::registerSurfaceBuilders)
        MOD_BUS.addGenericListener(::registerTileEntities)

        FORGE_BUS.addListener(HStructures::addDimensionalSpacing)
        FORGE_BUS.addListener(consumer = HBiomes::biomeLoading, priority = EventPriority.HIGH)
    }
    
    private fun registerBiomes(event: Register<Biome>) = HBiomes.registerBiomes(event.registry)

    private fun registerBlocks(event: Register<Block>) = HBlocks.registerBlocks(event.registry)

    private fun registerContainerTypes(event: Register<ContainerType<*>>) = HContainers.registerContainerTypes(event.registry)

    private fun registerDataSerializers(event: Register<DataSerializerEntry>) = HDataSerializers.registerDataSerializers(event.registry)

    private fun registerEntities(event: Register<EntityType<*>>) = HEntities.registerEntities(event.registry)

    private fun registerStructures(event: Register<Structure<*>>) = HStructures.registerStructures(event.registry)

    private fun registerItems(event: Register<Item>) = HItems.registerItems(event.registry)

    private fun registerParticles(event: Register<ParticleType<*>>) = HParticles.registerParticles(event.registry)

    private fun registerRecipes(event: Register<IRecipeSerializer<*>>) = HRecipes.registerRecipeSerializers(event.registry)

    private fun registerSounds(event: Register<SoundEvent>) = HSounds.registerSounds(event.registry)

    private fun registerSurfaceBuilders(event: Register<SurfaceBuilder<*>>) = HSurfaceBuilders.registerSurfaceBuilders(event.registry)

    private fun registerTileEntities(event: Register<TileEntityType<*>>) = HTileEntities.registerTileEntities(event.registry)
}