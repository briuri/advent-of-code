package buri.aoc.y22.d10

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
        assertRun("13140", 1)
        assertRun("14560", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■", 1)
        // EKRHEPUZ
        assertRun("■■■■ ■  ■ ■  ■ ■  ■ ■■■■ ■     ■■  ■■", 0, true)
    }

    private val signalCycles = listOf(20, 60, 100, 140, 180, 220)

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        var x = 1
        var cycle = 0
        var signals = mutableMapOf<Int, Int>()
        for (line in input) {
            if (line.startsWith("noop")) {
                cycle++
                signals[cycle] = x
            } else {
                cycle++
                signals[cycle] = x
                cycle++
                signals[cycle] = x
                x += line.extractInts().first()
            }
        }
        if (part.isOne()) {
            return signals.filter { it.key in signalCycles }.map { it.key * it.value }.sum().toString()
        }

        val output = StringBuilder()
        for (signal in signals.values) {
            val target = (output.length) % 40
            output.append(if (target in (signal - 1)..(signal + 1)) '■' else ' ')
        }
        return output.toString().chunked(40).joinToString("\n")
    }
}