package buri.aoc.y23.d12

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
        assertRun(18, 2)
        assertRun(21, 1)
        assertRun(7307, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3548, 2)
        assertRun(525152, 1)
        assertRun(3415570893842, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val part2Repeat = 5

        var sum = 0L
        for (line in input) {
            val tokens = line.split(" ")
            // Add a . on the end to simplify group parsing.
            val row = if (part.isOne()) {
                "${tokens[0]}."
            } else {
                (tokens[0] + '?').repeat(part2Repeat).dropLast(1) + '.'
            }
            val expected = tokens[1].extractInts().toMutableList()
            if (part.isTwo()) {
                repeat(part2Repeat - 1) {
                    expected.addAll(tokens[1].extractInts())
                }
            }

            sum += countWays(expected, mutableMapOf<String, Long>(), row)
        }
        return sum
    }

    /**
     * Recursively traverses the row from left to right to try # or . as a replacement for ?
     *
     * Symbols in comments show parenthesis around the current square.
     *
     * previous is damaged v
     *                     #(.)
     *                       ^ current is operational
     */
    private fun countWays(
        expected: List<Int>, visited: MutableMap<String, Long>, row: String,
        groupsSoFar: Int = 0, partialGroupSize: Int = 0
    ): Long {
        val visitedKey = "$row-$groupsSoFar-$partialGroupSize"
        if (visitedKey in visited) {
            return visited[visitedKey]!!
        }

        val count: Long
        val nextGroupSize = if (groupsSoFar !in expected.indices) 0 else expected[groupsSoFar]

        // Base Case: We have reached the end (always a . because of input alteration)
        if (row.length == 1) {
            // #(.) Just ended a group of the correct size.
            count = if (groupsSoFar == expected.size - 1 && partialGroupSize == nextGroupSize) {
                1
            }
            // .(.) No groups in progress and all groups are done.
            else if (groupsSoFar == expected.size && partialGroupSize == 0) {
                1
            }
            // Wrong number of groups or last group was wrong size
            else {
                0
            }
        }

        // Case #1: (#) Damaged spring
        else if (row.first() == '#') {
            // .(#) Start a new group
            // #(#) Continue a group.
            count = if (partialGroupSize + 1 <= nextGroupSize) {
                countWays(expected, visited, row.drop(1), groupsSoFar, partialGroupSize + 1)
            }
            // Cannot start or continue a group because we would exceed desired group size.
            else {
                0
            }
        }

        // Case #2: (.) Operational spring not at the end of the row.
        else if (row.first() == '.') {
            // #(.) A group just ended.
            count = if (partialGroupSize > 0) {
                // Group must be the right size to count.
                if (partialGroupSize == nextGroupSize) {
                    countWays(expected, visited, row.drop(1), groupsSoFar + 1, 0)
                }
                // Group was wrong size.
                else {
                    0
                }
            }
            // .(.) No groups are currently in progress. Just keep going.
            else {
                countWays(expected, visited, row.drop(1), groupsSoFar, 0)
            }
        }

        // Case #3: (?) Unknown spring
        else {
            // .(#) or #(#) Replace with a damaged one. Start or continue a group.
            val dCount = countWays(expected, visited, row.drop(1), groupsSoFar, partialGroupSize + 1)
            val oCount = when (partialGroupSize) {
                // .(.) Replace with an operational one. No groups in progress. Just keep going.
                0 -> countWays(expected, visited, row.drop(1), groupsSoFar, 0)
                // #(.) Replaced with an operational one, ending a group in progress.
                nextGroupSize -> countWays(expected, visited, row.drop(1), groupsSoFar + 1, 0)
                // Operational spring cannot be used here because group in progress isn't big enough yet.
                else -> 0
            }
            count = dCount + oCount
        }

        visited[visitedKey] = count
        return count
    }
}