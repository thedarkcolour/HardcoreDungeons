package thedarkcolour.hardcoredungeons.network

import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import thedarkcolour.hardcoredungeons.client.screen.EditDungeonSpawnerScreen
import java.util.function.Supplier

class GearWandMessage(val id: Int) {

    companion object {
        @JvmStatic
        fun encode(msg: GearWandMessage, buffer: PacketBuffer) {
            buffer.writeInt(msg.id)
        }

        @JvmStatic
        fun decode(buffer: PacketBuffer): GearWandMessage {
            return GearWandMessage(buffer.getInt(0))
        }

        @JvmStatic
        fun process(msg: GearWandMessage, contextSupplier: Supplier<NetworkEvent.Context>) {
            val ctx = contextSupplier.get()

            ctx.enqueueWork {
                Minecraft.getInstance().displayGuiScreen(EditDungeonSpawnerScreen())
            }
        }
    }
}