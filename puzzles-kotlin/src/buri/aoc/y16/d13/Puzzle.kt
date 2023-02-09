package buri.aoc.y16.d13

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun(11, 1)
        assertRun(92, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(124, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val magicNumber = input[0].toInt()
        val start = Pair(1, 1)
        val end = Pair(input[1].split(",")[0].toInt(), input[1].split(",")[1].toInt())

        val frontier = mutableListOf<Pair<Int, Int>>()
        val stepsTo = mutableMapOf<Pair<Int, Int>, Int>()
        frontier.add(start)
        stepsTo[start] = 0
        var current: Pair<Int, Int>?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            if (current == end) {
                break
            }
            for (next in getNeighbors(magicNumber, current).filter { !stepsTo.containsKey(it) }) {
                frontier.add(next)
                stepsTo[next] = stepsTo[current]!! + 1
            }
        }
        if (part == ONE) {
            return stepsTo[end]!!
        }
        return stepsTo.filter { it.value <= 50 }.size
    }

    /**
     * Finds legal places to move from this spot.
     */
    private fun getNeighbors(magicNumber: Int, current: Pair<Int, Int>): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()
        list.add(current.copy(first = current.first - 1))
        list.add(current.copy(first = current.first + 1))
        list.add(current.copy(second = current.second - 1))
        list.add(current.copy(second = current.second + 1))
        return list.filter { isTraversable(magicNumber, it) }
    }

    /**
     * Checks if a point is traversable.
     */
    private fun isTraversable(magicNumber: Int, point: Pair<Int, Int>): Boolean {
        val x = point.first
        val y = point.second
        if (x < 0 || y < 0) {
            return false
        }

        val number = (x * x + 3 * x + 2 * x * y + y + y * y) + magicNumber
        val binary = Integer.toBinaryString(number)
        val numOnes = binary.toCharArray().sumOf { it.digitToInt() }
        return (numOnes % 2 == 0)
    }
}