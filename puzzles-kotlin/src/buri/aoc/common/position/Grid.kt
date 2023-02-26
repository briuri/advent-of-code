package buri.aoc.common.position

import buri.aoc.common.getNeighbors
import buri.aoc.common.position.Orientation.*

/**
 * Helper class for grid navigation. Can hold Ints or Chars.
 *
 * @author Brian Uri!
 */
open class Grid<T>(val width: Int, val height: Int, private val defaultValue: T) {
    private val grid = MutableList(width * height) { defaultValue }

    init {
        if (defaultValue !is Int && defaultValue !is Char) {
            throw IllegalArgumentException("Only Int and Chars can be stored in grids.")
        }
    }

    /**
     * Sums all actual numbers in the grid.
     */
    fun sum(): Int {
        var sum = 0
        for (value in grid.filter { it is Int }) {
            sum += value as Int
        }
        return sum
    }

    /**
     * Counts occurrences of some value.
     */
    fun count(value: T): Int {
        return grid.filter { it == value }.size
    }

    /**
     * Creates a copy of this grid.
     */
    fun copy(orientation: Orientation = ORIGINAL): Grid<T> {
        val copy = when (orientation) {
            CLOCKWISE_90, CLOCKWISE_270 -> Grid(height, width, defaultValue)
            else -> Grid(width, height, defaultValue)
        }
        for (y in 0 until height) {
            for (x in 0 until width) {
                val point = when (orientation) {
                    CLOCKWISE_90 -> Pair(height - y - 1, x)
                    CLOCKWISE_180 -> Pair(width - x - 1, height - y - 1)
                    CLOCKWISE_270 -> Pair(y, width - x - 1)
                    MIRROR_H -> Pair(width - x - 1, y)
                    MIRROR_V -> Pair(x, height - y - 1)
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
    fun getSubGrid(start: Pair<Int, Int>, newWidth: Int, newHeight: Int): Grid<T> {
        assert(isInBounds(start) && newWidth <= width && newHeight <= height)
        val grid = Grid(newWidth, newHeight, defaultValue)
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
    fun getNeighbors(x: Int, y: Int, includeDiagonals: Boolean = false): List<Pair<Int, Int>> {
        val list = Pair(x, y).getNeighbors(includeDiagonals)
        list.removeIf { pair -> !isInBounds(pair.first, pair.second) }
        return list
    }

    /**
     * Returns all neighbors of a point in bounds of the grid.
     */
    fun getNeighbors(current: Pair<Int, Int>, includeDiagonals: Boolean = false): List<Pair<Int, Int>> {
        return getNeighbors(current.first, current.second, includeDiagonals)
    }

    /**
     * Return true if the point is in bounds.
     */
    fun isInBounds(x: Int, y: Int): Boolean {
        return (x in 0 until width) && (y in 0 until height)
    }
    fun isInBounds(point: Pair<Int, Int>): Boolean = isInBounds(point.first, point.second)

    /**
     * Gets a value in the grid.
     */
    operator fun get(x: Int, y: Int): T {
        val index = toIndex(x, y)
        if (index !in grid.indices) {
            throw IndexOutOfBoundsException("($x,$y) is out of bounds.")
        }
        return grid[toIndex(x, y)]
    }
    operator fun get(pair: Pair<Int, Int>): T = get(pair.first, pair.second)

    /**
     * Sets a value in the grid.
     */
    operator fun set(x: Int, y: Int, value: T) {
        grid[toIndex(x, y)] = value
    }

    operator fun set(point: Pair<Int, Int>, value: T) = set(point.first, point.second, value)

    /**
     * Converts a 2D coordinate into a 1D index.
     */
    private fun toIndex(x: Int, y: Int): Int = y * width + x

    /**
     * Adds space between numeric values in the grid output.
     */
    override fun toString(): String {
        val output = StringBuilder()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val value = get(x, y)
                output.append(get(x, y))
                if (value is Int) {
                    output.append("\t")
                }
            }
            output.append("\n")
        }
        return output.toString()
    }
}

enum class Orientation { ORIGINAL, CLOCKWISE_90, CLOCKWISE_180, CLOCKWISE_270, MIRROR_H, MIRROR_V }