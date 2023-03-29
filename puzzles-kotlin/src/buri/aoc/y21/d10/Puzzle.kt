package buri.aoc.y21.d10

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
        assertRun(26397, 1)
        assertRun(299793, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(288957, 1)
        assertRun(3654963618, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var corruptScore = 0L
        val completeScores = mutableListOf<Long>()
        for (line in input) {
            var isCorrupt = false
            val stack = ArrayDeque<Char>()
            for (value in line) {
                when (value) {
                    '(', '[', '{', '<' -> stack.addFirst(value)
                    else -> {
                        val matching = getMatch(value)
                        if (stack.first() != matching) {
                            isCorrupt = true
                            corruptScore += getScore(value)
                            break
                        } else {
                            stack.removeFirst()
                        }
                    }
                }
            }
            if (part.isTwo() && !isCorrupt) {
                var score = 0L
                while (stack.isNotEmpty()) {
                    val value = stack.removeFirst()
                    score = score * 5 + getScore(value)
                }
                completeScores.add(score)
            }
        }
        if (part.isOne()) {
            return corruptScore
        }

        val midIndex = completeScores.size / 2
        return completeScores.sorted()[midIndex]
    }

    /**
     * Gets the opposite match for a character.
     */
    private fun getMatch(value: Char): Char {
        return when (value) {
            ')' -> '('
            ']' -> '['
            '}' -> '{'
            else -> '<'
        }
    }

    /**
     * Gets the score for a  character.
     */
    private fun getScore(value: Char): Int {
        return when (value) {
            '(' -> 1
            '[' -> 2
            '{' -> 3
            '<' -> 4
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            else -> 25137
        }
    }
}