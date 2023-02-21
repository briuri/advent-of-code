package buri.aoc.y16.d20

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
        assertRun(19449262, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(119, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val minIP = 0L
        val maxIP = 4294967295L
        val ranges = mutableListOf<LongRange>()
        for (line in input) {
            val tokens = line.split("-")
            ranges.add(LongRange(tokens[0].toLong(), tokens[1].toLong()))
        }
        ranges.sortBy { it.first }

        val validRanges = mutableListOf<LongRange>()
        var current = minIP
        for (range in ranges) {
            if (current in range) {
                current = range.last + 1
            } else if (current < range.first) {
                validRanges.add(LongRange(current, range.first - 1))
                current = range.last + 1
            }
        }
        if (current < maxIP) {
            validRanges.add(LongRange(current, maxIP))
        }
        validRanges.sortBy { it.first }
        return if (part == ONE) {
            validRanges[0].first
        } else {
            validRanges.sumOf { it.last - it.first + 1 }
        }
    }
}