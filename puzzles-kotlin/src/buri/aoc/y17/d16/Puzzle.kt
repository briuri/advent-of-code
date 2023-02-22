package buri.aoc.y17.d16

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
        assertRun("gkmndaholjbfcepi", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("abihnfkojcmegldp", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        var order = "abcdefghijklmnop".toCharArray()

        val numDances = if (part.isOne()) 1 else 1_000_000_000
        val visited = mutableListOf<String>()
        for (i in 0 until numDances) {
            for (command in input[0].split(",")) {
                val tokens = command.drop(1).split("/")
                if (command[0] == 's') {
                    val start = order.size - tokens[0].toInt()
                    order = order.sliceArray(start until order.size) +
                            order.sliceArray(0 until start)
                } else {
                    val aIndex = if (command[0] == 'x') tokens[0].toInt() else order.indexOf(tokens[0][0])
                    val bIndex = if (command[0] == 'x') tokens[1].toInt() else order.indexOf(tokens[1][0])
                    val temp = order[aIndex]
                    order[aIndex] = order[bIndex]
                    order[bIndex] = temp
                }
            }

            // Search for a repeated state then extrapolate up to 1 billion
            val snapshot = order.joinToString("")
            if (part.isTwo() && snapshot in visited) {
                return visited[(numDances % visited.size) - 1]
            }
            visited.add(snapshot)
        }
        return order.joinToString("")
    }
}