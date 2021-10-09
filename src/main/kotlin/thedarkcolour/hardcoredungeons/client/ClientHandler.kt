package thedarkcolour.hardcoredungeons.client

import net.minecraft.client.renderer.Atlases
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.client.world.DimensionRenderInfo
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.hardcoredungeons.block.HBlocks
import thedarkcolour.hardcoredungeons.client.color.RainbowColor
import thedarkcolour.hardcoredungeons.client.dimension.AubrumEffects
import thedarkcolour.hardcoredungeons.client.dimension.CastletonEffects
import thedarkcolour.hardcoredungeons.client.model.block.FullbrightBakedModel
import thedarkcolour.hardcoredungeons.client.renderer.GreenWandRender
import thedarkcolour.hardcoredungeons.registry.HDimensions
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.registry.HParticles
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
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

        FORGE_BUS.addListener(GreenWandRender::renderTick)
        FORGE_BUS.addListener(GreenWandRender::renderOutline)

        //FORGE_BUS.addListener(::postRender)
    }

    // apparently RenderTypeLookup is not thread safe so we enqueue for later
    private fun clientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork(::setRenderTypes)
        HEntities.registerEntityRenderers()
        HEntities.registerEntityShaders()
        //HContainers.registerScreens()
        HItems.registerItemProperties()

        DimensionRenderInfo.EFFECTS[HDimensions.CASTLETON_ID] = CastletonEffects
        DimensionRenderInfo.EFFECTS[HDimensions.AUBRUM_ID] = AubrumEffects

        Atlases.addWoodType(HBlocks.LUMLIGHT_WOOD.type)

        //ClientRegistry.registerKeyBinding(HKeys.TOGGLE_HCD_OVERLAY)
    }

    /*
    private fun postRender(event: TickEvent.RenderTickEvent) {
        val mc = Minecraft.getInstance()
        val player = mc.player

        if (event.phase == TickEvent.Phase.END && !mc.gameSettings.hideGUI && player != null && !player.isSpectator) {

        }
    }*/

    private fun registerBakedModels(event: ModelBakeEvent) {
        val registry = event.modelRegistry as MutableMap

        FullbrightBakedModel.addFullBrightEffects(registry, HBlocks.CROWN/*HBlocks.CROWN*/, setOf(modLoc("block/crown_fullbright")))
        FullbrightBakedModel.addFullBrightEffects(registry, HBlocks.RUNED_CASTLETON_STONE/*HBlocks.RUNED_CASTLETON_STONE*/, setOf(modLoc("block/runed_castleton_stone_fullbright")))
    }

    private fun registerBlockColors(event: ColorHandlerEvent.Block) {
        val colors = event.blockColors

        colors.register(RainbowColor, HBlocks.RAINBOW_SOIL.grass)
        colors.register(RainbowColor, HBlocks.RAINBOW_GLASS)
        colors.register(RainbowColor, HBlocks.RAINBOW_GLASS_PANE)
    }

    private fun setRenderTypes() {
        RenderTypeLookup.setRenderLayer(HBlocks.GOLDEN_CARROTS, RenderType.cutout())

        RenderTypeLookup.setRenderLayer(HBlocks.CHILI_PEPPER.crop, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.RAINBOW_SOIL.grass, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.RAINBOW_GLASS, RenderType.translucent())
        RenderTypeLookup.setRenderLayer(HBlocks.RAINBOW_GLASS_PANE, RenderType.translucent())
        HBlocks.CASTLETON_TORCH.setRenderLayers()
        RenderTypeLookup.setRenderLayer(HBlocks.CROWN, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.RED_WINE_BOTTLE, RenderType.translucent())
        RenderTypeLookup.setRenderLayer(HBlocks.WHITE_WINE_BOTTLE, RenderType.translucent())
        RenderTypeLookup.setRenderLayer(HBlocks.WINE_BOTTLE, RenderType.translucent())
        RenderTypeLookup.setRenderLayer(HBlocks.CASTLETON_LANTERN, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.DUNGEON_SPAWNER, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.LUMLIGHT_CAMPFIRE, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.COTTONMARSH_WOOD.leaves, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.RUNED_CASTLETON_STONE, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.WILD_BERROOK.crop, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.DIAMOND_CRYSTAL.crystal, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(HBlocks.MALACHITE_CRYSTAL.crystal, RenderType.cutout())

        HBlocks.LUMLIGHT_WOOD.setRenderLayers()
        HBlocks.PURPLE_LUMSHROOM.setRenderLayers()
        HBlocks.BLUE_LUMSHROOM.setRenderLayers()
        HBlocks.LUMLIGHT_WOOD.setRenderLayers()
        HBlocks.FLAME_ROSE.setRenderLayers()
        HBlocks.GOLDEN_TULIP.setRenderLayers()
        HBlocks.GREEN_GUMDROP.setRenderLayers()
        HBlocks.MINI_GREEN_GUMDROP.setRenderLayers()
        HBlocks.PINK_GUMDROP.setRenderLayers()
        HBlocks.MINI_PINK_GUMDROP.setRenderLayers()
        HBlocks.BLUE_GUMDROP.setRenderLayers()
        HBlocks.MINI_BLUE_GUMDROP.setRenderLayers()
        HBlocks.PURPLE_GUMDROP.setRenderLayers()
        HBlocks.MINI_PURPLE_GUMDROP.setRenderLayers()
        HBlocks.RED_GUMDROP.setRenderLayers()
        HBlocks.MINI_RED_GUMDROP.setRenderLayers()
        HBlocks.YELLOW_GUMDROP.setRenderLayers()
        HBlocks.MINI_YELLOW_GUMDROP.setRenderLayers()
    }

    private fun registerParticleFactories(event: ParticleFactoryRegisterEvent) = HParticles.registerParticleFactories()

    private fun registerItemColors(event: ColorHandlerEvent.Item) = HItems.setItemColors(event.itemColors)
}