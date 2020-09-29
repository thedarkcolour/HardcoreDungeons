package thedarkcolour.hardcoredungeons.util

/**
 * Checks that [value] is in the specified [range]
 * and throws an exception if it is not. If the value
 * is valid, then this function returns it.
 *
 * @param value the value to check
 * @param range the range of possible values
 *
 * @return the value if it is in the range of possible values
 * @throws IllegalArgumentException if the value is not in the range of possible values
 */
fun checkRange(value: Int, range: IntRange): Int {
    if (value !in range) {
        throw IllegalArgumentException("Value $value not in specified range $range")
    }
    return value
}

/**
 * Overload of [checkNot] for [Int] values.
 *
 * Checks that [value] is not any of the [invalidValues]
 * and throws an exception if it is an invalid value. If the value
 * is valid, then this function returns it.
 *
 * @param value the value to check
 * @param invalidValues the invalid values that throw an exception
 *
 * @return the value if it is a valid value
 * @throws IllegalArgumentException if the value is an invalid value
 */
fun checkNot(value: Int, vararg invalidValues: Int): Int {
    if (value in invalidValues) {
        throw IllegalArgumentException("Value $value is invalid")
    }

    return value
}

/**
 * Overload of [checkNot] for [Long] values.
 *
 * Checks that [value] is not any of the [invalidValues]
 * and throws an exception if it is an invalid value. If the value
 * is valid, then this function returns it.
 *
 * @param value the value to check
 * @param invalidValues the invalid values that throw an exception
 *
 * @return the value if it is a valid value
 * @throws IllegalArgumentException if the value is an invalid value
 */
fun checkNot(value: Long, vararg invalidValues: Long): Long {
    if (value in invalidValues) {
        throw IllegalArgumentException("Value $value is invalid")
    }

    return value
}