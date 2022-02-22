package thedarkcolour.hardcoredungeons.entity.overworld.deer

import thedarkcolour.kotlinforforge.kotlin.enumSetOf

enum class DeerType(val type: Type) {
    // overworld patterns
    FOREST_STAG(Type.STAG),
    FOREST_DOE(Type.DOE),

    // castleton patterns
    BLUE_EYED_STAG(Type.STAG),
    BLUE_EYED_DOE(Type.DOE),
    BLUE_SPOTTED_STAG(Type.STAG),
    BLUE_SPOTTED_DOE(Type.DOE),
    BLUE_ALPHA(Type.ALPHA),
    PURPLE_EYED_STAG(Type.STAG),
    PURPLE_EYED_DOE(Type.DOE),
    PURPLE_SPOTTED_STAG(Type.STAG),
    PURPLE_SPOTTED_DOE(Type.DOE),
    PURPLE_ALPHA(Type.ALPHA);

    enum class Type {
        STAG, DOE, ALPHA
    }

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

    val isDoe: Boolean
        get() {
            return this.type == Type.DOE
        }

    val isStag: Boolean
        get() {
            return this.type == Type.STAG
        }

    val isAlpha: Boolean
        get() {
            return this.type == Type.ALPHA
        }

    val isBlue: Boolean
        get() {
            return BLUE_PATTERNS.contains(this)
        }

    companion object {
        // todo use arrays
        val BLUE_PATTERNS = enumSetOf(BLUE_EYED_STAG, BLUE_EYED_DOE, BLUE_SPOTTED_STAG, BLUE_SPOTTED_DOE, BLUE_ALPHA)
        val PURPLE_PATTERNS = enumSetOf(PURPLE_EYED_STAG, PURPLE_EYED_DOE, PURPLE_SPOTTED_STAG, PURPLE_SPOTTED_DOE, PURPLE_ALPHA)
    }
}