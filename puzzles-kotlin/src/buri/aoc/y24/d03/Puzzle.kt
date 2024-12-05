package buri.aoc.y24.d03

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.product
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(161, 1)
        assertRun(155955228, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(48, 2)
        assertRun(100189366, 0, true)
    }

    private val doToken = "do()"
    private val dontToken = "don't()"
    private val mulToken = "mul("

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val instructions = input.joinToString("")

        val products = mutableListOf<Long>()
        var isOn = true
        var openIndex = instructions.getNext(0, part)
        while (openIndex in instructions.indices) {
            if (instructions.substring(openIndex).startsWith(doToken)) {
                isOn = true
                openIndex = instructions.getNext(openIndex + doToken.length, part)
                continue
            }
            if (instructions.substring(openIndex).startsWith(dontToken)) {
                isOn = false
                openIndex = instructions.getNext(openIndex + dontToken.length, part)
                continue
            }
            val mul = instructions.substring(openIndex..instructions.indexOf(")", openIndex))
            val factors = mul.replace(mulToken, "").replace(")", "").split(",")
            if (isOn && factors.size == 2 && factors.all { it.toIntOrNull() != null }) {
                products.add(factors.map { it.toInt() }.product())
            }
            openIndex = instructions.getNext(openIndex + mulToken.length, part)
        }
        return products.sum()
    }

    /**
     * Returns the position of the next instruction: mul(, do(), don't()
     */
    private fun String.getNext(currentIndex: Int, part: Part): Int {
        val mulIndex = this.indexOf(mulToken, currentIndex)
        val doIndex = this.indexOf(doToken, currentIndex)
        val dontIndex = this.indexOf(dontToken, currentIndex)
        val indices = listOf(mulIndex, doIndex, dontIndex).filter { it > 0 }
        return if (part.isOne()) {
            mulIndex
        } else if (indices.isEmpty()) {
            -1
        } else {
            indices.min()
        }
    }
}