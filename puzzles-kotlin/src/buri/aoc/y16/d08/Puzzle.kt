package buri.aoc.y16.d08

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
        var grid = Grid(50, 6, 0)
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
                    } else {
                        nextGrid[which, 0] = grid[which, grid.height - 1]
                        for (y in (grid.height - 1) downTo 1) {
                            nextGrid[which, y] = grid[which, y - 1]
                        }
                    }
                    grid = nextGrid
                }
            }
        }
        return if (part.isOne()) grid.sum().toString() else grid.printLetters()
    }

    /**
     * Uses a block symbol to show letters.
     */
    private fun Grid<Int>.printLetters(): String {
        val output = StringBuilder()
        for (y in 0 until this.height) {
            for (x in 0 until this.width) {
                val value = if (this[x, y] == 1) '■' else ' '
                output.append(value)
            }
            output.append("\n")
        }
        return output.toString()
    }
}