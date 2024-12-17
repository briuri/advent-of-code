package buri.aoc.y24.d15

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction
import buri.aoc.common.position.Grid
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
        assertRun(2028, 1)
        assertRun(10092, 2)
        assertRun(1563092, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(618, 3)
        assertRun(9021, 2)
        assertRun(1582688, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var grid = Grid.fromCharInput(input.subList(0, input.indexOf("")))
        if (part.isTwo()) {
            val stretchedGrid = Grid(grid.width * 2, grid.height, '#')
            for (y in grid.yRange) {
                for (x in grid.xRange) {
                    val values = when (grid[x, y]) {
                        '#' -> Pair('#', '#')
                        'O' -> Pair('[', ']')
                        '.' -> Pair('.', '.')
                        else -> Pair('@', '.')
                    }
                    stretchedGrid[2 * x, y] = values.first
                    stretchedGrid[2 * x + 1, y] = values.second
                }
            }
            grid = stretchedGrid
        }
        val instructions = input.drop(input.indexOf("")).joinToString("")
        val robot = MutablePosition(grid.filter { it == '@' }.first(), Direction.NORTH)
        grid[robot.coords] = '.'

        for (nextFacing in instructions.map { Direction.from(it) }) {
            robot.facing = nextFacing
            grid.move(robot)
        }
        return grid.filter { it in listOf('O', '[') }.sumOf { it.y * 100 + it.x }
    }

    /**
     * Attempts to move the robot
     */
    private fun Grid<Char>.move(robot: MutablePosition) {
        val boxes = getAffectedBoxes(robot)
        if (canMove(robot, boxes)) {
            for (box in boxes) {
                val moved = Point2D(box.x + robot.facing.asSlope().x, box.y + robot.facing.asSlope().y)
                this[moved] = this[box]
                this[box] = '.'
            }
            robot.move()
        }
    }

    /**
     * Finds all boxes that will be in the path of a moving robot, ordered from farthest away to nearest.
     */
    private fun Grid<Char>.getAffectedBoxes(robot: MutablePosition): List<Point2D<Int>> {
        val slope = robot.facing.asSlope()
        val boxes = mutableSetOf<Point2D<Int>>()
        val frontier = ArrayDeque<Point2D<Int>>()
        frontier.add(Point2D(robot.coords.x + slope.x, robot.coords.y + slope.y))
        while (frontier.isNotEmpty()) {
            val current = frontier.removeFirst()
            if (this[current] in listOf('O', '[', ']')) {
                boxes.add(current)
                frontier.add(Point2D(current.x + slope.x, current.y + slope.y))
            }
            if (this[current] in listOf('[', ']') && robot.facing in listOf(Direction.NORTH, Direction.SOUTH)) {
                val halfBox = if (this[current] == '[') 1 else -1
                boxes.add(Point2D(current.x + halfBox, current.y))
                frontier.add(Point2D(current.x + halfBox + slope.x, current.y + slope.y))
            }
        }

        return when (robot.facing) {
            Direction.WEST -> boxes.sortedBy { it.x }
            Direction.EAST -> boxes.sortedByDescending { it.x }
            Direction.NORTH -> boxes.sortedBy { it.y }
            else -> boxes.sortedByDescending { it.y }
        }
    }

    /**
     * Returns true if all boxes can be shifted 1 square away in the direction.
     */
    private fun Grid<Char>.canMove(robot: MutablePosition, boxes: List<Point2D<Int>>): Boolean {
        val openSpaces = listOf('.', 'O', '[', ']')
        val slope = robot.facing.asSlope()
        if (boxes.isEmpty()) {
            val next = Point2D(robot.coords.x + slope.x, robot.coords.y + slope.y)
            return (this[next] in openSpaces)
        }
        if (boxes.size == 1) {
            val box = boxes.first()
            val next = Point2D(box.x + slope.x, box.y + slope.y)
            return (this[next] in openSpaces)
        }
        return (canMove(robot, boxes.subList(0, 1)) && canMove(robot, boxes.drop(1)))
    }
}