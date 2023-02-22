package buri.aoc.y18.d03

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(4, 1)
        assertRun(118539, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3, 1)
        assertRun(1270, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val fabric = mutableMapOf<Pair<Int, Int>, Int>()
        val ids = mutableSetOf<Int>()
        val overlaps = mutableSetOf<Int>()
        for (line in input) {
            val numbers = line.extractInts()
            val id = numbers[0]
            ids.add(id)

            val xBounds = numbers[1] until numbers[1] + numbers[3]
            val yBounds = numbers[2] until numbers[2] + numbers[4]
            for (y in yBounds) {
                for (x in xBounds) {
                    val point = Pair(x, y)
                    // Overlaps marked with -1.
                    if (fabric[point] == -1) {
                        overlaps.add(id)
                    } else if (fabric[point] != null) {
                        overlaps.add(fabric[point]!!)
                        overlaps.add(id)
                        fabric[point] = -1
                    } else {
                        fabric[point] = id
                    }
                }
            }
        }
        return if (part.isOne()) {
            fabric.values.filter { it == -1 }.size
        } else {
            ids.filter { it !in overlaps }[0]
        }
    }
}