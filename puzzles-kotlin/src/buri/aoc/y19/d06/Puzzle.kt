package buri.aoc.y19.d06

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
        assertRun(42, 1)
        assertRun(139597, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(4, 2)
        assertRun(286, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val orbits = mutableMapOf<String, String?>()
        orbits["COM"] = null

        for (line in input) {
            val tokens = line.split(")")
            orbits[tokens[1]] = tokens[0]
        }
        if (part.isOne()) {
            return orbits.keys.sumOf { buildPath(orbits, it).size }
        }
        val youPath = buildPath(orbits, "YOU")
        val sanPath = buildPath(orbits, "SAN")
        val intersection = youPath.intersect(sanPath.toSet()).first()
        return youPath.indexOf(intersection) + sanPath.indexOf(intersection) - 2
    }

    /**
     * Builds a path back to the center of the orbits.
     */
    private fun buildPath(orbits: MutableMap<String, String?>, obj: String): List<String> {
        val path = mutableListOf<String>()
        var current = obj
        while (current != "COM") {
            path.add(current)
            current = orbits[current]!!
        }
        return path
    }
}