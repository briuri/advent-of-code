package buri.aoc.y17.d10

import buri.aoc.common.BasePuzzle
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
        assertRun("2928", 0, true)
    }
    @Test
    fun runPart2() {
        assertRun("0c2f794b2eb555f7830766bf8fb65a16", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val size = 256
        val circle = ArrayDeque<Int>()
        for (i in 0 until size) {
            circle.add(i)
        }
        val lengths = if (part == ONE) {
            input[0].split(",").map { it.toInt() }
        }
        else {
            input[0].map { it.code } + listOf(17, 31, 73, 47, 23)
        }
        val numRounds = if (part == ONE) 1 else 64

        var current = 0
        var skipSize = 0
        for (round in 0 until numRounds) {
            for (length in lengths) {
                val twist = ArrayDeque<Int>()
                // Reverse the sublist.
                for (i in 0 until length) {
                    twist.addFirst(circle.removeFirst())
                }
                while (twist.isNotEmpty()) {
                    circle.addLast(twist.removeFirst())
                }
                // Move ahead so first element in the circle is the current position.
                for (i in 0 until skipSize) {
                    circle.addLast(circle.removeFirst())
                }
                // Keep track of where we are in relation to 0th position.
                current = (current + length + skipSize)
                skipSize++
            }
        }
        // Reset the circle so the 0th position is first.
        for (i in 0 until current % size) {
            circle.addFirst(circle.removeLast())
        }
        if (part == ONE) {
            return (circle.removeFirst() * circle.removeFirst()).toString()
        }

        val chunks = circle.chunked(16) { it.fold(0) { acc, next -> acc xor next } }
        return chunks.joinToString("") { it.toString(16).padStart(2, '0') }
    }
}