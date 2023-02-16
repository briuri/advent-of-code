package buri.aoc.y17.d09

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test
import java.util.*

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(16827, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(7298, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        // Stack will only hold valid {} <> chars.
        val stack = Stack<Char>()
        var score = 0
        var garbageCount = 0
        var i = 0
        while (i in input[0].indices) {
            val value = input[0][i]
            val inGarbage = stack.isNotEmpty() && stack.peek() == '<'

            // Handle cancellation
            if (value == '!') {
                i++
            }
            // Handle beginning/end of garbage
            else if (value == '<' && !inGarbage) {
                stack.push(value)
            } else if (value == '>') {
                stack.pop()
            }
            // Handle beginning/end of groups
            else if (value == '{' && !inGarbage) {
                stack.push(value)
            } else if (value == '}' && !inGarbage) {
                score += stack.size
                stack.pop()
            }
            // Count any other characters while we are in garbage
            else if (inGarbage) {
                garbageCount++
            }
            i++
        }
        return if (part == ONE) score else garbageCount
    }
}