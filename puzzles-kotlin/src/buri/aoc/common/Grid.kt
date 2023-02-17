package buri.aoc.common

import buri.aoc.common.Orientation.*
import java.lang.StringBuilder

/**
 * Helper class for grid navigation
 *
 * @author Brian Uri!
 */
class Grid(val width: Int, val height: Int) {
    private val grid = Array(width) { Array(height) { "0" } }

    /**
     * Sums all values in the grid.
     */
    fun getSum(): Int {
        var sum = 0
        for (x in 0 until width) {
            sum += grid[x].sumOf { it.toInt() }
        }
        return sum
    }

    /**
     * Creates a copy of this grid.
     */
    fun copy(orientation: Orientation = ORIGINAL): Grid {
        val copy = when (orientation) {
            CLOCKWISE_90, CLOCKWISE_270 -> Grid(height, width)
            else -> Grid(width, height)
        }
        for (y in 0 until height) {
            for (x in 0 until width) {
                val point = when (orientation) {
                    CLOCKWISE_90 -> Pair(height - y - 1, x)
                    CLOCKWISE_180 -> Pair(width - x - 1, height - y - 1)
                    CLOCKWISE_270 -> Pair(y, width - x - 1)
                    MIRROR_HORIZONTAL -> Pair(width - x - 1, y)
                    MIRROR_VERTICAL -> Pair(x, height - y - 1)
                    else -> Pair(x, y)
                }
                copy[point] = get(x, y)
            }
        }
        return copy
    }

    /**
     * Returns a smaller grid from inside this grid.
     */
    fun getSubGrid(start: Pair<Int, Int>, newWidth: Int, newHeight: Int): Grid {
        assert(isInBounds(start) && newWidth <= width && newHeight <= height)
        val grid = Grid(newWidth, newHeight)
        for (y in start.second until start.second + newHeight) {
            for (x in start.first until start.first + newWidth) {
                grid[x - start.first, y - start.second] = get(x, y)
            }
        }
        return grid
    }

    /**
     * Returns all neighbors of a point in bounds of the grid.
     */
    fun getNeighbors(current: Pair<Int, Int>, includeDiagonals: Boolean = false): List<Pair<Int, Int>> {
        return getNeighbors(current.first, current.second, includeDiagonals)
    }

    /**
     * Returns all neighbors of a point in bounds of the grid.
     */
    fun getNeighbors(x: Int, y: Int, includeDiagonals: Boolean = false): List<Pair<Int, Int>> {
        val list = Pair(x, y).getNeighbors(includeDiagonals)
        list.removeIf { pair -> !isInBounds(pair.first, pair.second) }
        return list
    }

    /**
     * Return true if the point is in bounds.
     */
    fun isInBounds(point: Pair<Int, Int>): Boolean {
        return isInBounds(point.first, point.second)
    }

    /**
     * Return true if the point is in bounds.
     */
    private fun isInBounds(x: Int, y: Int): Boolean {
        return (x in 0 until width) && (y in 0 until height)
    }

    /**
     * Gets a value in the grid.
     */
    operator fun get(x: Int, y: Int): String {
        return (grid[x][y])
    }

    /**
     * Gets a value in the grid.
     */
    operator fun get(pair: Pair<Int, Int>): String {
        return (grid[pair.first][pair.second])
    }

    /**
     * Sets a value in the grid.
     */
    operator fun set(point: Pair<Int, Int>, value: Int) {
        set(point.first, point.second, value)
    }

    /**
     * Sets a value in the grid.
     */
    operator fun set(point: Pair<Int, Int>, value: String) {
        set(point.first, point.second, value)
    }

    /**
     * Sets a value in the grid.
     */
    operator fun set(x: Int, y: Int, value: Int) {
        set(x, y, value.toString())
    }

    /**
     * Sets a value in the grid.
     */
    operator fun set(x: Int, y: Int, value: String) {
        grid[x][y] = value
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (y in 0 until height) {
            for (x in 0 until width) {
                builder.append(get(x, y))
            }
            builder.append("\n")
        }
        return builder.toString()
    }
}

enum class Orientation { ORIGINAL, CLOCKWISE_90, CLOCKWISE_180, CLOCKWISE_270, MIRROR_HORIZONTAL, MIRROR_VERTICAL }