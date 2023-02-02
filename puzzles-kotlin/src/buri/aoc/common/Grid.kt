package buri.aoc.common

/**
 * Helper class for grid navigation
 *
 * @author Brian Uri!
 */
class Grid(val width: Int, val height: Int) {
    private val grid = Array(width) { Array(height) { "0" } }

    /**
     * Gets a value in the grid.
     */
    fun get(x: Int, y: Int): String {
        return (grid[x][y])
    }

    /**
     * Gets a value in the grid.
     */
    fun get(pair: Pair<Int, Int>): String {
        return (grid[pair.first][pair.second])
    }

    /**
     * Sets a value in the grid.
     */
    fun set(x: Int, y: Int, value: Int) {
        set(x, y, value.toString())
    }

    /**
     * Sets a value in the grid.
     */
    fun set(x: Int, y: Int, value: String) {
        grid[x][y] = value
    }

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
     * Returns all neighbors of a point.
     */
    fun getNeighbors(x: Int, y: Int, includeDiagonals: Boolean = false): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()
        list.add(Pair(x - 1, y))
        list.add(Pair(x + 1, y))
        list.add(Pair(x, y - 1))
        list.add(Pair(x, y + 1))
        if (includeDiagonals) {
            list.add(Pair(x - 1, y - 1))
            list.add(Pair(x + 1, y - 1))
            list.add(Pair(x - 1, y + 1))
            list.add(Pair(x + 1, y + 1))
        }
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
    fun isInBounds(x: Int, y: Int): Boolean {
        return (x in 0 until width) && (y in 0 until height)
    }

    /**
     * Prints the grid.
     */
    fun print() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(get(x, y))
            }
            print("\n")
        }
    }
}