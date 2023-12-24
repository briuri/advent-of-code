package buri.aoc.y21.d19

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Point3D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(79, 1)
        assertRun(323, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3621, 1)
        assertRun(10685, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val scanners = mutableListOf<Scanner>()
        var i = 0
        while (i in input.indices) {
            if (input[i].contains("scanner")) {
                val id = input[i].extractInts(false)[0]
                i++
                val beacons = mutableListOf<Point3D<Int>>()
                while (i in input.indices && input[i].isNotEmpty()) {
                    val numbers = input[i].extractInts()
                    beacons.add(Point3D(numbers[0], numbers[1], numbers[2]))
                    i++
                }
                scanners.add(Scanner(id, beacons))
            }
            i++
        }

        scanners[0].fixedPoint = Point3D(0, 0, 0)
        val frontier = mutableListOf<Scanner>()
        frontier.add(scanners[0])
        while (frontier.isNotEmpty()) {
            val scanner = frontier.removeFirst()
            for (testScanner in scanners.filter { it.id != scanner.id && it.fixedPoint == null }) {
                if (scanner.hasOverlap(testScanner)) {
                    frontier.add(testScanner)
                }
            }
        }

        val uniqueBeacons = mutableSetOf<Point3D<Int>>()
        for (scanner in scanners) {
            uniqueBeacons.addAll(scanner.beacons)
        }

        if (part.isOne()) {
            return uniqueBeacons.size
        }

        var maxDistance = 0L
        for (scanner1 in scanners) {
            for (scanner2 in scanners.filter { it != scanner1 }) {
                val distance = scanner1.fixedPoint!!.getManhattanDistance(scanner2.fixedPoint!!)
                maxDistance = maxDistance.coerceAtLeast(distance)
            }
        }
        return maxDistance
    }
}

class Scanner(val id: Int, var beacons: List<Point3D<Int>>) {
    private val permutations = mutableListOf<List<Point3D<Int>>>()
    var fixedPoint: Point3D<Int>? = null

    init {
        // X-Axis
        permutations.add(beacons.map { Point3D(it.x * -1, it.y * -1, it.z) })
        permutations.add(beacons.map { Point3D(it.x * -1, it.z * -1, it.y * -1) })
        permutations.add(beacons.map { Point3D(it.x * -1, it.z, it.y) })
        permutations.add(beacons.map { Point3D(it.x * -1, it.y, it.z * -1) })
        permutations.add(beacons.map { Point3D(it.x, it.y * -1, it.z * -1) })
        permutations.add(beacons.map { Point3D(it.x, it.z * -1, it.y) })
        permutations.add(beacons.map { Point3D(it.x, it.z, it.y * -1) })
        permutations.add(beacons.map { Point3D(it.x, it.y, it.z) })

        // Y-Axis
        permutations.add(beacons.map { Point3D(it.y * -1, it.x * -1, it.z * -1) })
        permutations.add(beacons.map { Point3D(it.y * -1, it.z * -1, it.x) })
        permutations.add(beacons.map { Point3D(it.y * -1, it.z, it.x * -1) })
        permutations.add(beacons.map { Point3D(it.y * -1, it.x, it.z) })
        permutations.add(beacons.map { Point3D(it.y, it.x * -1, it.z) })
        permutations.add(beacons.map { Point3D(it.y, it.z * -1, it.x * -1) })
        permutations.add(beacons.map { Point3D(it.y, it.z, it.x) })
        permutations.add(beacons.map { Point3D(it.y, it.x, it.z * -1) })

        // Z-Axis
        permutations.add(beacons.map { Point3D(it.z * -1, it.x * -1, it.y) })
        permutations.add(beacons.map { Point3D(it.z * -1, it.y * -1, it.x * -1) })
        permutations.add(beacons.map { Point3D(it.z * -1, it.y, it.x) })
        permutations.add(beacons.map { Point3D(it.z * -1, it.x, it.y * -1) })
        permutations.add(beacons.map { Point3D(it.z, it.x * -1, it.y * -1) })
        permutations.add(beacons.map { Point3D(it.z, it.y * -1, it.x) })
        permutations.add(beacons.map { Point3D(it.z, it.y, it.x * -1) })
        permutations.add(beacons.map { Point3D(it.z, it.x, it.y) })
    }

    /**
     * Tries different permutations to align a scanner with this one. Sets the fixed location then returns true if
     * there is an overlap.
     */
    fun hasOverlap(testScanner: Scanner): Boolean {
        for (beacon in beacons) {
            for (testBeacons in testScanner.permutations) {
                for (testBeacon in testBeacons) {
                    val diffX: Int = testBeacon.x - beacon.x
                    val diffY: Int = testBeacon.y - beacon.y
                    val diffZ: Int = testBeacon.z - beacon.z
                    val offsetTestBeacons = testBeacons.map { Point3D(it.x - diffX, it.y - diffY, it.z - diffZ) }
                    val overlaps = offsetTestBeacons.count { it in beacons }
                    if (overlaps >= 12) {
                        testScanner.fixedPoint = Point3D(diffX, diffY, diffZ)
                        testScanner.beacons = offsetTestBeacons
                        return true
                    }
                }
            }
        }
        return false
    }
}