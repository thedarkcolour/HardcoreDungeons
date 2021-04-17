package thedarkcolour.hardcoredungeons.elements

/**
 * Elements in combat for more strategic gear sets.
 *
 * @property strongAgainst the ordinal of the element this element is strong against.
 */
enum class HElement(val strongAgainst: Int) {
    FIRE(3),
    WATER(0),
    EARTH(1),
    WIND(2);

    fun effectiveAgainst(other: HElement): Boolean = strongAgainst == other.ordinal
}