package buri.aoc.common.position

import kotlin.math.absoluteValue

/**
 * 2D, 3D, and 4D points using Ints and Longs
 *
 * @author Brian Uri!
 */

/**
 * Base Class
 */
abstract class BaseTuple<T> {

    /**
     * Enforce type restrictions (only Ints and Longs).
     */
    protected fun <T> assertValidType(a: T) {
        if (a !is Int && a !is Long) {
            throw IllegalArgumentException("Only Int and Longs can be stored in tuples.")
        }
    }

    /**
     * Does a comparison of two generics that are Ints or Longs.
     */
    protected fun <T> compareNumber(a: T, b: T): Int {
        return if (a is Int && b is Int) {
            a.compareTo(b)
        } else if (a is Long && b is Long) {
            a.compareTo(b)
        } else {
            0
        }
    }

    /**
     * Gets absolute value of difference of two generics that are Ints or Longs.
     */
    protected fun <T> abs(a: T, b: T): Int {
        return if (a is Int && b is Int) {
            (a - b).absoluteValue
        } else if (a is Long && b is Long) {
            (a - b).absoluteValue.toInt()
        } else {
            0
        }
    }
}

/**
 * 2D Points
 */
data class Point2D<T>(val x: T, val y: T) : BaseTuple<T>(), Comparable<Point2D<T>> {

    init {
        assertValidType(x)
    }

    /**
     * Returns the Manhattan distance to another point.
     */
    fun getManhattanDistance(that: Point2D<T>): Int {
        return (abs(x, that.x) + abs(y, that.y))
    }

    /**
     * Compares using reading order (Top to Bottom, Left to Right)
     */
    override fun compareTo(other: Point2D<T>): Int {
        var compare = compareNumber(y, other.y)
        if (compare == 0) {
            compare = compareNumber(x, other.x)
        }
        return compare
    }

    override fun toString(): String {
        return "$x,$y"
    }
}

/**
 * The rectangular bounds for a set of Int points.
 *
 * @author Brian Uri!
 */
class Bounds(points: Set<Point2D<Int>>) {
    val x: IntRange
    val y: IntRange
    val area: Long
        get() = (x.last - x.first).toLong() * (y.last - y.first).toLong()

    init {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        for (point in points) {
            minX = minX.coerceAtMost(point.x)
            maxX = maxX.coerceAtLeast(point.x)
            minY = minY.coerceAtMost(point.y)
            maxY = maxY.coerceAtLeast(point.y)
        }
        x = minX..maxX
        y = minY..maxY
    }
}

/**
 * Gets neighbors of an Int point in reading order.
 */
fun Point2D<Int>.getNeighbors(includeDiagonals: Boolean = false): MutableList<Point2D<Int>> {
    val list = mutableListOf<Point2D<Int>>()
    if (includeDiagonals) {
        list.add(Point2D(x - 1, y - 1))
    }
    list.add(Point2D(x, y - 1))
    if (includeDiagonals) {
        list.add(Point2D(x + 1, y - 1))
    }
    list.add(Point2D(x - 1, y))
    list.add(Point2D(x + 1, y))
    if (includeDiagonals) {
        list.add(Point2D(x - 1, y + 1))
    }
    list.add(Point2D(x, y + 1))
    if (includeDiagonals) {
        list.add(Point2D(x + 1, y + 1))
    }
    return list
}

/**
 * 3D Points
 */
data class Point3D<T>(val x: T, val y: T, val z: T) : BaseTuple<T>(), Comparable<Point3D<T>> {

    init {
        assertValidType(x)
    }

    /**
     * Returns the Manhattan distance to another point.
     */
    fun getManhattanDistance(that: Point3D<T>): Int {
        return (abs(x, that.x) + abs(y, that.y) + abs(z, that.z))
    }

    /**
     * Compares using reading order (Top to Bottom, Left to Right)
     */
    override fun compareTo(other: Point3D<T>): Int {
        var compare = compareNumber(z, other.z)
        if (compare == 0) {
            compare = compareNumber(y, other.y)
        }
        if (compare == 0) {
            compare = compareNumber(x, other.x)
        }
        return compare
    }

    override fun toString(): String {
        return "$x,$y,$z"
    }
}

/**
 * Gets neighbors of an Int point in reading order, in a single z plane.
 */
fun Point3D<Int>.getNeighbors(includeDiagonals: Boolean = false): MutableList<Point3D<Int>> {
    val list = mutableListOf<Point3D<Int>>()
    if (includeDiagonals) {
        list.add(Point3D(x - 1, y - 1, this.z))
    }
    list.add(Point3D(x, y - 1, this.z))
    if (includeDiagonals) {
        list.add(Point3D(x + 1, y - 1, this.z))
    }
    list.add(Point3D(x - 1, y, this.z))
    list.add(Point3D(x + 1, y, this.z))
    if (includeDiagonals) {
        list.add(Point3D(x - 1, y + 1, this.z))
    }
    list.add(Point3D(x, y + 1, this.z))
    if (includeDiagonals) {
        list.add(Point3D(x + 1, y + 1, this.z))
    }
    return list
}

/**
 * 4D Points
 */
data class Point4D<T>(val x: T, val y: T, val z: T, val t: T) : BaseTuple<T>(), Comparable<Point4D<T>> {

    init {
        assertValidType(x)
    }

    /**
     * Returns the Manhattan distance to another point.
     */
    fun getManhattanDistance(that: Point4D<T>): Int {
        return (abs(x, that.x) + abs(y, that.y) + abs(z, that.z) + abs(t, that.t))
    }

    /**
     * Compares using reading order (Top to Bottom, Left to Right)
     */
    override fun compareTo(other: Point4D<T>): Int {
        var compare = compareNumber(t, other.t)
        if (compare == 0) {
            compare = compareNumber(z, other.z)
        }
        if (compare == 0) {
            compare = compareNumber(y, other.y)
        }
        if (compare == 0) {
            compare = compareNumber(x, other.x)
        }
        return compare
    }

    override fun toString(): String {
        return "$x,$y,$z,$t"
    }
}