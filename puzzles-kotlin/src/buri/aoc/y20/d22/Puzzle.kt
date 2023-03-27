package buri.aoc.y20.d22

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
        assertRun(306, 1)
        assertRun(33680, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(291, 1)
        assertRun(33683, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val deck = ArrayDeque<Int>()
        val crabDeck = ArrayDeque<Int>()
        val spaceLine = input.indexOf("")
        deck.addAll(input.subList(1, spaceLine).map { it.toInt() })
        crabDeck.addAll(input.subList(spaceLine + 2, input.size).map { it.toInt() })

        val playerWins = play(part, deck, crabDeck)
        val winningDeck = if (playerWins) deck else crabDeck
        var score = 0
        for ((index, card) in winningDeck.reversed().withIndex()) {
            score += (index + 1) * card
        }
        return score
    }

    /**
     * Returns true if the player beats the crab.
     */
    private fun play(part: Part, deck1: ArrayDeque<Int>, deck2: ArrayDeque<Int>): Boolean {
        val visited = mutableSetOf<String>()
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            if (part.isTwo()) {
                val key = "$deck1|$deck2"
                if (key in visited) {
                    return true
                }
                visited.add(key)
            }

            val card1 = deck1.removeFirst()
            val card2 = deck2.removeFirst()
            val playerWins = if (part.isTwo() && deck1.size >= card1 && deck2.size >= card2) {
                // Play recursive game.
                play(part, copyDeck(deck1, card1), copyDeck(deck2, card2))
            } else {
                // High card wins.
                card1 > card2
            }

            if (playerWins) {
                deck1.addLast(card1)
                deck1.addLast(card2)
            } else {
                deck2.addLast(card2)
                deck2.addLast(card1)
            }
        }
        return (deck1.isNotEmpty())
    }

    /**
     * Makes a copy of the deck and drops the extra cards at the end.
     */
    private fun copyDeck(deck: ArrayDeque<Int>, size: Int): ArrayDeque<Int> {
        return ArrayDeque(deck.subList(0, size))
    }
}