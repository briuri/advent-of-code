package buri.aoc.y15.d09

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
        assertRun(605, 1)
        assertRun(251, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(982, 1)
        assertRun(898, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val map = mutableMapOf<String, Int>()
        val uniquePlaces = mutableSetOf<String>()
        for (line in input) {
            val tokens = line.split(" = ")
            val places = tokens[0].split(" to ")
            map[places[0] + places[1]] = tokens[1].toInt()
            map[places[1] + places[0]] = tokens[1].toInt()
            uniquePlaces.add(places[0])
            uniquePlaces.add(places[1])
        }

        val paths = generatePermutations(uniquePlaces.toList())
        val distances = mutableListOf<Int>()
        for (path in paths) {
            var distance = 0
            for (i in 0 until path.lastIndex) {
                distance += map[path[i] + path[i + 1]]!!
            }
            distances.add(distance)
        }
        return if (part.isOne()) distances.min() else distances.max()
    }

}