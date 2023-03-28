package buri.aoc.y21.d04

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Grid
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(4512, 1)
        assertRun(11536, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1924, 1)
        assertRun(1284, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = input[0].extractInts()

        val boards = mutableListOf<Board>()
        var i = 2
        while (i in input.indices) {
            boards.add(Board(input.subList(i, i + 5)))
            i += 6
        }
        for (number in numbers) {
            for (board in boards) {
                board.mark(number)
                if (board.wins()) {
                    if (part.isOne() || (part.isTwo() && boards.size == 1)) {
                        return board.unmarkedSum() * number
                    }
                }
            }
            if (part.isTwo()) {
                boards.removeIf { it.wins() }
            }
        }
        return -1
    }
}

class Board(data: List<String>) {
    val grid = Grid.fromInput(data, 0)
    private val size = data[0].extractInts().size

    /**
     * Marks a spot.
     */
    fun mark(spot: Int) {
        val spots = grid.filter { it == spot }
        if (spots.isNotEmpty()) {
            grid[spots.first()] = -1
        }
    }

    /**
     * Checks if this board is won.
     */
    fun wins(): Boolean {
        for (y in 0 until size) {
            var rowSum = 0
            var colSum = 0
            for (x in 0 until size) {
                rowSum += grid[x, y]
                colSum += grid[y, x]
            }
            if (rowSum == -1 * size || colSum == -1 * size) {
                return true
            }
        }
        return false
    }

    /**
     * Gets the unmarked spot sum of this (assumed to be) winning board
     */
    fun unmarkedSum(): Int = grid.filter { it != -1 }.sumOf { grid[it] }

    override fun toString(): String = grid.toString()
}