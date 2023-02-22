package buri.aoc.y18.d13

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction
import buri.aoc.common.position.Direction.*
import buri.aoc.common.position.Grid
import buri.aoc.common.position.MutablePosition
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
        val grid = Grid(input.maxOf { it.length }, input.size)
        val carts = mutableListOf<Cart>()
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.padEnd(grid.width, ' ').withIndex()) {
                grid[x, y] = value.toString()
                if (value in Direction.values().map { it.icon }) {
                    carts.add(Cart(Pair(x, y), Direction.from(value)))
                }
            }
        }

        while (true) {
            for (cart in carts.sortedWith(compareBy({ it.coords.second }, { it.coords.first }))) {
                cart.move()
                // If this cart has moved on top of another cart
                if (carts.any { it != cart && it.coords == cart.coords }) {
                    if (part.isOne()) {
                        return (cart.getPositionString())
                    }
                    carts.removeIf { it.coords == cart.coords }
                }
                // Turn cart if needed.
                when (grid[cart.coords]) {
                    "+" -> {
                        when (cart.getNextTurn()) {
                            0 -> cart.turnLeft()
                            2 -> cart.turnRight()
                            // else go straight
                        }
                    }
                    "/" -> {
                        when (cart.facing) {
                            NORTH, SOUTH -> cart.turnRight()
                            WEST, EAST -> cart.turnLeft()
                        }
                    }
                    "\\" -> {
                        when (cart.facing) {
                            NORTH, SOUTH -> cart.turnLeft()
                            WEST, EAST -> cart.turnRight()
                        }
                    }
                }
            }
            if (part.isTwo() && carts.size == 1) {
                return carts.first().getPositionString()
            }
        }
    }
}

class Cart(start: Pair<Int, Int>, facing: Direction) {
    val position = MutablePosition(start, facing)
    val coords: Pair<Int, Int>
        get() = position.coords
    val facing: Direction
        get() = position.facing
    private var turnHistory = 0

    /**
     * Returns the position in the format needed to solve the puzzle.
     */
    fun getPositionString(): String {
        return "${position.coords.first},${position.coords.second}"
    }

    /**
     * Passthrough to MutablePosition
     */
    fun move() {
        position.move()
    }

    /**
     * Passthrough to MutablePosition
     */
    fun turnLeft() {
        position.turnLeft()
    }

    /**
     * Passthrough to MutablePosition
     */
    fun turnRight() {
        position.turnRight()
    }

    /**
     * Returns 0, 1, or 2 to control which direction is turned next.
     */
    fun getNextTurn(): Int {
        val next = turnHistory
        turnHistory++
        if (turnHistory == 3) {
            turnHistory = 0
        }
        return next
    }
}