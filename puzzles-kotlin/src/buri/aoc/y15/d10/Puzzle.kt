package buri.aoc.y15.d10

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
        assertRun(360154, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(5103798, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var number = input[0]
        val times = if (part == ONE) 40 else 50
        val buffer = StringBuilder()
        for (time in 0 until times) {
            buffer.clear()
            var i = 0
            while (i < number.length) {
                val value = number[i]
                var count = 1
                i++
                while (i < number.length && value == number[i]) {
                    i++
                    count++
                }
                buffer.append(count)
                buffer.append(value)
            }
            number = buffer.toString()
        }
        return number.length
    }
}