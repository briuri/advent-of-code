package buri.aoc.y18.d06

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.extractInts
import buri.aoc.common.getManhattanDistance
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
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        for (line in input) {
            val numbers = line.extractInts()
            minX = minX.coerceAtMost(numbers[0])
            maxX = maxX.coerceAtLeast(numbers[0])
            minY = minY.coerceAtMost(numbers[1])
            maxY = maxY.coerceAtLeast(numbers[1])
            points.add(Pair(numbers[0], numbers[1]))
        }

        val regionSizes = mutableMapOf<Pair<Int, Int>, Int>()
        val distanceSums = mutableMapOf<Pair<Int, Int>, Int>()
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (part == ONE) {
                    val closest = getClosest(points, Pair(x, y))
                    if (closest != null) {
                        regionSizes.putIfAbsent(closest, 0)
                        regionSizes[closest] = regionSizes[closest]!! + 1
                    }
                } else {
                    val point = Pair(x, y)
                    val distances = getDistanceSum(points, point)
                    distanceSums[point] = distances
                }
            }
        }
        if (part == ONE) {
            return regionSizes.toList().maxByOrNull { it.second }!!.second
        }
        return distanceSums.filter { it.value < 10000 }.size

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
        val closest = mds.toList().minByOrNull { it.first }!!.second
        return if (closest.size == 1) closest.first() else null
    }

    /**
     * Returns the sum of all distances to a target.
     */
    private fun getDistanceSum(points: Set<Pair<Int, Int>>, target: Pair<Int, Int>): Int {
        return points.sumOf { it.getManhattanDistance(target) }
    }
}