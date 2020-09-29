package thedarkcolour.hardcoredungeons.entity.castleton.deer

import thedarkcolour.kotlinforforge.kotlin.enumSetOf

enum class DeerPattern {
    BLUE_EYED_STAG,
    BLUE_EYED_DOE,
    BLUE_SPOTTED_STAG,
    BLUE_SPOTTED_DOE,
    BLUE_ALPHA,
    PURPLE_EYED_STAG,
    PURPLE_EYED_DOE,
    PURPLE_SPOTTED_STAG,
    PURPLE_SPOTTED_DOE,
    PURPLE_ALPHA;

    fun isFemale(): Boolean {
        return this == BLUE_EYED_DOE || this == BLUE_SPOTTED_DOE || this == PURPLE_EYED_DOE || this == PURPLE_SPOTTED_DOE
    }

    fun isStag(): Boolean {
        return this == BLUE_EYED_STAG || this == BLUE_SPOTTED_STAG || this == PURPLE_EYED_STAG || this == PURPLE_SPOTTED_STAG
    }

    fun isAlpha(): Boolean {
        return this == BLUE_ALPHA || this == PURPLE_ALPHA
    }

    fun isBlue(): Boolean {
        return BLUE_PATTERNS.contains(this)
    }

    companion object {
        val BLUE_PATTERNS = enumSetOf(BLUE_EYED_STAG, BLUE_EYED_DOE, BLUE_SPOTTED_STAG, BLUE_SPOTTED_DOE, BLUE_ALPHA)
        val PURPLE_PATTERNS = enumSetOf(PURPLE_EYED_STAG, PURPLE_EYED_DOE, PURPLE_SPOTTED_STAG, PURPLE_SPOTTED_DOE, PURPLE_ALPHA)
    }
}