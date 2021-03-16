package thedarkcolour.hardcoredungeons.item

// todo check if weakness is needed probably not o.o
enum class HElement(val index: Int, val strength: Int) {
    FIRE(0, 3),
    WATER(1, 0),
    EARTH(2, 2),
    WIND(3, 0);

    fun effectiveAgainst(other: HElement): Boolean = strength == other.index
}