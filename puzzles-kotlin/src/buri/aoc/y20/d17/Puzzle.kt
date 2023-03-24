package buri.aoc.y20.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Bounds4D
import buri.aoc.common.position.Point4D
import buri.aoc.common.position.getNeighbors
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(112, 1)
        assertRun(280, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(848, 1)
        assertRun(1696, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var activeCubes = mutableSetOf<Point4D<Int>>()
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                if (value == '#') {
                    activeCubes.add(Point4D(x, y, 0, 0))
                }
            }
        }

        repeat(6) {
            val nextActive = mutableSetOf<Point4D<Int>>()
            val bounds = Bounds4D(activeCubes)
            val xBounds = bounds.x.first - 1..bounds.x.last + 1
            val yBounds = bounds.y.first - 1..bounds.y.last + 1
            val zBounds = bounds.z.first - 1..bounds.z.last + 1
            val tBounds = if (part.isOne()) (0..0) else (bounds.t.first - 1..bounds.t.last + 1)
            for (t in tBounds) {
                for (z in zBounds) {
                    for (y in yBounds) {
                        for (x in xBounds) {
                            val point = Point4D(x, y, z, t)
                            var neighbors = point.getNeighbors()
                            if (part.isOne()) {
                                neighbors = neighbors.filter { it.t == 0 }.toMutableList()
                            }
                            val count = neighbors.count { it in activeCubes }

                            if (point in activeCubes && (count == 2 || count == 3)) {
                                nextActive.add(point)
                            } else if (point !in activeCubes && count == 3) {
                                nextActive.add(point)
                            }
                        }
                    }
                }
            }
            activeCubes = nextActive
        }
        return activeCubes.size
    }
}