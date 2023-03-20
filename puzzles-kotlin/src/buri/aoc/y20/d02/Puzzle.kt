package buri.aoc.y20.d02

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
        assertRun(434, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(509, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var valid = 0
        for (line in input) {
            val tokens = line.split(" ")
            val rawNumbers = tokens[0].split("-")
            val range = rawNumbers[0].toInt()..rawNumbers[1].toInt()
            val pos1 = rawNumbers[0].toInt() - 1
            val pos2 = rawNumbers[1].toInt() - 1
            val letter = tokens[1].dropLast(1).first()
            val password = tokens[2]

            if (part.isOne()) {
                if (password.count { it == letter } in range) {
                    valid++
                }
            } else {
                if ((password[pos1] == letter && password[pos2] != letter) || (password[pos1] != letter && password[pos2] == letter)) {
                    valid++
                }
            }
        }
        return valid
    }
}