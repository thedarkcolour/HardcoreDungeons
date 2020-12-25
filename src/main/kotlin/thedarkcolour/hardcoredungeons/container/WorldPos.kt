package thedarkcolour.hardcoredungeons.container

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Kotlin-ified version of [net.minecraft.util.IWorldPosCallable]
 */
interface WorldPos {
    /**
     * Invokes the function and returns a nullable result.
     *
     * [DUMMY] will always return null.
     *
     * @see invokeDefaulted
     */
    fun <T> invoke(function: (World, BlockPos) -> T): T?

    /**
     * Invokes the function and returns `default` if the function returns null.
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
                    return function(worldIn, pos)
                }
            }
        }
    }
}