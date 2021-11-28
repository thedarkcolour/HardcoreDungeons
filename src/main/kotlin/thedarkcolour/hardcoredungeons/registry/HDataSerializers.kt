package thedarkcolour.hardcoredungeons.registry

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.syncher.EntityDataSerializer
import net.minecraftforge.registries.*
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerType

/**
 * @author TheDarkColour
 */
object HDataSerializers {
    val DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.DATA_SERIALIZERS, HardcoreDungeons.ID)

    val DEER_TYPE = DATA_SERIALIZERS.register("") {
        DataSerializerEntry(enumSerializer<DeerType>()).setRegistryKey("deer_type")
    }

    private inline fun <reified T : Enum<T>> enumSerializer() = object : EntityDataSerializer<T> {
        override fun read(buf: FriendlyByteBuf): T =
            buf.readEnum(T::class.java)

        override fun write(buf: FriendlyByteBuf, value: T) {
            buf.writeEnum(value)
        }

        override fun copy(value: T) = value
    }
}
