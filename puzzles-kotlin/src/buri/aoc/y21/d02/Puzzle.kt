package buri.aoc.y21.d02

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
        assertRun(150, 1)
        assertRun(2120749, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(900, 1)
        assertRun(2138382217, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var x = 0L
        var depth = 0L
        var aim = 0L
        for (line in input) {
            val tokens = line.split(" ")
            val amount = tokens[1].toInt()
            when (tokens[0]) {
                "forward" -> {
                    x += amount
                    if (part.isTwo()) {
                        depth += (aim * amount)
                    }
                }

                "down" -> {
                    if (part.isOne()) {
                        depth += amount
                    } else {
                        aim += amount
                    }
                }

                "up" -> {
                    if (part.isOne()) {
                        depth -= amount
                    } else {
                        aim -= amount
                    }
                }
            }
        }
        return x * depth
    }
}