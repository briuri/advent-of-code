package buri.aoc.y22.d06

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
        assertRun(1833, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3425, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val string = input[0].toCharArray()
        val length = if (part.isOne()) 4 else 14
        for (i in length..string.lastIndex) {
            val list = string.slice(i - length until i)
            if (list.toSet().size == list.size) {
                return i
            }
        }
        return -1
    }
}