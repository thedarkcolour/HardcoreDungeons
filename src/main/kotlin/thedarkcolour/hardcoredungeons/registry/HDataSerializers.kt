package thedarkcolour.hardcoredungeons.registry

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.syncher.EntityDataSerializer
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerType

/**
 * @author thedarkcolour
 */
object HDataSerializers : HRegistry<EntityDataSerializer<*>>(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS) {
    val DEER_TYPE by register<EntityDataSerializer<DeerType>>("deer_type", this::enumSerializer)

    private inline fun <reified T : Enum<T>> enumSerializer() = object : EntityDataSerializer<T> {
        override fun read(buf: FriendlyByteBuf): T =
            buf.readEnum(T::class.java)

        override fun write(buf: FriendlyByteBuf, value: T) {
            buf.writeEnum(value)
        }

        override fun copy(value: T) = value
    }
}