package buri.aoc.y16.d01

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test
import kotlin.math.absoluteValue

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
        val visited = mutableSetOf<Pair<Int, Int>>()
        val position = Position(Pair(0, 0), Direction.NORTH)
        for (command in input[0].split(", ")) {
            position.turn(command[0])
            for (i in 0 until command.drop(1).toInt()) {
                if (part == Part.TWO && visited.contains(position.coords)) {
                    return position.getBlocks()
                }
                visited.add(position.coords)
                position.move()
            }
        }
        return position.getBlocks()
    }
}
enum class Direction { NORTH, EAST, SOUTH, WEST }
data class Position(var coords: Pair<Int, Int>, var facing: Direction) {

    /**
     * Move one square in the current direction.
     */
    fun move() {
        coords = when (facing) {
            Direction.NORTH -> coords.copy(second = coords.second + 1)
            Direction.EAST -> coords.copy(first = coords.first + 1)
            Direction.SOUTH -> coords.copy(second = coords.second - 1)
            Direction.WEST -> coords.copy(first = coords.first - 1)
        }
    }

    /**
     * Change the facing on this position
     */
    fun turn(turn: Char) {
        if (turn == 'R') {
            facing = when (facing) {
                Direction.NORTH -> Direction.EAST
                Direction.EAST -> Direction.SOUTH
                Direction.SOUTH -> Direction.WEST
                Direction.WEST -> Direction.NORTH
            }
        }
        // L
        else {
            facing = when (facing) {
                Direction.NORTH -> Direction.WEST
                Direction.EAST -> Direction.NORTH
                Direction.SOUTH -> Direction.EAST
                Direction.WEST -> Direction.SOUTH
            }
        }
    }

    /**
     * Get the Manhattan distance of this position from the origin.
     */
    fun getBlocks(): Int {
        return coords.first.absoluteValue + coords.second.absoluteValue
    }
}