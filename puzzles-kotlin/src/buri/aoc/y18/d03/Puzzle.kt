package buri.aoc.y18.d03

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.*
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
            val tokens = line.split(" ")
            val id = tokens[0].drop(1).toInt()
            val rawStart = tokens[2].dropLast(1).split(",")
            val start = Pair(rawStart[0].toInt(), rawStart[1].toInt())
            val rawDims = tokens[3].split("x")
            val dims = Pair(rawDims[0].toInt(), rawDims[1].toInt())

            ids.add(id)
            for (y in start.second until start.second + dims.second) {
                for (x in start.first until start.first + dims.first) {
                    val point = Pair(x, y)
                    if (fabric[point] == -1) {
                        overlaps.add(id)
                    }
                    else if (fabric[point] != null) {
                        overlaps.add(fabric[point]!!)
                        overlaps.add(id)
                        fabric[point] = -1
                    }
                    else {
                        fabric[point] = id
                    }
                }
            }
        }
        return if (part == ONE) {
            fabric.values.filter { it == -1}.size
        }
        else {
            ids.filter { it !in overlaps }[0]
        }
    }
}