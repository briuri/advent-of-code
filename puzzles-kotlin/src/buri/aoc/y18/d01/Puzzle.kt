package buri.aoc.y18.d01

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
        assertRun(477, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(390, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        if (part.isOne()) {
            return input.sumOf { it.toInt() }
        }
        val visited = mutableSetOf<Int>()
        var current = 0
        // Continuously loop over input, since cycle may not occur in first run.
        while (true) {
            for (frequency in input.map { it.toInt() }) {
                visited.add(current)
                current += frequency
                if (visited.contains(current)) {
                    return current
                }
            }
        }
    }
}