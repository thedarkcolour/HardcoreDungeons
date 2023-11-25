package thedarkcolour.hardcoredungeons.client

import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.Sheets
import net.minecraftforge.client.event.ComputeFovModifierEvent
import net.minecraftforge.client.event.ModelEvent
import net.minecraftforge.client.event.RegisterColorHandlersEvent
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.hardcoredungeons.client.color.RainbowColor
import thedarkcolour.hardcoredungeons.client.dimension.CastletonEffects
import thedarkcolour.hardcoredungeons.client.model.HModelLayers
import thedarkcolour.hardcoredungeons.client.model.block.FullbrightBakedModel
import thedarkcolour.hardcoredungeons.client.renderer.curio.PendantCurioRenderer
import thedarkcolour.hardcoredungeons.item.GunItem
import thedarkcolour.hardcoredungeons.registry.HDimensions
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HEntities.registerEntityRenderers
import thedarkcolour.hardcoredungeons.registry.HParticles
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.AURIGOLD_PENDANT_ITEM
import thedarkcolour.hardcoredungeons.registry.items.HItems
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import top.theillusivec4.curios.api.client.CuriosRendererRegistry

/**
 * Handles events that are only fired on the client side.
 *
 * @author thedarkcolour
 */
object ClientHandler {
    /**
     * Adds listeners to the mod bus.
     */
    fun registerEvents() {
        MOD_BUS.addListener(::clientSetup)
        MOD_BUS.addListener(HParticles::registerParticleFactories)
        MOD_BUS.addListener(::registerBlockColors)
        MOD_BUS.addListener(::registerItemColors)
        MOD_BUS.addListener(::registerBakedModels)
        MOD_BUS.addListener(HModelLayers::registerLayers)
        MOD_BUS.addListener(HEntities::registerEntityShaders)
        MOD_BUS.addListener(HEntities::registerEntityShaders)
        MOD_BUS.addListener(::registerDimensionFx)
        MOD_BUS.addListener(::registerEntityRenderers)
        FORGE_BUS.addListener(::computeFOV)

        //FORGE_BUS.addListener(::postRender)
    }

    private fun computeFOV(event: ComputeFovModifierEvent) {
        val item = event.player.mainHandItem.item

        if (item is GunItem) {
            // Maybe a zoom function?
            event.newFovModifier = event.newFovModifier * item.zoomModifier(event.player)
        }
    }

    // ItemBlockRenderTypes is not (?) thread safe so enqueue on main thread
    private fun clientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork(::setRenderTypes)
        //HContainers.registerScreens()
        HItems.registerItemProperties()

        Sheets.addWoodType(HBlocks.LUMLIGHT_WOOD.woodType)
        Sheets.addWoodType(HBlocks.AURI_WOOD.woodType)
        Sheets.addWoodType(HBlocks.COTTONMARSH_WOOD.woodType)

        CuriosRendererRegistry.register(AURIGOLD_PENDANT_ITEM, ::PendantCurioRenderer)
    }

    private fun registerDimensionFx(event: RegisterDimensionSpecialEffectsEvent) {
        event.register(HDimensions.CASTLETON_ID, CastletonEffects)
    }

    private fun registerBakedModels(event: ModelEvent.BakingCompleted) {
        val registry = event.models as MutableMap

        FullbrightBakedModel.addFullBrightEffects(registry, HBlocks.CROWN, setOf(modLoc("block/crown_fullbright")))
        FullbrightBakedModel.addFullBrightEffects(registry, HBlocks.RUNED_CASTLETON_STONE, setOf(modLoc("block/runed_castleton_stone_fullbright")))
    }

    private fun registerBlockColors(event: RegisterColorHandlersEvent.Block) {
        event.register(RainbowColor, HBlocks.RAINBOW_SOIL.grass)
        event.register(RainbowColor, HBlocks.RAINBOW_GLASS)
        event.register(RainbowColor, HBlocks.RAINBOW_GLASS_PANE)
    }

    private fun setRenderTypes() {
        ItemBlockRenderTypes.setRenderLayer(HBlocks.CHILI_PEPPER.crop, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.RAINBOW_SOIL.grass, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.RAINBOW_GLASS, RenderType.translucent())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.RAINBOW_GLASS_PANE, RenderType.translucent())
        HBlocks.CASTLETON_TORCH.setRenderLayers()
        ItemBlockRenderTypes.setRenderLayer(HBlocks.CROWN, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.RED_WINE_BOTTLE, RenderType.translucent())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.WHITE_WINE_BOTTLE, RenderType.translucent())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.WINE_BOTTLE, RenderType.translucent())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.CASTLETON_LANTERN, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.LUMLIGHT_CAMPFIRE, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.COTTONMARSH_WOOD.leaves, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.RUNED_CASTLETON_STONE, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.WILD_BERROOK.crop, RenderType.cutout())
        //ItemBlockRenderTypes.setRenderLayer(HBlocks.DIAMOND_CRYSTAL.crystal, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(HBlocks.MALACHITE_CRYSTAL.crystal, RenderType.cutout())

        HBlocks.LUMLIGHT_WOOD.setRenderLayers()
        HBlocks.AURI_WOOD.setRenderLayers()
        HBlocks.COTTONMARSH_WOOD.setRenderLayers()

        HBlocks.FLAME_ROSE.setRenderLayers()
        HBlocks.GOLDEN_TULIP.setRenderLayers()

        HBlocks.PURPLE_LUMSHROOM.setRenderLayers()
        HBlocks.BLUE_LUMSHROOM.setRenderLayers()

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

    private fun registerItemColors(event: RegisterColorHandlersEvent.Item) = HItems.setItemColors(event)
}