package buri.aoc.y22.d09

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction.*
import buri.aoc.common.position.MutablePosition
import buri.aoc.common.position.Point2D
import buri.aoc.common.position.getNeighbors
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(13, 1)
        assertRun(6367, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(36, 2)
        assertRun(2536, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val tailVisited = mutableSetOf<Point2D<Int>>()
        val rope = mutableListOf<MutablePosition>()
        val size = if (part.isOne()) 2 else 10
        repeat(size) {
            rope.add(MutablePosition(Point2D(0, 0)))
        }
        tailVisited.add(rope.last().coords)

        for (line in input) {
            val tokens = line.split(" ")
            rope[0].facing = when (tokens[0]) {
                "U" -> NORTH
                "D" -> SOUTH
                "L" -> WEST
                else -> EAST
            }
            repeat(tokens[1].toInt()) {
                rope[0].move()
                for (i in 1..rope.lastIndex) {
                    follow(rope[i - 1], rope[i])
                }
                tailVisited.add(rope.last().coords)
            }
        }
        return tailVisited.size
    }

    /**
     * Updates the position of the tail based on the head.
     */
    private fun follow(head: MutablePosition, tail: MutablePosition) {
        if (tail.coords in head.coords.getNeighbors(true)) {
            return
        }

        if (head.coords.y < tail.coords.y) {
            tail.move(NORTH)
            if (head.coords.x < tail.coords.x) {
                tail.move(WEST)
            } else if (head.coords.x > tail.coords.x) {
                tail.move(EAST)
            }
        } else if (head.coords.y > tail.coords.y) {
            tail.move(SOUTH)
            if (head.coords.x < tail.coords.x) {
                tail.move(WEST)
            } else if (head.coords.x > tail.coords.x) {
                tail.move(EAST)
            }
        } else if (head.coords.x < tail.coords.x) {
            tail.move(WEST)
            if (head.coords.y < tail.coords.y) {
                tail.move(NORTH)
            } else if (head.coords.y > tail.coords.y) {
                tail.move(SOUTH)
            }
        } else if (head.coords.x > tail.coords.x) {
            tail.move(EAST)
            if (head.coords.y < tail.coords.y) {
                tail.move(NORTH)
            } else if (head.coords.y > tail.coords.y) {
                tail.move(SOUTH)
            }
        }
    }
}