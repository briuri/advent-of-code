package buri.aoc.y18.d06

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.extractInts
import buri.aoc.common.getManhattanDistance
import buri.aoc.common.position.Bounds
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(17, 1)
        assertRun(3647, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(41605, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val points = mutableSetOf<Pair<Int, Int>>()
        for (line in input) {
            val numbers = line.extractInts()
            points.add(Pair(numbers[0], numbers[1]))
        }
        val bounds = Bounds(points)

        val regionSizes = mutableMapOf<Pair<Int, Int>, Int>()
        val distanceSums = mutableMapOf<Pair<Int, Int>, Int>()
        for (y in bounds.y) {
            for (x in bounds.x) {
                if (part == ONE) {
                    val closest = getClosest(points, Pair(x, y))
                    if (closest != null) {
                        // Update the size of the region "owned" by the closest point.
                        regionSizes.putIfAbsent(closest, 0)
                        regionSizes[closest] = regionSizes[closest]!! + 1
                    }
                } else {
                    val target = Pair(x, y)
                    // Store the sum of all MDs to the target point.
                    distanceSums[target] = points.sumOf { it.getManhattanDistance(target) }
                }
            }
        }
        return if (part == ONE) {
            regionSizes.maxByOrNull { it.value }!!.value
        } else {
            distanceSums.filter { it.value < 10000 }.size
        }
    }

    /**
     * Returns the closest point to a target, or null if there are multiple.
     */
    private fun getClosest(points: Set<Pair<Int, Int>>, target: Pair<Int, Int>): Pair<Int, Int>? {
        val mds = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
        for (point in points) {
            val md = point.getManhattanDistance(target)
            mds.putIfAbsent(md, mutableListOf())
            mds[md]!!.add(point)
        }
        val closest = mds.minByOrNull { it.key }!!.value
        return if (closest.size == 1) closest.first() else null
    }
}