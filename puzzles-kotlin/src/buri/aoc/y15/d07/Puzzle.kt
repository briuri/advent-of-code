package buri.aoc.y15.d07

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
        assertRun(956, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(40149, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val simpleWires = mutableMapOf<String, Int>()
        val complexWires = mutableMapOf<String, String>()
        for (line in input) {
            val tokens = line.split(" -> ")
            complexWires[tokens[1]] = if (part == TWO && tokens[1] == "b") {
                "956"
            } else {
                tokens[0]
            }
        }

        do {
            val wires = complexWires.filter { it.key !in simpleWires.keys }
            for ((wire, value) in wires) {
                val answer = simplify(value, simpleWires)
                if (answer != null) {
                    simpleWires[wire] = answer
                }
            }
        } while (wires.isNotEmpty())
        return simpleWires["a"]!!
    }

    /**
     * Attempts to solve an operation using all known simple values. Returns null if it can't be solved yet.
     */
    private fun simplify(operation: String, simpleWires: MutableMap<String, Int>): Int? {
        val tokens = operation.split(" ")
        if (tokens.size == 1) { // Simplest form or direct mapping
            return resolveValue(tokens[0], simpleWires)
        }
        if (tokens.size == 2) { // NOT
            return simpleWires[tokens[1]]?.inv()
        }

        val a = resolveValue(tokens[0], simpleWires)
        val b = resolveValue(tokens[2], simpleWires)
        // Give up if there's not enough info to solve yet.
        if (a == null || b == null) {
            return null
        }

        if (tokens[1] == "AND") {
            return (a and b)
        }
        if (tokens[1] == "OR") {
            return (a or b)
        }
        if (tokens[1] == "LSHIFT") {
            return (a shl b)
        }   // RSHIFT
        return (a shr b)
    }

    /**
     * Tries to resolve a wire id to a number
     */
    private fun resolveValue(wire: String, simpleWires: MutableMap<String, Int>): Int? {
        return if (wire.toIntOrNull() != null) wire.toInt() else simpleWires[wire]
    }
}