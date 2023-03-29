package buri.aoc.y21.d08

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
        assertRun(26, 1)
        assertRun(284, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(61229, 1)
        assertRun(973499, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var sum = 0
        for (line in input) {
            val tokens = line.split(" | ")
            val inCodes = tokens[0].split(" ")
            val outCodes = tokens[1].split(" ")
            sum += if (part.isOne()) {
                outCodes.count { it.length in listOf(2, 3, 4, 7) }
            } else {
                val digit = Digit(inCodes)
                val output = StringBuilder()
                for (code in outCodes) {
                    output.append(digit.parse(code))
                }
                output.toString().toInt()
            }
        }
        return sum
    }
}

/**
 * Digit    Wires   Letters             aaaa
 * 1        2       cf                 b    c
 * 7        3       acf                b    c
 * 4        4       bcdf               b    c
 * 8        7       abcdefg            b    c
 * 2        5       acdeg               dddd
 * 3        5       acdfg              e    f
 * 5        5       abdfg              e    f
 * 0        6       abcefg             e    f
 * 6        6       abdefg             e    f
 * 9        6       abcdfg              gggg
 *
 * Segment  Fives:  Sixes:
 * a        3       3
 * b        1       3
 * c        2       2
 * d        3       2
 * e        1       2
 * f        2       3
 * g        3       3
 */
class Digit(data: List<String>) {
    private val segments = mutableMapOf<Char, Char>()

    init {
        val one = data.first { it.length == 2 }
        val seven = data.first { it.length == 3 }
        val four = data.first { it.length == 4 }
        val eight = data.first { it.length == 7 }

        val lenFive = data.filter { it.length == 5 }
        val lenSix = data.filter { it.length == 6 }

        // a (in 7 but not 1)
        segments['a'] = seven.first { it !in one }

        // b (in 4 and appears 1 time in 2,3,5)
        segments['b'] = four.first { count(lenFive, it) == 1 }

        // c (in 1 and appears 2 times in 2,3,5 and 2 times in 0,6,9)
        segments['c'] = one.first { count(lenFive, it) == 2 && count(lenSix, it) == 2 }

        // d (in 4 and appears 3 times in 2,3,5)
        segments['d'] = four.first { count(lenFive, it) == 3 }

        // e (in 8 and appears 1 time in 2,3,5 and 2 times in 0,6,9)
        segments['e'] = eight.first { count(lenFive, it) == 1 && count(lenSix, it) == 2 }

        // f (in 8 and appears 3 times in 2,3,5 and 3 times in 0,6,9)
        segments['f'] = eight.first { count(lenFive, it) == 3 && count(lenSix, it) == 3 }
    }

    /**
     * Converts a string of segments into a digit.
     */
    fun parse(code: String): Char {
        return when (code.length) {
            2 -> '1'
            3 -> '7'
            4 -> '4'
            7 -> '8'
            5 -> {
                if (segment('c') in code && segment('e') in code) {
                    '2'
                } else if (segment('c') in code && segment('f') in code) {
                    '3'
                } else {
                    '5'
                }
            }
            else -> {
                if (segment('c') in code && segment('e') in code) {
                    '0'
                } else if (segment('d') in code && segment('e') in code) {
                    '6'
                } else {
                    '9'
                }
            }
        }
    }

    /**
     * Looks up the value mapped to this segment.
     */
    private fun segment(name: Char): Char = segments[name]!!

    /**
     * Returns the number of times a segment appears in a list of strings.
     */
    private fun count(list: List<String>, value: Char): Int = list.count { value in it }
}