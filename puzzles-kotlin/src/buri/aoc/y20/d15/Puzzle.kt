package buri.aoc.y20.d15

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
        assertRun(211, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2159626, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val history = mutableMapOf<Int, Pair<Int, Int>>()
        var lastNumber = 0
        for ((index, startingNumber) in input[0].extractInts().withIndex()) {
            history.addNumber(startingNumber, index + 1)
            lastNumber = startingNumber
        }

        var turn = history.size + 1
        val lastTurn = if (part.isOne()) 2020 else 30_000_000
        while (turn <= lastTurn) {
            lastNumber = if (history[lastNumber]!!.first == 0) {
                0
            } else {
                history.getDifference(lastNumber)
            }
            history.addNumber(lastNumber, turn)
            turn++
        }
        return lastNumber
    }

    /**
     * Adds a number to the game history.
     */
    private fun MutableMap<Int, Pair<Int, Int>>.addNumber(number: Int, turn: Int) {
        this[number] = if (number !in this.keys) {
            Pair(0, turn)
        } else {
            Pair(this[number]!!.second, turn)
        }
    }

    /**
     * Returns the difference between the last 2 turns a number was mentioned.
     */
    private fun MutableMap<Int, Pair<Int, Int>>.getDifference(number: Int): Int {
        val pair = this[number]!!
        return pair.second - pair.first
    }
}