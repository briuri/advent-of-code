package buri.aoc.y16.d03

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(1032, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(1838, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val triangles = mutableListOf<List<Int>>()
        val triangle1 = mutableListOf<Int>()
        val triangle2 = mutableListOf<Int>()
        val triangle3 = mutableListOf<Int>()
        for (line in input) {
            val sides = line.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            if (part == Part.ONE) {
                triangles.add(sides.sorted())
            } else {
                triangle1.add(sides[0])
                triangle2.add(sides[1])
                triangle3.add(sides[2])
                if (triangle1.size == 3) {
                    triangles.add(triangle1.sorted())
                    triangles.add(triangle2.sorted())
                    triangles.add(triangle3.sorted())
                    triangle1.clear()
                    triangle2.clear()
                    triangle3.clear()
                }
            }
        }
        var possible = 0
        for (triangle in triangles) {
            if (triangle[0] + triangle[1] > triangle[2]) {
                possible++
            }
        }
        return possible
    }
}