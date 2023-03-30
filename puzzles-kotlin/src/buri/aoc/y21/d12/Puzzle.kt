package buri.aoc.y21.d12

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
        assertRun(226, 1)
        assertRun(4411, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3509, 1)
        assertRun(136767, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val caves = Caves(input)
        return caves.explore(part, mutableListOf(), "start")
    }
}

class Caves(data: List<String>) {
    val map = mutableMapOf<String, MutableSet<String>>()

    init {
        for (line in data) {
            val tokens = line.split("-")
            map.putIfAbsent(tokens[0], mutableSetOf())
            map[tokens[0]]!!.add(tokens[1])
            map.putIfAbsent(tokens[1], mutableSetOf())
            map[tokens[1]]!!.add(tokens[0])
        }
    }

    /**
     * Returns the number of paths that get from start to "end"
     */
    fun explore(part: Part, smallCaves: List<String>, start: String): Int {
        if (start == "end") {
            return 1
        }
        val visited = mutableListOf<String>()
        visited.addAll(smallCaves)

        if (start.lowercase() == start) {
            visited.add(start)
        }
        var pathCount = 0
        for (next in getNext(start)) {
            val neverVisited = (next !in visited)
            val alreadyVisitedTwice = (visited.toSet().size == visited.size)
            if (part.isOne() && neverVisited) {
                pathCount += explore(part, visited, next)
            }
            if (part.isTwo() && (neverVisited || alreadyVisitedTwice)) {
                pathCount += explore(part, visited, next)
            }
        }
        return pathCount
    }

    /**
     * Returns the options to visit next.
     */
    private fun getNext(name: String): List<String> = map[name]!!.filter { it != "start" }
}