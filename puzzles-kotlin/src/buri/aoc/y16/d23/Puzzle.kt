package buri.aoc.y16.d23

import buri.aoc.common.Assembunny
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
        assertRun(10880, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(479007440, 1, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val registers = Assembunny()
        registers["a"] = if (part == ONE) 7L else 12L
        registers.process(input.toMutableList())
        return registers["a"]
    }
}