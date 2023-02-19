package buri.aoc.y17.d06

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.TWO
import buri.aoc.common.extractInts
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(5, 1)
        assertRun(7864, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(4, 1)
        assertRun(1695, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val banks = mutableListOf<Int>()
        banks.addAll(input[0].extractInts())

        val visited = mutableSetOf<String>()
        var steps = 0
        var findingCycle = false
        while (true) {
            visited.add(banks.toString())
            var target = banks.indexOfFirst { it == banks.max() }
            var blocks = banks[target]
            banks[target] = 0
            while (blocks > 0) {
                target++
                if (target > banks.lastIndex) {
                    target = 0
                }
                banks[target] += 1
                blocks--
            }
            steps++

            // In part two, reset the states and find the next cycle.
            if (visited.contains(banks.toString())) {
                if (part == TWO && !findingCycle) {
                    steps = 0
                    visited.clear()
                    findingCycle = true
                } else {
                    return steps
                }
            }
        }
    }
}