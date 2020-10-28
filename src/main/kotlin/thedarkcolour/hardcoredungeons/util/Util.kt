package thedarkcolour.hardcoredungeons.util

import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTUtil
import net.minecraft.network.datasync.DataParameter
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import java.lang.reflect.Field
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @return Whether [this] is equal to any of the values in [others].
 */
fun <T> T.matchesAny(vararg others: T): Boolean {
    for (other in others) {
        if (this == other) {
            return true
        }
    }
    return false
}

/**
 * Creates an immutable copy of this [Set].
 */
inline fun <reified T> Set<T>.copyOf(): Set<T> {
    return setOf(*this.toTypedArray())
}

fun <T> dataParameter(parameter: DataParameter<T>): ReadWriteProperty<Entity, T> {
    return object : ReadWriteProperty<Entity, T> {
        override fun getValue(thisRef: Entity, property: KProperty<*>): T {
            return thisRef.dataManager[parameter]
        }

        override fun setValue(thisRef: Entity, property: KProperty<*>, value: T) {
            thisRef.dataManager[parameter] = value
        }
    }
}

/**
 * Property delegate to a Java Reflected field.
 *
 * Automatically makes the field non-private.
 * Does not make the field non-final.
 */
fun <T> Field.delegate(): ReadWriteProperty<Any, T> {
    return FieldDelegate(this)
}

private class FieldDelegate<T>(private val field: Field) : ReadWriteProperty<Any, T> {
    init {
        field.isAccessible = true
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return field.get(thisRef) as T
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        field.set(thisRef, value)
    }
}

/**
 * Float value for PI
 */
const val PI = 3.14159265358979323846.toFloat()

/**
 * [Math.toRadians] for double values
 */
fun toRadians(angle: Float): Float {
    return angle / 180.0f * PI
}

class BlockPosNBTDelegate(private val tagName: String) : ReadWriteProperty<ItemStack, BlockPos?> {
    override fun getValue(thisRef: ItemStack, property: KProperty<*>): BlockPos? {
        return thisRef.getChildTag(tagName)?.let(NBTUtil::readBlockPos)
    }

    override fun setValue(thisRef: ItemStack, property: KProperty<*>, value: BlockPos?) {
        if (value != null) {
            thisRef.setTagInfo(tagName,  NBTUtil.writeBlockPos(value))
        } else {
            thisRef.removeChildTag(tagName)
        }
    }
}

fun modLoc(path: String): ResourceLocation {
    return ResourceLocation(HardcoreDungeons.ID, path)
}