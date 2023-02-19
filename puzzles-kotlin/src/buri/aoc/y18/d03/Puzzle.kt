package buri.aoc.y18.d03

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
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
            val start = Pair(numbers[1], numbers[2])
            val dims = Pair(numbers[3], numbers[4])
            ids.add(id)

            for (y in start.second until start.second + dims.second) {
                for (x in start.first until start.first + dims.first) {
                    val point = Pair(x, y)
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
        return if (part == ONE) {
            fabric.values.filter { it == -1 }.size
        } else {
            ids.filter { it !in overlaps }[0]
        }
    }
}