package buri.aoc.common.position

/**
 * The rectangular bounds for a set of points.
 *
 * @author Brian Uri!
 */
data class Bounds(val points: Set<Pair<Int, Int>>) {
    val x: IntRange
    val y: IntRange

    init {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        for (point in points) {
            minX = minX.coerceAtMost(point.first)
            maxX = maxX.coerceAtLeast(point.first)
            minY = minY.coerceAtMost(point.second)
            maxY = maxY.coerceAtLeast(point.second)
        }
        x = minX..maxX
        y = minY..maxY
    }

    /**
     * Returns the area of the rectangle.
     */
    fun getArea(): Long {
        return (x.last - x.first).toLong() * (y.last - y.first).toLong()
    }
}