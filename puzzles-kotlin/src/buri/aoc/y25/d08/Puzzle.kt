package buri.aoc.y25.d08

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import buri.aoc.common.position.Point3D
import org.junit.Test
import kotlin.math.sqrt

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(40, 1)
        assertRun(98696, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(25272, 1)
        assertRun(2245203960, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val connectionsNeeded = if (input.size < 21) {
            10
        } else {
            1000
        }
        val boxes = mutableListOf<Point3D<Long>>()
        for (line in input) {
            val numbers = line.extractLongs()
            boxes.add(Point3D(numbers[0], numbers[1], numbers[2]))
        }

        val groups = mutableListOf<MutableSet<Point3D<Long>>>()
        for (box in boxes) {
            val group = mutableSetOf<Point3D<Long>>()
            group.add(box)
            groups.add(group)
        }

        var distances = mutableMapOf<Double, Pair<Point3D<Long>, Point3D<Long>>>()
        for (pointA in boxes) {
            for (pointB in boxes.filter { it != pointA }) {
                val currentDistance = pointA.getDistance(pointB)
                distances[currentDistance] = Pair(pointA, pointB)
            }
        }
        distances = distances.toSortedMap()

        if (part.isOne()) {
            var count = 0
            for (pair in distances.values) {
                groups.merge(pair)
                count++
                if (count == connectionsNeeded) {
                    break
                }
            }
            val sizeOrder = groups.sortedByDescending { it.size }
            return sizeOrder[0].size * sizeOrder[1].size * sizeOrder[2].size
        }

        // Part Two
        for (pair in distances.values) {
            groups.merge(pair)
            if (groups.size == 1) {
                return (pair.first.x * pair.second.x)
            }
        }
        throw Exception("Never reached a single circuit.")
    }

    fun MutableList<MutableSet<Point3D<Long>>>.merge(pair: Pair<Point3D<Long>, Point3D<Long>>) {
        val groupA = this.first { pair.first in it }
        val groupB = this.first { pair.second in it }
        if (groupA != groupB) {
            groupA.addAll(groupB)
            this.remove(groupB)
        }
    }

    fun Point3D<Long>.getDistance(that: Point3D<Long>): Double {
        return sqrt(((x - that.x) * (x - that.x) + (y - that.y) * (y - that.y) + (z - that.z) * (z - that.z)).toDouble())
    }
}