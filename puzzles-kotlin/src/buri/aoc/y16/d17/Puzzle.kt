package buri.aoc.y16.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.MD5
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("DDUDRLRRUDRD", 1)
        assertRun("DDURRLRRDD", 0, true)
    }
    @Test
    fun runPart2() {
        assertRun("492", 1)
        assertRun("436", 0, true)
    }

    private val openDoors = "bcdef"

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val md5 = MD5()
        val salt = input[0]
        val start = Position(Pair(0,0), "")
        val end = Pair(3,3)

        val frontier = mutableListOf<Position>()
        val visited = mutableSetOf<Position>()
        frontier.add(start)
        visited.add(start)

        var current: Position?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            visited.add(current)
            if (current.point == end) {
                if (part == ONE) {
                    return visited.filter { it.point == end }[0].path
                }
                else {
                    continue
                }
            }
            for (next in getNeighbors(md5, salt, current).filter { !visited.contains(it) }) {
                frontier.add(next)
            }
        }

        // Part TWO
        val allPaths = visited.filter { it.point == end }
        return allPaths.maxOfOrNull { it.path.length }.toString()
    }

    /**
     * Finds valid places to go from here.
     */
    private fun getNeighbors(md5: MD5, salt: String, current: Position): List<Position> {
        val hash = md5.getMultipleHashes(salt + current.path).substring(0, 4)
        val list = mutableListOf<Position>()
        // Order: U D L R
        with (current) {
            if (hash[0] in openDoors && point.second != 0) {
                list.add(Position(point.copy(second = point.second - 1), path + "U"))
            }
            if (hash[1] in openDoors && point.second != 3) {
                list.add(Position(point.copy(second = point.second + 1), path + "D"))
            }
            if (hash[2] in openDoors && point.first != 0) {
                list.add(Position(point.copy(first = point.first - 1), path + "L"))
            }
            if (hash[3] in openDoors && point.first != 3) {
                list.add(Position(point.copy(first = point.first + 1), path + "R"))
            }
        }
        return list
    }
}
data class Position(val point: Pair<Int, Int>, val path: String) {
    override fun toString(): String {
        return ("$point-$path")
    }
}