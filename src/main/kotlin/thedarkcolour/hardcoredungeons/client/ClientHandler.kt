package thedarkcolour.hardcoredungeons.client

import net.minecraft.block.Block
import net.minecraft.client.renderer.model.IBakedModel
import net.minecraft.client.world.DimensionRenderInfo
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.hardcoredungeons.client.dimension.CastletonEffects
import thedarkcolour.hardcoredungeons.client.model.block.FullbrightBakedModel
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.path
import thedarkcolour.hardcoredungeons.registry.*
import thedarkcolour.hardcoredungeons.util.modLoc
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

    // apparently RenderTypeLookup is not thread safe so we enqueue for later
    private fun clientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork(HBlocks::setRenderTypes)
        HEntities.registerEntityRenderers()
        HEntities.registerEntityShaders()
        //HContainers.registerScreens()
        HItems.registerItemProperties()

        DimensionRenderInfo.field_239208_a_[HDimensions.CASTLETON_ID] = CastletonEffects

        //ClientRegistry.registerKeyBinding(HKeys.TOGGLE_HCD_OVERLAY)

    }

    private fun registerBakedModels(event: ModelBakeEvent) {
        val registry = event.modelRegistry as MutableMap

        FullbrightBakedModel.addFullBrightEffects(registry, HBlocks.CROWN, setOf(modLoc("block/crown_fullbright")))
        FullbrightBakedModel.addFullBrightEffects(registry, HBlocks.RUNED_CASTLETON_STONE, setOf(modLoc("block/runed_castleton_stone_fullbright")))
    }

    fun addFullbright(registry: MutableMap<ResourceLocation, IBakedModel>, block: Block) {
        FullbrightBakedModel.addFullBrightEffects(registry, block, setOf(modLoc("block/" + path(block))))
    }

    private fun registerBlockColors(event: ColorHandlerEvent.Block) = HBlocks.setBlockColors(event.blockColors)

    private fun registerParticleFactories(event: ParticleFactoryRegisterEvent) = HParticles.registerParticleFactories()

    private fun registerItemColors(event: ColorHandlerEvent.Item) = HItems.setItemColors(event.itemColors)
}