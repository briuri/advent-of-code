package buri.aoc.y23.d02

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.product
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(8, 1)
        assertRun(2283, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2286, 1)
        assertRun(78669, 0, true)
    }

    private val maxColors = mapOf("red" to 12, "green" to 13, "blue" to 14)

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        return input.sumOf { getNumber(part, it) }
    }

    /**
     * In part 1: Returns the id of the game if it is valid, or 0 if not.
     * In part 2: Returns the product of the number of cubes needed.
     */
    private fun getNumber(part: Part, line: String): Long {
        val needed = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
        val gameData = line.split(": ")[1].replace(";", ",")
        for (cubeType in gameData.split(", ")) {
            val (count, color) = cubeType.split(" ")
            needed[color] = needed[color]!!.coerceAtLeast(count.toInt())
        }

        if (part.isOne()) {
            return if (needed.any { it.value > maxColors[it.key]!! }) {
                0
            } else {
                line.extractInts()[0].toLong()
            }
        }
        return needed.values.product()
    }
}