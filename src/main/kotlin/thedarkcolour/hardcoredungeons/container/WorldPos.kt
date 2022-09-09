package thedarkcolour.hardcoredungeons.container

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks

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
    fun <T> invoke(function: (Level, BlockPos) -> T): T?

    /**
     * Invokes the function and returns `default` if the function returns null.
     *
     * @param default the value to default to if the [function] returns null.
     */
    fun <T> invokeDefaulted(default: T, function: (Level, BlockPos) -> T?): T {
        return invoke(function) ?: default
    }

    /**
     * The [BlockState] at this [WorldPos]
     */
    val state: BlockState
        get() = invokeDefaulted(Blocks.AIR.defaultBlockState()) { worldIn, pos ->
            worldIn.getBlockState(pos)
        }

    companion object {
        /**
         * A [WorldPos] that always returns null.
         */
        val DUMMY = object : WorldPos {
            override fun <T> invoke(function: (Level, BlockPos) -> T): T? {
                return null
            }
        }

        /**
         * Helper method for creating a new [WorldPos].
         */
        fun of(worldIn: Level, pos: BlockPos): WorldPos {
            return object : WorldPos {
                override fun <T> invoke(function: (Level, BlockPos) -> T): T {
                    return function(worldIn, pos)
                }
            }
        }
    }
}