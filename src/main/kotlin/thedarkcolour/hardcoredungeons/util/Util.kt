package thedarkcolour.hardcoredungeons.util

import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTUtil
import net.minecraft.network.datasync.DataParameter
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Property delegate to a property in the `dataManager` of an `Entity`.
 */
fun <T> dataParameterDelegate(parameter: DataParameter<T>): ReadWriteProperty<Entity, T> {
    return object : ReadWriteProperty<Entity, T> {
        override fun getValue(thisRef: Entity, property: KProperty<*>): T {
            return thisRef.dataManager[parameter]
        }

        override fun setValue(thisRef: Entity, property: KProperty<*>, value: T) {
            thisRef.dataManager[parameter] = value
        }
    }
}

/** `Float` value of PI */
const val PI = 3.14159265358979323846.toFloat()

/**
 * [Math.toRadians] for `Float` values
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

// internal to not mess up other people's mods
internal fun modLoc(path: String): ResourceLocation {
    return ResourceLocation(HardcoreDungeons.ID, path)
}