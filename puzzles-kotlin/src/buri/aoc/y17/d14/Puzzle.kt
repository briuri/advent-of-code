package buri.aoc.y17.d14

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Grid
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.Part.TWO
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(8108, 1)
        assertRun(8204, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(1242, 1)
        assertRun(1089, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val hashes = mutableListOf<String>()
        for (i in 0..127) {
            val newInput = mutableListOf<String>()
            newInput.add(input[0] + "-" + i.toString())
            hashes.add(toBinary(buri.aoc.y17.d10.Puzzle().run(TWO, newInput)))
        }
        if (part == ONE) {
            var sum = 0
            for (hash in hashes) {
                sum += hash.toCharArray().sumOf { it.digitToInt() }
            }
            return sum
        }

        val grid = Grid(128, 128)
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                grid[x, y] = hashes[y][x].toString()
            }
        }

        var regions = 0
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                if (grid[x, y] == "1") {
                    regions++
                    eraseRegion(grid, Pair(x, y))
                }
            }
        }
        return regions
    }

    /**
     * Converts a hex string into binary.
     */
    private fun toBinary(hexString: String): String {
        val builder = StringBuilder()
        for (value in hexString) {
            builder.append(value.digitToInt(16).toString(2).padStart(4, '0'))
        }
        return builder.toString()
    }

    /**
     * Erases all connected areas of a region.
     */
    private fun eraseRegion(grid: Grid, point: Pair<Int, Int>) {
        grid[point] = "0"
        for (neighbor in grid.getNeighbors(point).filter { grid[it] == "1" }) {
            eraseRegion(grid, neighbor)
        }
    }
}