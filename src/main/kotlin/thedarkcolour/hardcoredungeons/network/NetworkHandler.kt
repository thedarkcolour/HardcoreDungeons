package thedarkcolour.hardcoredungeons.network

import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.item.debug.GearWand
import thedarkcolour.hardcoredungeons.util.modLoc

object NetworkHandler {
    private const val PROTOCOL_VERSION = "1"
    private val CHANNEL = NetworkRegistry.newSimpleChannel(modLoc(HardcoreDungeons.ID), ::PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals)

    // todo use
    @Suppress("UNUSED_CHANGED_VALUE", "INACCESSIBLE_TYPE")
    fun register() {
        var msgID = 0

        CHANNEL.registerMessage(msgID++, GearWandMessage::class.java, GearWandMessage::encode, GearWandMessage::decode, GearWandMessage::process)
    }

    /**
     * Sends a gear wand message to the client to open an editor screen.
     *
     * @param id The id of the type of screen to open
     */
    fun sendGearWandMessage(id: GearWand.Type, playerIn: ServerPlayerEntity) {
        CHANNEL.sendTo(GearWandMessage(id.ordinal), playerIn.connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
    }
}