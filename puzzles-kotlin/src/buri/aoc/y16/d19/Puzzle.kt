package buri.aoc.y16.d19

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
        assertRun(3, 1)
        assertRun(1842613, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2, 1)
        assertRun(1424135, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numElves = input[0].toInt()
        if (part.isOne()) {
            val elves = ArrayDeque<Int>()
            elves.addAll(1..numElves)
            while (elves.size > 1) {
                elves.addLast(elves.removeFirst())
                elves.removeFirst()
            }
            return elves[0]
        }

        // Part TWO
        // Use two deques. The point between them is the elf across the circle.
        val elvesBefore = ArrayDeque<Int>()
        elvesBefore.addAll(1..(numElves / 2))
        val elvesAfter = ArrayDeque<Int>()
        elvesAfter.addAll((numElves / 2 + 1)..numElves)

        while (elvesBefore.size + elvesAfter.size > 1) {
            if (elvesBefore.size > elvesAfter.size) {
                elvesBefore.removeLast()
            } else {
                elvesAfter.removeFirst()
            }
            elvesAfter.addLast(elvesBefore.removeFirst())
            elvesBefore.addLast(elvesAfter.removeFirst())
        }
        return elvesBefore.removeFirstOrNull() ?: elvesAfter.removeFirst()
    }
}