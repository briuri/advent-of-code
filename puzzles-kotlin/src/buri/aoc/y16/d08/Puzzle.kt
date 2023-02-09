package buri.aoc.y16.d08

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Grid
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("128", 0, true)
    }
    @Test
    fun runPart2() {
        // EOARGPHYAO
        assertRun("■■■■  ■■   ■■  ■■■   ■■  ■■■  ■  ■ ■   ■ ■■   ■■ ", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        var grid = Grid(50, 6)
        for (line in input) {
            if (line.startsWith("rect")) {
                val w = line.split(" ")[1].split("x")[0].toInt()
                val h = line.split(" ")[1].split("x")[1].toInt()
                for (y in 0 until h) {
                    for (x in 0 until w) {
                        grid[x, y] = 1
                    }
                }
            } else {
                val isRow = line.contains("rotate row")
                val which = line.split("=")[1].split(" ")[0].toInt()
                val amount = line.split("by ")[1].toInt()
                for (i in 0 until amount) {
                    val nextGrid = grid.copy()
                    if (isRow) {
                        nextGrid[0, which] = grid[grid.width - 1, which]
                        for (x in (grid.width - 1) downTo 1) {
                            nextGrid[x, which] = grid[x - 1, which]
                        }
                    }
                    else {
                        nextGrid[which, 0] = grid[which, grid.height - 1]
                        for (y in (grid.height - 1) downTo 1) {
                            nextGrid[which, y] = grid[which, y - 1]
                        }
                    }
                    grid = nextGrid
                }
            }
        }
        return if (part == ONE) grid.getSum().toString() else printLetters(grid)
    }

    /**
     * Uses a block symbol to show letters.
     */
    private fun printLetters(grid: Grid): String {
        val builder = StringBuilder()
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                val value = if (grid[x, y] == "1") '■' else ' '
                builder.append(value)
            }
            builder.append("\n")
        }
        return builder.toString()
    }
}