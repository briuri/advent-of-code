package buri.aoc.y16.d01

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
        assertRun(12, 1)
        assertRun(243, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(4, 2)
        assertRun(142, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val origin = Point2D(0, 0)
        val visited = mutableSetOf<Point2D<Int>>()
        val position = MutablePosition(origin, Direction.NORTH)
        for (command in input[0].split(", ")) {
            if (command[0] == 'L') {
                position.turnLeft()
            } else {
                position.turnRight()
            }
            for (i in 0 until command.drop(1).toInt()) {
                if (part.isTwo() && position.coords in visited) {
                    return position.coords.getManhattanDistance(origin)
                }
                visited.add(position.coords)
                position.move()
            }
        }
        return position.coords.getManhattanDistance(origin)
    }
}