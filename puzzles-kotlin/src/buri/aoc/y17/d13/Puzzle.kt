package buri.aoc.y17.d13

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun(24, 1)
        assertRun(2604, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(10, 1)
        assertRun(3941460, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val scanners = mutableSetOf<Scanner>()
        for (line in input) {
            val numbers = line.extractInts()
            scanners.add(Scanner(numbers[0], numbers[1]))
        }

        if (part.isOne()) {
            // Returns the sum of severity scores for each scanner at the top.
            return scanners.filter { it.isAtTop(it.depth) }.sumOf { it.getSeverity() }
        }

        var delay = 0
        while (scanners.any { it.isAtTop(it.depth + delay) }) {
            delay++
        }
        return delay
    }
}

data class Scanner(val depth: Int, val range: Int) {
    /**
     * Returns true if the scanner is at the top of its layer at this time.
     *
     * depth: top period
     * 2: 0 2 4
     * 3: 0 4 8
     * 4: 0 6 12
     * 5: 0 8 16
     */
    fun isAtTop(time: Int): Boolean {
        return (time % ((range * 2) - 2) == 0)
    }

    /**
     * Returns the severity score of getting caught.
     */
    fun getSeverity(): Int {
        return depth * range
    }
}