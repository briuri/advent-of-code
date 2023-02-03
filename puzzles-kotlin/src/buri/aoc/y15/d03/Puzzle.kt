package buri.aoc.y15.d03

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
        assertRun(2, 1)
        assertRun(2081, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(11, 1)
        assertRun(2341, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val santas = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until part.number) {
            santas.add(Pair(0, 0))
        }
        val visited = mutableSetOf<Pair<Int, Int>>()
        visited.add(santas[0])

        for (direction in input[0]) {
            val activeSanta = santas.removeAt(0)
            val movedSanta = when (direction) {
                '<' -> {
                    activeSanta.copy(first = activeSanta.first - 1)
                }
                '>' -> {
                    activeSanta.copy(first = activeSanta.first + 1)
                }
                '^' -> {
                    activeSanta.copy(second = activeSanta.second - 1)
                }
                else -> {   // v
                    activeSanta.copy(second = activeSanta.second + 1)
                }
            }
            visited.add(movedSanta)
            santas.add(movedSanta)
        }
        return visited.size
    }
}