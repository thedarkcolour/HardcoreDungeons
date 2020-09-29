package thedarkcolour.hardcoredungeons

import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.fml.InterModComms
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.hardcoredungeons.block.misc.BonusFarmlandBlock
import thedarkcolour.hardcoredungeons.block.plant.misc.GoldenCarrotsBlock
import thedarkcolour.hardcoredungeons.client.ClientHandler
import thedarkcolour.hardcoredungeons.command.GenerateStructureCommand
import thedarkcolour.hardcoredungeons.command.LocateBiomeCommand
import thedarkcolour.hardcoredungeons.command.ReloadModelsCommand
import thedarkcolour.hardcoredungeons.registry.HBiomes
import thedarkcolour.hardcoredungeons.registry.RegistryEventHandler
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runWhenOn
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.SlotTypeMessage

/**
 * Main mod class for Hardcore Dungeons.
 *
 * @author TheDarkColour, genericrandom64
 */
@Mod(HardcoreDungeons.ID)
object HardcoreDungeons {
    const val ID = "hardcoredungeons"
    // Logger for Hardcore Dungeons
    val LOGGER: Logger = LogManager.getLogger()

    // Early to rise, early to bed, makes a man healthy but socially dead.
    init {
        RegistryEventHandler.registerEvents()
        FORGE_BUS.addListener(::registerCommands)
        // maybe split into separate events class?
        FORGE_BUS.addListener(BonusFarmlandBlock::overrideCropGrowthBehaviour)
        FORGE_BUS.addListener(GoldenCarrotsBlock::onBlockActivated)

        MOD_BUS.addListener(::interModComms)
        MOD_BUS.addListener(::commonSetup)

        runWhenOn(Dist.CLIENT, ClientHandler::registerEvents)
    }

    /**
     * Inter-mod communications with Curios API.
     *
     * Registers 3 Curio types.
     *
     * @see top.theillusivec4.curios.api.CurioTags.HEAD
     * @see top.theillusivec4.curios.api.CurioTags.BODY
     * @see top.theillusivec4.curios.api.CurioTags.NECKLACE
     */
    private fun interModComms(event: InterModEnqueueEvent) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE) {
            SlotTypeMessage.Builder("head").build()
        }
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE) {
            SlotTypeMessage.Builder("body").build()
        }
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE) {
            SlotTypeMessage.Builder("necklace").build()
        }
    }

    /**
     * Registers the /reloadmodels and /locatebiome commands
     */
    private fun registerCommands(event: RegisterCommandsEvent) {
        val dispatcher = event.dispatcher

        LocateBiomeCommand.register(dispatcher)
        ReloadModelsCommand.register(dispatcher)
        GenerateStructureCommand.register(dispatcher)
    }

    /**
     * Moving the code that normally operated on registry entries during the registry events.
     *
     * This will allow us to use object holders safely.
     */
    private fun commonSetup(event: FMLCommonSetupEvent) {
        HBiomes.postBiomeRegistry()
    }

    fun loc(path: String): ResourceLocation {
        return ResourceLocation(ID, path)
    }
}