package buri.aoc.y16.d18

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.TWO
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(38, 1)
        assertRun(1956, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(19995121, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var rows = if (input[0].length < 20) 10 else 40
        if (part == TWO) {
            rows = 400_000
        }

        var row = input[0]
        var safe = 0L
        val trapSignals = listOf("^^.", ".^^", "^..", "..^")
        for (i in 1..rows) {
            safe += getSafeCount(row)
            val nextRow = StringBuilder()
            for (j in row.indices) {
                nextRow.append(if (getTrapBasis(row, j) in trapSignals) '^' else '.')
            }
            row = nextRow.toString()
        }
        return safe
    }

    /**
     * Returns the three spots used to determine a new trap.
     */
    private fun getTrapBasis(row: String, index: Int): String {
        val builder = StringBuilder()
        builder.append(if (index == 0) '.' else row[index - 1])
        builder.append(row[index])
        builder.append(if (index == row.lastIndex) '.' else row[index + 1])
        return builder.toString()
    }

    /**
     * Counts the number of safe spots in a row.
     */
    private fun getSafeCount(row: String): Int {
        return row.filter { it == '.' }.length
    }
}