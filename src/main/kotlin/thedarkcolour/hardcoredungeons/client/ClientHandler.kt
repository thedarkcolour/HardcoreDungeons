package thedarkcolour.hardcoredungeons.client

import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.hardcoredungeons.client.model.FullbrightBakedModel
import thedarkcolour.hardcoredungeons.registry.*
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * Handles events that are only fired on the client side.
 *
 * @author TheDarkColour
 */
object ClientHandler {
    /**
     * Adds listeners to the mod bus.
     */
    fun registerEvents() {
        MOD_BUS.addListener(::clientSetup)
        MOD_BUS.addListener(::registerParticleFactories)
        MOD_BUS.addListener(::registerBlockColors)
        MOD_BUS.addListener(::registerItemColors)
        MOD_BUS.addListener(::registerBakedModels)
    }
    
    private fun clientSetup(event: FMLClientSetupEvent) {
        HBlocks.setRenderTypes()
        HEntities.registerEntityRenderers()
        HEntities.registerEntityShaders()
        HContainers.registerScreens()
    }

    private fun registerBakedModels(event: ModelBakeEvent) {
        val registry = event.modelRegistry as MutableMap

        FullbrightBakedModel.addFullBrightEffects(registry, HBlocks.CROWN, setOf(ResourceLocation("hardcoredungeons:block/crown_fullbright")))
        FullbrightBakedModel.addFullBrightEffects(registry, HBlocks.RUNED_CASTLETON_STONE, setOf(ResourceLocation("hardcoredungeons:block/runed_castleton_stone_fullbright")))
    }

    private fun registerBlockColors(event: ColorHandlerEvent.Block) = HBlocks.setBlockColors(event.blockColors)

    private fun registerParticleFactories(event: ParticleFactoryRegisterEvent) = HParticles.registerParticleFactories()

    private fun registerItemColors(event: ColorHandlerEvent.Item) = HItems.setItemColors(event.itemColors)
}