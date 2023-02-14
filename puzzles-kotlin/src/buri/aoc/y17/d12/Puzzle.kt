package buri.aoc.y17.d12

import buri.aoc.common.BasePuzzle
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
        assertRun(283, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(195, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val visited = mutableSetOf<Int>()
        var groupCount = 0
        var testProgram = 0
        while (testProgram in input.indices) {
            val frontier = mutableListOf<Int>()
            frontier.add(testProgram)

            var current: Int?
            while (frontier.isNotEmpty()) {
                current = frontier.removeFirst()
                if (!visited.contains(current)) {
                    visited.add(current)
                    val next = input[current].split(" <-> ")[1].split(", ")
                    frontier.addAll(next.map { it.toInt() })
                }
            }
            // In part ONE, quit after group 0 is accounted for.
            if (part == ONE) {
                return visited.size
            }
            groupCount++

            // Cycle through input to the next unconnected program.
            while (visited.contains(testProgram)) {
                testProgram++
            }
        }
        return groupCount
    }
}