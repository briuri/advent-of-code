package buri.aoc.y18.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
import buri.aoc.common.countSteps
import buri.aoc.common.position.Grid
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
        assertRun(23, 1)
        assertRun(3633, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(8756, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val map = Map(210, 210)
        val fullPath = input[0].drop(1).dropLast(1)
        map.explore(mutableSetOf(), map.start.x, map.start.y, fullPath)

        // Use a pathfinder that avoids walls.
        val pathfinder = Pathfinder<Point2D<Int>> { current ->
            map.getNeighbors(current).filter { map[it] != '#' }
        }

        val cameFrom = pathfinder.exploreFrom(map.start)
        val steps = mutableMapOf<Point2D<Int>, Int>()
        // Rooms are any square that isn't a door or wall.
        for (room in cameFrom.keys.filter { map[it] == '.' }) {
            // Each movement is 2 steps to account for the door.
            steps[room] = cameFrom.countSteps(map.start, room) / 2
        }

        return if (part.isOne()) {
            steps.maxOf { it.value }
        } else {
            steps.count { it.value >= 1000 }
        }
    }
}

class Map(width: Int, height: Int) : Grid<Char>(width, height, '#') {
    val start = Point2D(width / 2, height / 2)

    init {
        set(start, '.')
    }

    /**
     * Follows the path through the map.
     */
    fun explore(visited: MutableSet<String>, startX: Int, startY: Int, path: String) {
        val visitedKey = "$startX,$startY-$path"
        if (visitedKey in visited) {
            return
        }
        visited.add(visitedKey)

        var i = 0
        var x = startX
        var y = startY
        while (i in path.indices) {
            when (path[i]) {
                'N' -> {
                    set(x, y - 1, 'D')
                    set(x, y - 2, '.')
                    y -= 2
                }

                'S' -> {
                    set(x, y + 1, 'D')
                    set(x, y + 2, '.')
                    y += 2
                }

                'W' -> {
                    set(x - 1, y, 'D')
                    set(x - 2, y, '.')
                    x -= 2
                }

                'E' -> {
                    set(x + 1, y, 'D')
                    set(x + 2, y, '.')
                    x += 2
                }

                '(' -> {
                    val endIndex = getClosingIndex(path, i + 1)
                    for (choice in getChoices(path.substring(i + 1, endIndex))) {
                        explore(visited, x, y, choice + path.substring(endIndex + 1))
                    }
                    break
                }

                ')' -> break
            }
            i++
        }
    }

    /**
     * Returns the index of the closing ) that matches the opening ('s depth.
     */
    private fun getClosingIndex(path: String, startIndex: Int): Int {
        var depth = 0
        for (i in startIndex until path.length) {
            when (path[i]) {
                '(' -> depth++
                ')' -> {
                    if (depth == 0) {
                        return i
                    }
                    depth--
                }
            }
        }
        return -1
    }

    /**
     * Splits up the options in a choice group.
     */
    private fun getChoices(choiceString: String): List<String> {
        val choices = mutableListOf<String>()
        var depth = 0
        var prev = 0
        for (i in choiceString.indices) {
            if (choiceString[i] == '(') {
                depth++
            } else if (choiceString[i] == ')') {
                depth--
            } else if (choiceString[i] == '|' && depth == 0) {
                choices.add(choiceString.substring(prev, i))
                prev = i + 1
            }
            if (i == choiceString.lastIndex) {
                choices.add(choiceString.substring(prev, choiceString.length))
            }
        }
        return choices
    }
}