package buri.aoc.y20.d14

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
        assertRun(165, 1)
        assertRun(9296748256641, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(208, 2)
        assertRun(4877695371685, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val mem = mutableMapOf<Long, Long>()
        var mask = CharArray(36)
        for (line in input) {
            if (line.startsWith("mask")) {
                mask = line.split(" = ")[1].toCharArray()
            } else {
                val register = line.split("] = ")[0].split("[")[1].toLong()
                var value = line.split(" = ")[1].toLong()
                if (part.isOne()) {
                    value = maskValue(mask, value)
                    mem[register] = value
                } else {
                    for (maskedRegister in maskAddress(mask, register)) {
                        mem[maskedRegister] = value
                    }
                }
            }
        }
        return mem.values.sum()
    }

    /**
     * Applies the mask to a value.
     */
    private fun maskValue(mask: CharArray, value: Long): Long {
        val masked = toBinary(value)
        for (i in masked.indices) {
            if (mask[i] != 'X') {
                masked[i] = mask[i]
            }
        }
        return masked.joinToString("").toLong(2)
    }

    /**
     * Applies the mask to an address.
     */
    private fun maskAddress(mask: CharArray, address: Long): List<Long> {
        var list = mutableListOf<String>()
        val masked = toBinary(address)
        for (i in masked.indices) {
            if (mask[i] != '0') {
                masked[i] = mask[i]
            }
        }
        list.add(masked.joinToString(""))
        while (list.any { it.contains("X") }) {
            val newList = mutableListOf<String>()
            for (xAddress in list) {
                newList.add(xAddress.replaceFirst('X', '0'))
                newList.add(xAddress.replaceFirst('X', '1'))
            }
            list = newList
        }
        return list.map { it.toLong(2) }
    }

    /**
     * Converts decimal to binary.
     */
    private fun toBinary(value: Long): CharArray {
        return value.toString(2).padStart(36, '0').toCharArray()
    }
}