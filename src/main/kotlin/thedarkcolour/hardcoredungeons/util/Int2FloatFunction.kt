package thedarkcolour.hardcoredungeons.util

fun interface Int2FloatFunction {
    operator fun invoke(int: Int): Float

    companion object {
        @JvmStatic
        val ZERO = Int2FloatFunction { 0.0f }
    }
}