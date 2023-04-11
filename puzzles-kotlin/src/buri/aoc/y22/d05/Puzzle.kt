package buri.aoc.y22.d05

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("CMZ", 1)
        assertRun("TGWSMRBPN", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("MCD", 1)
        assertRun("TZLTLWRNF", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val spaceLine = input.indexOf("")
        val numStacks = input[spaceLine - 1].extractInts().last()
        val stacks = mutableMapOf<Int, ArrayDeque<Char>>()
        for (i in 1..numStacks) {
            stacks[i] = ArrayDeque()
            for (j in spaceLine - 2 downTo 0) {
                val line = input[j].padEnd(40, ' ')
                val index = (i - 1) * 4 + 1
                val value = line[index]
                if (value.isLetter()) {
                    stacks[i]!!.addFirst(value)
                } else {
                    break
                }
            }
        }

        for (line in input.subList(spaceLine + 1, input.size)) {
            val numbers = line.extractInts()
            if (part.isOne()) {
                for (i in 0 until numbers[0]) {
                    val value = stacks[numbers[1]]!!.removeFirst()
                    stacks[numbers[2]]!!.addFirst(value)
                }
            } else {
                val chunk = ArrayDeque<Char>()
                for (i in 0 until numbers[0]) {
                    chunk.addFirst(stacks[numbers[1]]!!.removeFirst())
                }
                while (chunk.isNotEmpty()) {
                    stacks[numbers[2]]!!.addFirst(chunk.removeFirst())
                }
            }
        }
        return stacks.values.map { it.first() }.joinToString("")
    }
}