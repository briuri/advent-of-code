package buri.aoc.y17.d03

import buri.aoc.common.*
import buri.aoc.common.Part.ONE
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(552, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(330785, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val num = input[0].toInt()
        if (part == ONE) {
            // Each ring's lower right value is a square of an odd number.
            var maxInRingRoot = 3
            while (true) {
                if (maxInRingRoot * maxInRingRoot > num) {
                    break
                }
                maxInRingRoot += 2
            }
            val maxInRing = maxInRingRoot * maxInRingRoot
            val minInRing = (maxInRingRoot - 2) * (maxInRingRoot - 2) + 1

            // On the each side of the ring, the MD goes down from the corner to a minMD then back up again.
            val maxMD = maxInRingRoot - 1
            val minMD = maxMD / 2
            var md = maxMD
            var offset = -1
            for (i in minInRing..maxInRing) {
                offset = when (md) {
                    maxMD -> -1
                    minMD -> 1
                    else -> offset
                }
                md += offset
                if (i == num) {
                    return md
                }
            }
        }
        // Part TWO
        return SpiralGrid().firstGreaterThan(num)
    }
}

class SpiralGrid {
    private val squares = mutableMapOf<Pair<Int, Int>, Int>()
    private var current = MutablePosition(Pair(0, 0), Direction.EAST)

    /**
     * Fills in the grid until we reach the target threshold.
     */
    fun firstGreaterThan(threshold: Int): Int {
        squares[current.coords] = 1
        while (squares[current.coords]!! < threshold) {
            current.move()
            squares[current.coords] = getNeighborSum()
            if (shouldTurnLeft()) {
                current.turn('L')
            }
        }
        return squares[current.coords]!!
    }

    /**
     * Sums any populated squares around this square.
     */
    private fun getNeighborSum(): Int {
        val list = current.coords.getNeighbors(true)
        var sum = 0
        for (point in list) {
            sum += squares[point] ?: 0
        }
        return sum
    }

    /**
     * Updates the facing on the current position to keep going in a spiral.
     */
    private fun shouldTurnLeft(): Boolean {
        val test = current.copy()
        test.turn('L')
        test.move()
        return (squares[test.coords] == null)
    }
}