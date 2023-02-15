package buri.aoc.y17.d15

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
        assertRun(588, 1)
        assertRun(626, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(309, 1)
        assertRun(306, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val multiplesA = if (part == ONE) 1L else 4L
        val multiplesB = if (part == ONE) 1L else 8L
        val genA = Generator(input[0].split(" ")[4].toLong(), 16807L, multiplesA)
        val genB = Generator(input[1].split(" ")[4].toLong(), 48271L, multiplesB)
        val times = if (part == ONE) 40000000 else 5000000
        var same = 0
        for (time in 0 until times) {
            if (genA.getNextLeastBits() == genB.getNextLeastBits()) {
                same++
            }
        }
        return same
    }
}
class Generator(private var current: Long, private val factor: Long, private val multiples: Long) {

    /**
     * Calculates the next value and returns the binary least 16 bits.
     */
    fun getNextLeastBits(): Long {
        do {
            current = (current * factor) % 2147483647
        } while (current % multiples != 0L)
        return (current and 65535L)
    }
}