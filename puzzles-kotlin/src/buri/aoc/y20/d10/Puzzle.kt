package buri.aoc.y20.d10

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
        assertRun(220, 1)
        assertRun(2277, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(19208, 1)
        assertRun(37024595836928, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val adapters = input.map { it.toInt() }.sorted().toMutableList()
        adapters.add(adapters.last() + 3)

        if (part.isOne()) {
            val pluggedIn = mutableListOf<Int>()
            pluggedIn.add(0)

            var oneDiff = 0
            var threeDiff = 0
            while (adapters.isNotEmpty()) {
                val adapter = adapters.removeFirst()
                val diff = adapter - pluggedIn.last()
                if (diff in 1..3) {
                    if (diff == 1) {
                        oneDiff++
                    }
                    if (diff == 3) {
                        threeDiff++
                    }
                    pluggedIn.add(adapter)
                }
            }
            return oneDiff * threeDiff
        }

        // Add the plug for pathing.
        adapters.add(0, 0)

        val paths = mutableMapOf<Int, Long>()
        paths[0] = 1
        for (adapter in adapters) {
            for (step in 1..3) {
                val next = adapter + step
                if (next in adapters) {
                    paths.putIfAbsent(adapter, 0L)
                    paths.putIfAbsent(next, 0L)
                    paths[next] = paths[next]!! + paths[adapter]!!
                }
            }
        }
        return paths[adapters.last()]!!
    }
}