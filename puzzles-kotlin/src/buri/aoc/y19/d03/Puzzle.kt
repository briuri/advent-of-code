package buri.aoc.y19.d03

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction
import buri.aoc.common.position.MutablePosition
import buri.aoc.common.position.Point2D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(159, 1)
        assertRun(1431, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(610, 1)
        assertRun(48012, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val origin = Point2D(0, 0)
        // Index to Point to Steps
        val wires = mutableMapOf<Int, MutableMap<Point2D<Int>, Int>>()
        for ((index, line) in input.withIndex()) {
            wires[index] = mutableMapOf()
            val position = MutablePosition(Point2D(0, 0), Direction.NORTH)
            var step = 0
            for (command in line.split(",")) {
                when (command[0]) {
                    'U' -> position.facing = Direction.NORTH
                    'D' -> position.facing = Direction.SOUTH
                    'L' -> position.facing = Direction.WEST
                    'R' -> position.facing = Direction.EAST
                }
                repeat(command.drop(1).toInt()) {
                    step++
                    position.move()
                    wires[index]!![position.coords] = step
                }
            }
        }
        val intersections = wires[0]!!.keys.intersect(wires[1]!!.keys)
        return if (part.isOne()) {
            intersections.minOf { it.getManhattanDistance(origin) }
        } else {
            intersections.minOf { wires[0]!![it]!! + wires[1]!![it]!! }
        }
    }
}