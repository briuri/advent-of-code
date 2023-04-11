package buri.aoc.y22.d01

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(68787, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(198041, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = mutableListOf<Int>()
        val backpacks = mutableListOf<Int>()
        for (line in input) {
            if (line.isEmpty()) {
                backpacks.add(numbers.sum())
                numbers.clear()
            } else {
                numbers.add(line.toInt())
            }
        }
        backpacks.add(numbers.sum())
        backpacks.sortDescending()
        return if (part.isOne()) backpacks.first() else (backpacks[0] + backpacks[1] + backpacks[2])
    }
}