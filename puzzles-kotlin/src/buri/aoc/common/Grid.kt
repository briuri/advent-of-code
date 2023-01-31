package buri.aoc.common

/**
 * Helper class for grid navigation
 *
 * @author Brian Uri!
 */
class Grid(private val size: Int) {
    private val grid = Array(size) { IntArray(size) }

    /**
     * Gets a value in the grid.
     */
    fun get(x: Int, y: Int): Int {
        return (grid[x][y])
    }

    /**
     * Sets a value in the grid.
     */
    fun set(x: Int, y: Int, value: Int) {
        grid[x][y] = value
    }

    /**
     * Sums all values in the grid.
     */
    fun getSum(): Int {
        var sum = 0
        for (x in 0 until size) {
            sum += grid[x].sum()
        }
        return sum
    }
}