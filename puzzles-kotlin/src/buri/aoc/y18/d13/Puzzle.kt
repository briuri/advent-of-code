package buri.aoc.y18.d13

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction
import buri.aoc.common.position.Direction.*
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
        assertRun("7,3", 1)
        assertRun("64,57", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("6,4", 2)
        assertRun("136,8", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val grid = Grid(input.maxOf { it.length }, input.size, ' ')
        val carts = mutableListOf<Cart>()
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.padEnd(grid.width, ' ').withIndex()) {
                grid[x, y] = value
                if (value in Direction.values().map { it.icon }) {
                    carts.add(Cart(Point2D(x, y), Direction.from(value)))
                }
            }
        }

        while (true) {
            for (cart in carts.sortedBy { it.coords }) {
                cart.move()
                // If this cart has moved on top of another cart
                if (carts.any { it != cart && it.coords == cart.coords }) {
                    if (part.isOne()) {
                        return (cart.coords.toString())
                    }
                    carts.removeIf { it.coords == cart.coords }
                }
                // Turn cart if needed.
                when (grid[cart.coords]) {
                    '+' -> {
                        when (cart.nextTurn) {
                            0 -> cart.turnLeft()
                            2 -> cart.turnRight()
                            // else go straight
                        }
                    }
                    '/' -> {
                        when (cart.facing) {
                            NORTH, SOUTH -> cart.turnRight()
                            WEST, EAST -> cart.turnLeft()
                        }
                    }
                    '\\' -> {
                        when (cart.facing) {
                            NORTH, SOUTH -> cart.turnLeft()
                            WEST, EAST -> cart.turnRight()
                        }
                    }
                }
            }
            if (part.isTwo() && carts.size == 1) {
                return carts.first().coords.toString()
            }
        }
    }
}

class Cart(start: Point2D<Int>, facing: Direction) {
    private var turnHistory = 0
    val nextTurn: Int
        get() {
            val next = turnHistory
            turnHistory++
            if (turnHistory == 3) {
                turnHistory = 0
            }
            return next
        }
    val position = MutablePosition(start, facing)

    // Pass-throughs
    val coords: Point2D<Int>
        get() = position.coords
    val facing: Direction
        get() = position.facing

    fun move() = position.move()
    fun turnLeft() = position.turnLeft()
    fun turnRight() = position.turnRight()
}