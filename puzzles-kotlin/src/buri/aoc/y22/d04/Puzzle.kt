package buri.aoc.y22.d04

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
        assertRun(513, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(878, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var count = 0
        for (line in input) {
            val numbers = line.extractInts(false)
            val elf1 = numbers[0]..numbers[1]
            val elf2 = numbers[2]..numbers[3]
            if (part.isOne()) {
                if ((elf2.first in elf1 && elf2.last in elf1) || (elf1.first in elf2 && elf1.last in elf2)) {
                    count++
                }
            } else {
                if (elf2.first in elf1 || elf2.last in elf1 || elf1.first in elf2 || elf1.last in elf2) {
                    count++
                }
            }
        }
        return count
    }
}