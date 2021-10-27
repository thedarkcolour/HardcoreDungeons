package thedarkcolour.hardcoredungeons

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.hardcoredungeons.client.ClientHandler
import thedarkcolour.hardcoredungeons.command.ReloadModelsCommand
import thedarkcolour.hardcoredungeons.config.ConfigHolder
import thedarkcolour.hardcoredungeons.event.EventHandler
import thedarkcolour.hardcoredungeons.registry.RegistryEventHandler
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runWhenOn

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
        EventHandler.registerEvents()

        FORGE_BUS.addListener(::registerCommands)

        MOD_BUS.addListener(::interModComms)

        ConfigHolder.register(MOD_BUS, ID)

        runWhenOn(Dist.CLIENT, ClientHandler::registerEvents)
    }

    /**
     * Inter-mod communications with Curios API.
     *
     * Registers 3 Curio types.
     */
    private fun interModComms(event: InterModEnqueueEvent) {
        event.enqueueWork {
            /*InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE) {
                SlotTypeMessage.Builder("head").build()
            }
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE) {
                SlotTypeMessage.Builder("body").build()
            }
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE) {
                SlotTypeMessage.Builder("necklace").build()
            }*/
        }
    }

    /**
     * Registers the /reloadmodels and /locatebiome commands
     */
    private fun registerCommands(event: RegisterCommandsEvent) {
        val dispatcher = event.dispatcher

        ReloadModelsCommand.register(dispatcher)
    }
}