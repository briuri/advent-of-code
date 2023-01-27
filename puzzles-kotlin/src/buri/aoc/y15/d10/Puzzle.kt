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
        for (i in 0 until times) {
            val buffer = StringBuilder()
            var index = 0
            while (index < number.length) {
                val value = number[index]
                var count = 1
                index++
                while (index < number.length && number[index]  == value) {
                    index++
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