package buri.aoc.y19.d10

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Point2D
import org.junit.Test
import kotlin.math.PI
import kotlin.math.atan2

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(210, 1)
        assertRun(267, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(802, 1)
        assertRun(1309, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val asteroids = mutableSetOf<Point2D<Int>>()
        for ((y, line) in input.withIndex()) {
            for ((x, _) in line.withIndex().filter { it.value == '#' }) {
                asteroids.add(Point2D(x, y))
            }
        }

        val slopeMaps = mutableListOf<SlopeMap>()
        for (site in asteroids) {
            slopeMaps.add(SlopeMap(asteroids.filter { it != site }, site))
        }

        val bestSite = slopeMaps.maxBy { it.countVisibleAsteroids() }
        if (part.isOne()) {
            return bestSite.countVisibleAsteroids()
        }
        val last = bestSite.vaporize(200)
        return (last.x * 100 + last.y)
    }
}

class SlopeMap(asteroids: List<Point2D<Int>>, private val site: Point2D<Int>) {
    private val map = mutableMapOf<Double, MutableList<Point2D<Int>>>()

    init {
        for (asteroid in asteroids) {
            val slope = toAngle(asteroid.y - site.y, asteroid.x - site.x)
            map.putIfAbsent(slope, mutableListOf())
            map[slope]!!.add(asteroid)
        }
    }

    /**
     * Counts how many asteroids can be seen from this spot.
     */
    fun countVisibleAsteroids(): Int {
        // Include all the slopes that have exactly 1 asteroid.
        var count = map.count { it.value.size == 1 }
        // If there are more, insert the site itself, sort, and take the two on either side.
        for (asteroids in map.filter { it.value.size > 1 }.map { it.value }) {
            val points = (asteroids.toMutableList() + site).sorted()
            count += if (points.indexOf(site) == 0 || points.indexOf(site) == points.lastIndex) 1 else 2
        }
        return count
    }

    /**
     * Finds the Xth asteroid to be vaporized in clockwise order.
     */
    fun vaporize(count: Int): Point2D<Int> {
        val sortedValues = mutableListOf<MutableList<Point2D<Int>>>()
        for (key in map.keys.sortedDescending()) {
            sortedValues.add(map[key]!!)
        }
        return (sortedValues[count - 1].first())
    }

    /**
     * Converts a slope to an angle for sorting (clockwise from 12 o clock)
     */
    private fun toAngle(dy: Int, dx: Int): Double {
        return atan2(dx.toDouble(), dy.toDouble()) - PI / 2.0
    }

    override fun toString(): String = map.toString()
}