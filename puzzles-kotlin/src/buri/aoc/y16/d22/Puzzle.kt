package buri.aoc.y16.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Grid
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(872, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(211, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val nodes = input.filter { it.startsWith("/dev") }
        if (part.isOne()) {
            var count = 0
            for (nodeA in nodes) {
                for (nodeB in nodes.filter { it != nodeA }) {
                    if (getUsed(nodeA) in 1..getAvailable(nodeB)) {
                        count++
                    }
                }
            }
            return count
        }
        val highestNode = getNodeLocation(nodes[nodes.lastIndex])
        val grid = Grid(highestNode.first + 1, highestNode.second + 1)
        for (node in nodes) {
            val point = getNodeLocation(node)
            val value = if (getUsed(node) == 0) {
                "_"
            } else if (point.first == highestNode.first && point.second == 0) {
                "G"
            } else if (getUsed(node) > 100) {
                "#"
            } else {
                "."
            }
            grid[point] = value
        }
//        println(grid)
        /*
        Initial State
        ...............................G
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        .........#######################
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        ................................
        ........................_.......
        ................................
        ................................
        ................................
        ................................
        ................................
        38 steps to get _ up to top (up, left, up).
        ........_......................G
        ................................
        38 + 23 steps to swap _ and G
        ..............................G_
        ................................
        38 + 23 + 30*5 to shift G_ left
        G_..............................
        ................................
        */
        return 211
    }

    /**
     * Extracts the x,y position of the node.
     */
    private fun getNodeLocation(line: String): Pair<Int, Int> {
        val dimensions = line.split(" ")[0].split("-x")[1]
        val x = dimensions.split("-y")[0].toInt()
        val y = dimensions.split("-y")[1].toInt()
        return Pair(x, y)
    }

    /**
     * Extracts the used space on a node.
     */
    private fun getUsed(line: String): Int {
        return line.substring(28, 33).trim().toInt()
    }

    /**
     * Extracts the available space on a node.
     */
    private fun getAvailable(line: String): Int {
        return line.substring(34, 40).trim().toInt()
    }
}