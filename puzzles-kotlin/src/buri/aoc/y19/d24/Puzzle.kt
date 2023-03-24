package buri.aoc.y19.d24

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Point3D
import org.junit.Test
import kotlin.math.pow

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(32513278, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1912, 0, true)
    }

    private val size = 5

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var bugs = mutableSetOf<Point3D<Int>>()
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                if (value == '#') {
                    bugs.add(Point3D(x, y, 0))
                }
            }
        }

        if (part.isOne()) {
            val visited = mutableSetOf<String>()
            while (bugs.toString() !in visited) {
                visited.add(bugs.toString())
                bugs = getNextBugs(part, bugs)
            }
            return getRating(bugs)
        }

        // Part TWO
        repeat(200) {
            bugs = getNextBugs(part, bugs)
        }
        return bugs.size
    }

    /**
     * Iterates the bug biome one tick.
     */
    private fun getNextBugs(part: Part, bugs: Set<Point3D<Int>>): MutableSet<Point3D<Int>> {
        val minZ = bugs.minOf { it.z } - 1
        val maxZ = bugs.maxOf { it.z } + 1

        val nextBugs = mutableSetOf<Point3D<Int>>()
        for (z in minZ..maxZ) {
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (part.isTwo() && x == 2 && y == 2) {
                        continue
                    }
                    val point = Point3D(x, y, z)
                    if (point.becomesBug(part, bugs)) {
                        nextBugs.add(point)
                    }
                }
            }
        }
        return nextBugs
    }

    /**
     * Returns the rating for a bug biome.
     */
    private fun getRating(bugs: Set<Point3D<Int>>): Int {
        var power = 0
        var rating = 0.toDouble()
        for (y in 0 until size) {
            for (x in 0 until size) {
                if (Point3D(x, y, 0) in bugs) {
                    rating += 2.toDouble().pow(power)
                }
                power++
            }
        }
        return rating.toInt()
    }

    /**
     * Returns true if a point becomes a bug on the next iteration.
     */
    private fun Point3D<Int>.becomesBug(part: Part, bugs: Set<Point3D<Int>>): Boolean {
        var neighbors = mutableListOf<Point3D<Int>>()
        neighbors.add(Point3D(x, y - 1, z))
        neighbors.add(Point3D(x - 1, y, z))
        neighbors.add(Point3D(x + 1, y, z))
        neighbors.add(Point3D(x, y + 1, z))
        if (part.isTwo()) {
            val newNeighbors = mutableListOf<Point3D<Int>>()
            for (neighbor in neighbors) {
                val isInner = (neighbor.x == 2 && neighbor.y == 2)
                val innerZ = neighbor.z + 1
                val outerZ = neighbor.z - 1

                // Outer edges
                if (neighbor.y < 0) {
                    newNeighbors.add(Point3D(2, 1, outerZ))
                } else if (neighbor.y == size) {
                    newNeighbors.add(Point3D(2, 3, outerZ))
                } else if (neighbor.x < 0) {
                    newNeighbors.add(Point3D(1, 2, outerZ))
                } else if (neighbor.x == size) {
                    newNeighbors.add(Point3D(3, 2, outerZ))
                }
                // Inner edges
                else if (isInner && this.x == 1) {
                    repeat(size) {
                        newNeighbors.add(Point3D(0, it, innerZ))
                    }
                } else if (isInner && this.x == 3) {
                    repeat(size) {
                        newNeighbors.add(Point3D(4, it, innerZ))
                    }
                } else if (isInner && this.y == 1) {
                    repeat(size) {
                        newNeighbors.add(Point3D(it, 0, innerZ))
                    }
                } else if (isInner && this.y == 3) {
                    repeat(size) {
                        newNeighbors.add(Point3D(it, 4, innerZ))
                    }
                }
                // Regular edges
                else {
                    newNeighbors.add(neighbor)
                }
            }
            neighbors = newNeighbors
        }
        val count = neighbors.count { it in bugs }
        return ((this in bugs && count == 1) || (this !in bugs && (count == 1 || count == 2)))
    }
}