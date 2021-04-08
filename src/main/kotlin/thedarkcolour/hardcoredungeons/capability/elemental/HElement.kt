package thedarkcolour.hardcoredungeons.capability.elemental

enum class HElement(val strength: Int) {
    FIRE(3),
    WATER(0),
    EARTH(2),
    WIND(0);

    fun effectiveAgainst(other: HElement): Boolean = strength == other.ordinal
}