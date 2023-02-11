package buri.aoc.y17.d04

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
        assertRun(383, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(3, 1)
        assertRun(265, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var valid = 0
        for (line in input) {
            var list = line.split(" ")
            if (part == TWO) {
                list = list.map { it.toCharArray().sorted().joinToString() }
            }
            if (list.size == list.toSet().size) {
                valid++
            }
        }
        return valid
    }
}