package buri.aoc.y18.d25

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Point4D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(2, 1)
        assertRun(407, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val unused = mutableListOf<Point4D<Int>>()
        for (line in input) {
            val numbers = line.extractInts(true)
            unused.add(Point4D(numbers[0], numbers[1], numbers[2], numbers[3]))
        }

        var count = 0
        while (unused.isNotEmpty()) {
            // Start a constellation with the next quad.
            val constellation = mutableSetOf<Point4D<Int>>()
            constellation.add(unused.removeFirst())
            do {
                val oldSize = constellation.size
                // Add any other quads with the right distance.
                for (quad in unused) {
                    if (constellation.any { it.getManhattanDistance(quad) <= 3 }) {
                        constellation.add(quad)
                    }
                }
                val newSize = constellation.size
                // Stop when we've cycled through all unused quads with no change to constellation.
            } while (oldSize != newSize)
            // Save the finished constellation.
            unused.removeAll { it in constellation }
            count++
        }
        return count
    }
}