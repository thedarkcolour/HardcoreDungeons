package thedarkcolour.hardcoredungeons.util

import net.minecraft.resources.ResourceLocation
import net.minecraft.core.BlockPos
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Property delegate to a property in the `dataManager` of an `Entity`.
 */
fun <T> dataParameterDelegate(parameter: EntityDataAccessor<T>): ReadWriteProperty<Entity, T> {
    return object : ReadWriteProperty<Entity, T> {
        override fun getValue(thisRef: Entity, property: KProperty<*>): T {
            return thisRef.entityData[parameter]
        }

        override fun setValue(thisRef: Entity, property: KProperty<*>, value: T) {
            thisRef.entityData[parameter] = value
        }
    }
}

/** [Float] value of PI */
const val PI = 3.14159265358979323846.toFloat()

/**
 * [Math.toRadians] for [Float] values
 */
fun toRadians(degrees: Float): Float {
    return degrees / 180.0f * PI
}

fun toDegrees(radians: Float): Float {
    return radians * 180.0f / PI
}

class BlockPosNBTDelegate(private val tagName: String) : ReadWriteProperty<ItemStack, BlockPos?> {
    override fun getValue(thisRef: ItemStack, property: KProperty<*>): BlockPos? {
        return thisRef.getTagElement(tagName)?.let(NbtUtils::readBlockPos)
    }

    override fun setValue(thisRef: ItemStack, property: KProperty<*>, value: BlockPos?) {
        if (value != null) {
            thisRef.addTagElement(tagName,  NbtUtils.writeBlockPos(value))
        } else {
            thisRef.removeTagKey(tagName)
        }
    }
}

// internal to not mess up other people's mods
internal fun modLoc(path: String): ResourceLocation {
    return ResourceLocation(HardcoreDungeons.ID, path)
}