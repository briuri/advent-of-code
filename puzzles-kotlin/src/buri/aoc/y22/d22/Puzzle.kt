package buri.aoc.y22.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
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
        assertRun(6032, 1)
        assertRun(56372, 0, true)
    }

    @Test
    fun runPart2() {
        // 186101 too low
        // 10, 196, North
        assertRun(197047, 0, true)
    }

    /**
     * Hardcoded mapping of wrapping spots all around the cube.
     * 			 000-049  050-099  100-149
     *
     * 000-049           1111111  2222222
     * 050-099           3333333
     * 100-149  4444444  5555555
     * 150-199  6666666
     */
    private val wraps = mutableMapOf<MutablePosition, MutablePosition>()

    init {
        for (i in 0 until 50) {
            // 1U - 6L (R)
            wraps[MutablePosition(50 + i, 0, NORTH)] = MutablePosition(0, 150 + i, EAST)
            // 6L - 1U (D)
            wraps[MutablePosition(0, 150 + i, WEST)] = MutablePosition(50 + i, 0, SOUTH)

            // 2U - 6D (U)
            wraps[MutablePosition(100 + i, 0, NORTH)] = MutablePosition(i, 199, NORTH)
            // 6D - 2U (D)
            wraps[MutablePosition(i, 199, SOUTH)] = MutablePosition(100 + i, 0, SOUTH)

            // 4U - 3L (R)
            wraps[MutablePosition(i, 100, NORTH)] = MutablePosition(50, 50 + i, EAST)
            // 3L - 4U (D)
            wraps[MutablePosition(50, 50 + i, WEST)] = MutablePosition(i, 100, SOUTH)

            // 3R - 2D (U)
            wraps[MutablePosition(99, 50 + i, EAST)] = MutablePosition(100 + i, 49, NORTH)
            // 2D - 3R (L)
            wraps[MutablePosition(100 + i, 49, SOUTH)] = MutablePosition(99, 50 + i, WEST)

            // 6R - 5D (U)
            wraps[MutablePosition(49, 150 + i, EAST)] = MutablePosition(50 + i, 149, NORTH)
            // 5D - 6R (L)
            wraps[MutablePosition(50 + i, 149, SOUTH)] = MutablePosition(49, 150 + i, WEST)

            // 2R - 5R (L)
            wraps[MutablePosition(149, i, EAST)] = MutablePosition(99, 149 - i, WEST)
            // 5R - 2R (L)
            wraps[MutablePosition(99, 100 + i, EAST)] = MutablePosition(149, 49 - i, WEST)

            // 1L - 4L (R)
            wraps[MutablePosition(50, i, WEST)] = MutablePosition(0, 149 - i, EAST)
            // 4L - 1L (R)
            wraps[MutablePosition(0, 100 + i, WEST)] = MutablePosition(50, 49 - i, EAST)
        }
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val width = input.dropLast(2).maxOf { it.length }
        val rawMaze = input.dropLast(2).map { it.padEnd(width, ' ') }
        val grid = Grid.fromCharInput(rawMaze)
        var position = MutablePosition(grid.filter { it == '.' }.first(), EAST)
        val instructions = input.last()

        var index = 0
        for (distance in instructions.extractInts()) {
            // Move
            for (i in 0 until distance) {
                val next = getNextPosition(part, grid, position)
                if (grid[next.coords] == '.') {
                    position = next.copy()

                } else {
                    break
                }
            }
            index += distance.toString().length

            // Turn
            when (if (index in instructions.indices) instructions[index] else ' ') {
                'L' -> position.turnLeft()
                'R' -> position.turnRight()
            }
            index++
        }

        val facingScore = when (position.facing) {
            EAST -> 0
            SOUTH -> 1
            WEST -> 2
            else -> 3
        }
        return 1000 * (position.coords.y + 1) + 4 * (position.coords.x + 1) + facingScore
    }

    /**
     * Finds the next position using wrap rules.
     */
    private fun getNextPosition(part: Part, grid: Grid<Char>, position: MutablePosition): MutablePosition {
        val next = position.copy()
        with(next) {
            move()
            if (!grid.isInBounds(coords) || grid[coords] == ' ') {
                if (part.isTwo()) {
                    return wraps[position]!!
                }
                // Part 1
                coords = when (position.facing) {
                    SOUTH -> coords.copy(y = 0)
                    NORTH -> coords.copy(y = grid.yRange.last)
                    EAST -> coords.copy(x = 0)
                    WEST -> coords.copy(x = grid.xRange.last)
                }
                while (grid[coords] == ' ') {
                    move()
                }
            }
            return next
        }
    }
}