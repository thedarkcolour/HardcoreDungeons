package thedarkcolour.hardcoredungeons.util

import net.minecraft.world.item.ItemStack
import net.minecraft.resources.ResourceLocation
import net.minecraft.core.BlockPos
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// internal to not mess up other people's mods
internal fun modLoc(path: String): ResourceLocation {
    return ResourceLocation(HardcoreDungeons.ID, path)
}

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

/**
 * [Math.toRadians] for [Float] values
 */
fun toRadians(degrees: Float): Float {
    return degrees * Mth.DEG_TO_RAD
}

fun toDegrees(radians: Float): Float {
    return radians * Mth.DEG_TO_RAD
}

fun setStoredPos(stack: ItemStack, pos: BlockPos?) {
    if (pos == null) {
        stack.removeTagKey("StoredPos")
    } else {
        stack.addTagElement("StoredPos", NbtUtils.writeBlockPos(pos))
    }
}

fun getStoredPos(stack: ItemStack) : BlockPos? {
    return stack.getTagElement("StoredPos")?.let(NbtUtils::readBlockPos)
}
