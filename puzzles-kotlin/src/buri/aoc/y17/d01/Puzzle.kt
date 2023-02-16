package buri.aoc.y17.d01

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
        assertRun(1175, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1166, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var sum = 0
        val sequence = input[0]
        for (i in sequence.indices) {
            val next = if (part == ONE) {
                if (i == sequence.lastIndex) 0 else i + 1
            } else {
                (i + (sequence.length / 2)) % sequence.length
            }
            if (sequence[i] == sequence[next]) {
                sum += sequence[i].digitToInt()
            }
        }
        return sum
    }
}