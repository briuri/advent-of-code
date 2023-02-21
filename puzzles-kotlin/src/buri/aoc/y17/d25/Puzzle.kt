package buri.aoc.y17.d25

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
        assertRun(3, 1)
        assertRun(5593, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val states = mutableMapOf<String, State>()
        var i = 0
        while (i in input.indices) {
            if (input[i].startsWith("In state")) {
                val name = input[i].split("state ")[1].dropLast(1)
                states[name] = State(input.subList(i + 1, i + 9))
            }
            i++
        }
        val steps = input[1].split(" ")[5].toInt()
        var nextStateName = input[0].split(" ")[3].dropLast(1)

        val ticker = mutableMapOf<Int, Int>()
        var cursor = 0
        repeat(steps) {
            ticker.putIfAbsent(cursor, 0)
            val current = ticker[cursor]!!

            val state = states[nextStateName]!!
            ticker[cursor] = state.write[current]!!
            cursor += state.move[current]!!
            nextStateName = state.next[current]!!
        }
        return ticker.values.sum()
    }
}

data class State(private val input: List<String>) {
    val write = mutableMapOf<Int, Int>()
    val move = mutableMapOf<Int, Int>()
    val next = mutableMapOf<Int, String>()

    init {
        write[0] = input[1].extractInts(false)[0]
        write[1] = input[5].extractInts(false)[0]
        move[0] = toOffset(input[2].split("the ")[1].dropLast(1))
        move[1] = toOffset(input[6].split("the ")[1].dropLast(1))
        next[0] = input[3].split("state ")[1].dropLast(1)
        next[1] = input[7].split("state ")[1].dropLast(1)
    }

    /**
     * Converts right or left to 1 or -1.
     */
    private fun toOffset(direction: String): Int {
        return if (direction == "right") 1 else -1
    }

    override fun toString(): String {
        return "write=$write move=$move next=$next"
    }
}