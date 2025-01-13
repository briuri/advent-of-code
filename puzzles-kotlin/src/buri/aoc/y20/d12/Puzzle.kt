package buri.aoc.y20.d12

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
        assertRun(25, 1)
        assertRun(1007, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(286, 1)
        assertRun(41212, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val start = Point2D(0, 0)
        val ship = MutablePosition(start, Direction.EAST)
        val waypoint = MutablePosition(Point2D(10, -1), Direction.EAST)
        for (line in input) {
            var command = line.take(1)[0]
            var amount = line.drop(1).toInt()
            // Convert left turns into right turns.
            if (command == 'L') {
                command = 'R'
                amount = when (amount) {
                    90 -> 270
                    270 -> 90
                    else -> amount
                }
            }
            if (part.isOne()) {
                move(ship, command, amount)
            } else {
                move(ship, waypoint, command, amount)
            }
        }
        return ship.coords.getManhattanDistance(start)
    }

    /**
     * Moves the ship in Part One.
     */
    private fun move(ship: MutablePosition, command: Char, amount: Int) {
        with(ship) {
            when (command) {
                'N' -> {
                    coords = coords.copy(y = coords.y - amount)
                }

                'S' -> {
                    coords = coords.copy(y = coords.y + amount)
                }

                'W' -> {
                    coords = coords.copy(x = coords.x - amount)
                }

                'E' -> {
                    coords = coords.copy(x = coords.x + amount)
                }

                'R' -> {
                    when (amount) {
                        90 -> turnRight()
                        180 -> turnAround()
                        270 -> turnLeft()
                    }
                }

                'F' -> {
                    repeat(amount) {
                        move()
                    }
                }
            }
        }
    }

    /**
     * Moves the ship and waypoint in Part Two.
     */
    private fun move(ship: MutablePosition, waypoint: MutablePosition, command: Char, amount: Int) {
        with(waypoint) {
            when (command) {
                'N' -> {
                    coords = coords.copy(y = coords.y - amount)
                }

                'S' -> {
                    coords = coords.copy(y = coords.y + amount)
                }

                'W' -> {
                    coords = coords.copy(x = coords.x - amount)
                }

                'E' -> {
                    coords = coords.copy(x = coords.x + amount)
                }

                'R' -> {
                    when (amount) {
                        // 3,1 -> -1, 3
                        90 -> coords = Point2D(coords.y * -1, coords.x)
                        // 3,1 -> -3, -1
                        180 -> coords = Point2D(coords.x * -1, coords.y * -1)
                        // 3,1 -> 1,-3
                        270 -> coords = Point2D(coords.y, coords.x * -1)
                    }
                }

                'F' -> {
                    repeat(amount) {
                        ship.coords = Point2D(ship.coords.x + coords.x, ship.coords.y + coords.y)
                    }
                }
            }
        }
    }
}