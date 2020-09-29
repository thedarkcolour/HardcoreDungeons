package thedarkcolour.hardcoredungeons.container

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Kotlin-ified version of [net.minecraft.util.IWorldPosCallable]
 *
 * Functions are arranged so a functional parameter is always
 * the last argument to make calling them more readable and simpler.
 *
 * Functions have their names changed to match
 * the naming scheme of functional types.
 */
interface WorldPos {
    /**
     * Invokes the function and returns a nullable result.
     *
     * The returned value should really be a non-null value,
     * but Kotlin allows passing in a function with a nullable result.
     *
     * @see invokeDefaulted
     */
    fun <T> invoke(function: (World, BlockPos) -> T): T?

    /**
     * Invokes the function and always returns a non-null result.
     * If the function returns a null value, Elvis operator defaults
     * to the non-null value [default]
     *
     * @param default the value to default to if the [function] returns null.
     */
    fun <T> invokeDefaulted(default: T, function: (World, BlockPos) -> T?): T {
        return invoke(function) ?: default
    }

    /**
     * The [BlockState] at this [WorldPos]
     */
    val state: BlockState
        get() = invokeDefaulted(Blocks.AIR.defaultState) { worldIn, pos ->
            worldIn.getBlockState(pos)
        }

    companion object {
        /**
         * A [WorldPos] that always returns null.
         */
        val DUMMY = object : WorldPos {
            override fun <T> invoke(function: (World, BlockPos) -> T): T? {
                return null
            }
        }

        /**
         * Helper method for creating a new [WorldPos].
         */
        fun of(worldIn: World, pos: BlockPos): WorldPos {
            return object : WorldPos {
                override fun <T> invoke(function: (World, BlockPos) -> T): T {
                    return function(worldIn, pos)!!
                }
            }
        }
    }
}