package buri.aoc.y15.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(4, 1)
        assertRun(654, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3, 1)
        assertRun(57, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val rawContainers = mutableListOf<Int>()
        rawContainers.addAll(input.map { it.toInt() })
        val containers = rawContainers.sorted().reversed()

        val end = if (input.size < 10) 25 else 150
        val frequency = mutableMapOf<Int, Int>()
        val combos = getCount(0, end, containers, 0, frequency)
        return if (part.isOne()) combos else frequency[frequency.keys.min()]!!
    }

    /**
     * Counts how many ways the provided numbers can be grouped to reach a sum.
     */
    private fun getCount(
        start: Int,
        end: Int,
        containers: List<Int>,
        depth: Int,
        frequency: MutableMap<Int, Int>,
    ): Int {
        var count = 0
        if (start == end) {
            frequency.putIfAbsent(depth, 0)
            frequency[depth] = frequency[depth]!! + 1
            count = 1
        } else if (start < end) {
            for (i in containers.indices) {
                count += getCount(
                    start + containers[i], end,
                    containers.subList(i + 1, containers.size), depth + 1, frequency,
                )
            }
        }
        return count
    }
}