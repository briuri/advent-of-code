package buri.aoc.y22.d15

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import buri.aoc.common.position.Point2D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(26, 1)
        assertRun(4748135, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(56000011, 1)
        assertRun(13743542639657, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val sensors = mutableListOf<Sensor>()
        sensors.addAll(input.map { Sensor(it) })
        val maxMD = sensors.maxOfOrNull { it.md }!!

        val beacons = mutableSetOf<Point2D<Long>>()
        beacons.addAll(sensors.map { it.beacon })

        if (part.isOne()) {
            var count = 0
            val y = if (input.size < 20) 10L else 2_000_000L
            val positions = sensors.map { it.position }.toSet()
            val minX = positions.minOf { it.x }
            val maxX = positions.maxOf { it.x }
            for (x in minX - maxMD..maxX + maxMD) {
                val point = Point2D(x, y)
                if (point !in beacons && sensors.any { it.inRange(point) }) {
                    count++
                }
            }
            return count
        }

        for (sensor in sensors.sortedBy { it.md }) {
            val test = testRing(sensors, beacons, sensor)
            if (test != null) {
                return test.x * 4_000_000L + test.y
            }
        }
        return -1
    }

    /**
     * Checks the ring just outside the MD for a sensor.
     */
    private fun testRing(sensors: List<Sensor>, beacons: Set<Point2D<Long>>, sensor: Sensor): Point2D<Long>? {
        val ringDistance = sensor.md + 1
        var x: Long
        var y: Long
        val tests = mutableSetOf<Point2D<Long>>()
        for (i in 0 until ringDistance) {
            // UL
            x = sensor.position.x - ringDistance + i
            y = sensor.position.y - i
            if (isInBounds(x, y)) {
                tests.add(Point2D(x, y))
            }
            // UR
            x = sensor.position.x + ringDistance - i
            y = sensor.position.y - i
            if (isInBounds(x, y)) {
                tests.add(Point2D(x, y))
            }
            // LR
            x = sensor.position.x + ringDistance - i
            y = sensor.position.y + i
            if (isInBounds(x, y)) {
                tests.add(Point2D(x, y))
            }
            // LL
            x = sensor.position.x - ringDistance - i
            y = sensor.position.y + i
            if (isInBounds(x, y)) {
                tests.add(Point2D(x, y))
            }
        }

        for (test in tests.filter { it !in beacons }) {
            if (sensors.all { it.position.getManhattanDistance(test) > it.md }) {
                return test
            }
        }
        return null
    }

    /**
     * Bounds the points in Part 2.
     */
    private fun isInBounds(x: Long, y: Long): Boolean = (x in 0L..4_000_000L && y in 0L..4_000_000L)
}

class Sensor(data: String) {
    val position: Point2D<Long>
    val beacon: Point2D<Long>
    val md: Long

    init {
        val numbers = data.extractLongs()
        position = Point2D(numbers[0], numbers[1])
        beacon = Point2D(numbers[2], numbers[3])
        md = position.getManhattanDistance(beacon)
    }

    /**
     * Returns true if the point is in range of this sensor.
     */
    fun inRange(point: Point2D<Long>): Boolean = position.getManhattanDistance(point) <= md
}