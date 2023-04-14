package buri.aoc.y22.d15

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Bounds2D
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

        val beacons = mutableSetOf<Point2D<Int>>()
        beacons.addAll(sensors.map { it.beacon })

        if (part.isOne()) {
            var count = 0
            val y = if (input.size < 20) 10 else 2_000_000
            val bounds = Bounds2D(sensors.map { it.position }.toSet())
            for (x in bounds.x.first - maxMD..bounds.x.last + maxMD) {
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
    private fun testRing(sensors: List<Sensor>, beacons: Set<Point2D<Int>>, sensor: Sensor): Point2D<Int>? {
        val ringDistance = sensor.md + 1
        var x: Int
        var y: Int
        val tests = mutableSetOf<Point2D<Int>>()
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
    private fun isInBounds(x: Int, y: Int): Boolean = (x in 0..4_000_000 && y in 0..4_000_000)
}

class Sensor(data: String) {
    val position: Point2D<Int>
    val beacon: Point2D<Int>
    val md: Int

    init {
        val numbers = data.extractInts()
        position = Point2D(numbers[0], numbers[1])
        beacon = Point2D(numbers[2], numbers[3])
        md = position.getManhattanDistance(beacon)
    }

    /**
     * Returns true if the point is in range of this sensor.
     */
    fun inRange(point: Point2D<Int>): Boolean = position.getManhattanDistance(point) <= md
}