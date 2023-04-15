package buri.aoc.y22.d18

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
        assertRun(64, 1)
        assertRun(4242, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(58, 1)
        assertRun(2428, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val cubes = mutableSetOf<Point3D<Int>>()
        for (line in input) {
            val numbers = line.extractInts()
            cubes.add(Point3D(numbers[0], numbers[1], numbers[2]))
        }

        var allSides = 0
        for (cube in cubes) {
            allSides += (6 - getNeighbors(cube).intersect(cubes).size)
        }
        if (part.isOne()) {
            return allSides
        }

        val allSpaces = mutableSetOf<Point3D<Int>>()
        for (cube in cubes) {
            allSpaces.addAll(getNeighbors(cube).filter { it !in cubes })
        }

        val innerSpaces = mutableSetOf<Point3D<Int>>()
        for (space in allSpaces) {
            if (space !in innerSpaces) {
                val reachable = mutableSetOf<Point3D<Int>>()
                val bounded = explore(cubes, reachable, space)
                if (bounded) {
                    innerSpaces.addAll(reachable)
                }
            }
        }

        var innerSides = 0
        for (space in innerSpaces) {
            innerSides += (6 - getNeighbors(space).filter { it !in cubes }.size)
        }

        return allSides - innerSides
    }

    /**
     * Explores from a space. Returns true if the space is bounded.
     */
    private fun explore(cubes: Set<Point3D<Int>>, innerSpaces: MutableSet<Point3D<Int>>, space: Point3D<Int>): Boolean {
        // Big enough for input data
        if (space.x !in 0..20 || space.y !in 0..20 || space.z !in 0..20) {
            return false
        }
        innerSpaces.add(space)
        for (neighbor in getNeighbors(space).filter { it !in cubes && it !in innerSpaces }) {
            if (!explore(cubes, innerSpaces, neighbor)) {
                return false
            }
        }
        return true
    }

    /**
     * Returns non-diagonal neighbors of the cube.
     */
    private fun getNeighbors(cube: Point3D<Int>): MutableSet<Point3D<Int>> {
        val neighbors = mutableSetOf<Point3D<Int>>()
        neighbors.add(Point3D(cube.x - 1, cube.y, cube.z))
        neighbors.add(Point3D(cube.x + 1, cube.y, cube.z))
        neighbors.add(Point3D(cube.x, cube.y - 1, cube.z))
        neighbors.add(Point3D(cube.x, cube.y + 1, cube.z))
        neighbors.add(Point3D(cube.x, cube.y, cube.z - 1))
        neighbors.add(Point3D(cube.x, cube.y, cube.z + 1))
        return neighbors
    }
}