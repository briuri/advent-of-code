package buri.aoc.y17.d09

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
        // Stack will only hold valid { < chars.
        val groupOpeners = ArrayDeque<Char>()
        var score = 0
        var garbageCount = 0
        var i = 0
        while (i in input[0].indices) {
            val value = input[0][i]
            val inGarbage = groupOpeners.isNotEmpty() && groupOpeners.first() == '<'

            // Handle cancellation
            if (value == '!') {
                i++
            }
            // Handle beginning/end of garbage
            else if (value == '<' && !inGarbage) {
                groupOpeners.addFirst(value)
            } else if (value == '>') {
                groupOpeners.removeFirst()
            }
            // Handle beginning/end of groups
            else if (value == '{' && !inGarbage) {
                groupOpeners.addFirst(value)
            } else if (value == '}' && !inGarbage) {
                score += groupOpeners.size
                groupOpeners.removeFirst()
            }
            // Count any other characters while we are in garbage
            else if (inGarbage) {
                garbageCount++
            }
            i++
        }
        return if (part.isOne()) score else garbageCount
    }
}