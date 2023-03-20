package buri.aoc.y16.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.MD5
import buri.aoc.common.Part
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
        val start = Position(Point2D(0, 0), "")
        val end = Point2D(3, 3)

        val frontier = mutableListOf<Position>()
        frontier.add(start)
        val visited = mutableSetOf<Position>()
        var current: Position?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            visited.add(current)
            if (current.point == end) {
                if (part.isOne()) {
                    return visited.first { it.point == end }.path
                } else {
                    continue
                }
            }
            for (next in getNeighbors(md5, salt, current).filter { it !in visited }) {
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
        val hash = md5.getHash(salt + current.path).substring(0, 4)
        val list = mutableListOf<Position>()
        // Order: U D L R
        with(current) {
            if (hash[0] in openDoors && point.y != 0) {
                list.add(Position(point.copy(y = point.y - 1), path + "U"))
            }
            if (hash[1] in openDoors && point.y != 3) {
                list.add(Position(point.copy(y = point.y + 1), path + "D"))
            }
            if (hash[2] in openDoors && point.x != 0) {
                list.add(Position(point.copy(x = point.x - 1), path + "L"))
            }
            if (hash[3] in openDoors && point.x != 3) {
                list.add(Position(point.copy(x = point.x + 1), path + "R"))
            }
        }
        return list
    }
}

data class Position(val point: Point2D<Int>, val path: String)