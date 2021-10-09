package thedarkcolour.hardcoredungeons.registry

import net.minecraft.network.PacketBuffer
import net.minecraft.network.datasync.IDataSerializer
import net.minecraftforge.registries.DataSerializerEntry
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerType

/**
 * @author TheDarkColour
 */
object HDataSerializers {
    val DEER_TYPE = DataSerializerEntry(enumSerializer<DeerType>())
        .setRegistryKey("deer_type")

    fun registerDataSerializers(serializers: IForgeRegistry<DataSerializerEntry>) {
        serializers.register(DEER_TYPE)
    }

    private inline fun <reified T : Enum<T>> enumSerializer() = object : IDataSerializer<T> {
        override fun read(buf: PacketBuffer): T =
            buf.readEnum(T::class.java)

        override fun write(buf: PacketBuffer, value: T) {
            buf.writeEnum(value)
        }

        override fun copy(value: T) = value
    }
}