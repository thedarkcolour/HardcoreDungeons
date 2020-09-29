package thedarkcolour.hardcoredungeons.registry

import net.minecraft.network.PacketBuffer
import net.minecraft.network.datasync.IDataSerializer
import net.minecraftforge.registries.DataSerializerEntry
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.entity.castleton.deer.DeerPattern

/**
 * @author TheDarkColour
 */
object HDataSerializers {
    val DEER_PATTERN = DataSerializerEntry(enumSerializer<DeerPattern>())
        .setRegistryKey("deer_pattern")

    fun registerDataSerializers(serializers: IForgeRegistry<DataSerializerEntry>) {
        serializers.register(DEER_PATTERN)
    }

    private inline fun <reified T : Enum<T>> enumSerializer() = object : IDataSerializer<T> {
        override fun read(buf: PacketBuffer): T =
            buf.readEnumValue(T::class.java)

        override fun write(buf: PacketBuffer, value: T) {
            buf.writeEnumValue(value)
        }

        override fun copyValue(value: T) = value
    }
}