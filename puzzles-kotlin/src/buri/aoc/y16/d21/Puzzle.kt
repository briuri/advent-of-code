package buri.aoc.y16.d21

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.TWO
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("aefgbcdh", 0, true)
    }
    @Test
    fun runPart2() {
        assertRun("egcdahbf", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val isReverse = (part == TWO)
        val password = if (isReverse) "fbgdceah" else "abcdefgh"
        val builder = StringBuilder(password)
        val partInput = if (isReverse) input.reversed() else input
        for (line in partInput) {
            val tokens = line.split(" ")
            // Symmetric operations
            if (line.startsWith("swap position")) {
                val x = tokens[2].toInt()
                val y = tokens[5].toInt()
                val value = builder[x]
                builder[x] = builder[y]
                builder[y] = value
            }
            else if (line.startsWith("swap letter")) {
                val x = builder.indexOf(tokens[2])
                val y = builder.indexOf(tokens[5])
                val value = builder[x]
                builder[x] = builder[y]
                builder[y] = value
            }
            else if (line.startsWith("reverse")) {
                val x = tokens[2].toInt()
                val y = tokens[4].toInt()
                val value = builder.substring(x..y)
                builder.replace(x, y + 1, value.reversed())
            }
            // Assymmetric operations
            else if (line.startsWith("rotate")) {
                var x: Int
                if (tokens[1] ==  "based") {
                    /*
                          abcdefgh
                        0 habcdefg 7
                        1 ghabcdef 7
                        2 fghabcde 2
                        3 efghabcd 6
                        4 cdefghab 1
                        5 bcdefgha 5
                        6 abcdefgh 0
                        7 habcdefg 4
                     */
                    x = builder.indexOf(tokens[6])
                    if (isReverse) {
                        x = "77261504"[x].digitToInt()
                    }
                    else {
                        x += if (x >= 4) 2 else 1
                    }
                }
                else {
                    x = tokens[2].toInt()
                    if (tokens[1] == "left") {
                        x = builder.length - x
                    }
                    if (isReverse) {
                        x = builder.length - x
                    }
                }
                for (i in 0 until x) {
                    val value = builder[builder.lastIndex]
                    builder.delete(builder.lastIndex, builder.length)
                    builder.insert(0, value)
                }
            }
            else if (line.startsWith("move")) {
                var x = tokens[2].toInt()
                var y = tokens[5].toInt()
                if (isReverse) {
                    x = y.also { y = x }
                }
                val value = builder[x]
                builder.deleteCharAt(x)
                builder.insert(y, value)
            }
        }
        return builder.toString()
    }
}