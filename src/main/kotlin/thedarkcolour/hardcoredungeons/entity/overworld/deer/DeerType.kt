package thedarkcolour.hardcoredungeons.entity.overworld.deer

import thedarkcolour.kotlinforforge.kotlin.enumSetOf

enum class DeerType(
    val isDoe: Boolean = false,
    val isStag: Boolean = false,
    val isAlpha: Boolean = false,
    val isBlue: Boolean = false,
    val isSpotted: Boolean = false,
    val isForest: Boolean = false,
) {
    // overworld patterns
    FOREST_STAG(isStag = true, isForest = true),
    FOREST_DOE(isDoe = true, isForest = true),

    // castleton patterns
    BLUE_EYED_STAG(isStag = true, isBlue = true),
    BLUE_EYED_DOE(isDoe = true, isBlue = true),
    BLUE_SPOTTED_STAG(isStag = true, isSpotted = true, isBlue = true),
    BLUE_SPOTTED_DOE(isDoe = true, isSpotted = true, isBlue = true),
    BLUE_ALPHA(isAlpha = true, isBlue = true),
    PURPLE_EYED_STAG(isStag = true),
    PURPLE_EYED_DOE(isDoe = true),
    PURPLE_SPOTTED_STAG(isStag = true, isSpotted = true),
    PURPLE_SPOTTED_DOE(isDoe = true, isSpotted = true),
    PURPLE_ALPHA(isAlpha = true);

    fun toPurple(): DeerType {
        return when (this) {
            BLUE_EYED_STAG -> PURPLE_EYED_STAG
            BLUE_EYED_DOE -> PURPLE_EYED_DOE
            BLUE_SPOTTED_STAG -> PURPLE_SPOTTED_STAG
            BLUE_SPOTTED_DOE -> PURPLE_SPOTTED_DOE
            BLUE_ALPHA -> PURPLE_ALPHA

            else -> this
        }
    }

    fun toBlue(): DeerType {
        return when (this) {
            PURPLE_EYED_STAG -> BLUE_EYED_STAG
            PURPLE_EYED_DOE -> BLUE_EYED_DOE
            PURPLE_SPOTTED_STAG -> BLUE_SPOTTED_STAG
            PURPLE_SPOTTED_DOE -> BLUE_SPOTTED_DOE
            PURPLE_ALPHA -> BLUE_ALPHA

            else -> this
        }
    }

    companion object {
        // todo use arrays
        val BLUE_PATTERNS = enumSetOf(BLUE_EYED_STAG, BLUE_EYED_DOE, BLUE_SPOTTED_STAG, BLUE_SPOTTED_DOE, BLUE_ALPHA)
        val PURPLE_PATTERNS = enumSetOf(PURPLE_EYED_STAG, PURPLE_EYED_DOE, PURPLE_SPOTTED_STAG, PURPLE_SPOTTED_DOE, PURPLE_ALPHA)
    }
}