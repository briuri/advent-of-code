package buri.aoc.y19.d08

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
        assertRun("1620", 0, true)
    }

    @Test
    fun runPart2() {
        // BCYEF
        assertRun("■■■   ■■  ■   ■■■■■ ■■■■ ", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val width = 25
        val height = 6
        val chunks = input[0].chunked(width * height)
        if (part.isOne()) {
            val chunk = chunks.minBy { it.count { value -> value == '0' } }
            return (chunk.count { it == '1' } * chunk.count { it == '2' }).toString()
        }

        val image = chunks[0].toCharArray()
        for (chunk in chunks.drop(1)) {
            for ((index, value) in chunk.withIndex()) {
                if (image[index] == '2') {
                    image[index] = value
                }
            }
        }
        val grid = Grid(width, height, ' ')
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                val index = y * grid.width + x
                if (image[index] == '1') {
                    grid[x, y] = '■'
                }
            }
        }
        return grid.toString()
    }
}