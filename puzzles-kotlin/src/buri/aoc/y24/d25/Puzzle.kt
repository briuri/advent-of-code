package buri.aoc.y24.d25

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Grid
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(3, 1)
        assertRun(3114, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grids = mutableListOf<Grid<Char>>()
        var remaining = input.toMutableList()
        var blank = input.indexOf("")
        while (blank != -1) {
            val lockOrKey = remaining.subList(0, blank)
            grids.add(Grid.fromCharInput(lockOrKey))
            remaining = remaining.drop(blank + 1).toMutableList()
            blank = remaining.indexOf("")
        }

        val reducedKeys = mutableListOf<List<Int>>()
        val reducedLocks = mutableListOf<List<Int>>()
        for (grid in grids) {
            val l = mutableListOf<Int>()
            for (x in grid.xRange) {
                var count = 0
                for (y in grid.yRange) {
                    if (grid[x, y] == '#') {
                        count++
                    }
                }
                l.add(count - 1)
            }
            if (grid[0, 0] == '#') {
                reducedLocks.add(l)
            } else {
                reducedKeys.add(l)
            }
        }

        var count = 0
        for (key in reducedKeys) {
            for (lock in reducedLocks) {
                val fit = key.mapIndexed { index, it -> it + lock[index] }
                if (fit.all { it <= 5 }) {
                    count++
                }
            }
        }
        return count
    }
}