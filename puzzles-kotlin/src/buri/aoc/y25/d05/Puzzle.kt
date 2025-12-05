package buri.aoc.y25.d05

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(3, 1)
        assertRun(558, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(14, 1)
        assertRun(344813017450467, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val fileSeparator = input.indexOf("")
        var ranges = mutableListOf<LongRange>()
        for (line in input.subList(0, fileSeparator)) {
            val numbers = line.extractLongs(false)
            ranges.add(numbers[0]..numbers[1])
        }
        if (part.isOne()) {
            var count = 0
            for (id in input.drop(fileSeparator + 1).map { it.toLong() }) {
                if (ranges.any { it.contains(id) }) {
                    count++
                }
            }
            return count
        }

        // Part 2
        ranges = ranges.sortedBy { it.first }.toMutableList()
        // Picked an arbitrary number of times to repeat instead of tracking whether the ranges changed.
        repeat(4) {
            val newRanges = mutableListOf<LongRange>()
            var i = 0
            while (i in ranges.indices) {
                val first = ranges[i]
                // Last range to consider.
                if (i == ranges.lastIndex) {
                    newRanges.add(first)
                } else {
                    val second = ranges[i + 1]
                    // Distinct ranges.
                    if (second.first > first.last) {
                        newRanges.add(first)
                    }
                    // Overlapping ranges can be merged.
                    else {
                        newRanges.add(first.first..maxOf(first.last, second.last))
                        i++
                    }
                }
                i++
            }
            ranges = newRanges
        }
        return ranges.toSet().sumOf { it.last - it.first + 1 }
    }
}