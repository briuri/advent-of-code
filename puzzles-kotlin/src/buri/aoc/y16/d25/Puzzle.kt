package buri.aoc.y16.d25

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
        assertRun(180, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val c = input[1].split(" ")[1].toInt()
        val b = input[2].split(" ")[1].toInt()

        /*
        reg[d] = start + (15 * 170)
      A reg[a] = reg[d]
      B reg[b] = reg[a]
        reg[a] = 0

      C reg[c] = 2
      D if (reg[b] != 0) then goto E else goto F
      E reg[b] -= 1
        reg[c] -= 1
        if (reg[c] != 0) then goto D
        reg[a] += 1
        goto C

      F reg[b] = 2
        while (reg[c] != 0) {
           reg[b] -= 1
           reg[c] -= 1
        }
        transmit reg[b]
        if (reg[a] != 0) then goto B else goto A
        */
        val target = b * c
        var n = 1
        while (n < target) {
            n = if (n % 2 == 0) {
                n * 2 + 1
            } else {
                n * 2
            }
        }
        return n - target
    }
}