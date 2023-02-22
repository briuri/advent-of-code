package buri.aoc.y17.d24

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
        assertRun(31, 1)
        assertRun(1906, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(19, 1)
        assertRun(1824, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val sizeToStrength = mutableMapOf<Int, Int>()
        val components = mutableListOf<Component>()
        for (line in input) {
            val numbers = line.extractInts()
            components.add(Component(numbers[0], numbers[1]))
        }
        val maxStrength = getStrength(sizeToStrength, 0, 0, 0, components)
        return if (part.isOne()) maxStrength else sizeToStrength[sizeToStrength.keys.max()]!!
    }

    /**
     * Recursively calculates possible strength values to find the max.
     */
    private fun getStrength(
        sizeToStrength: MutableMap<Int, Int>,
        length: Int,
        strength: Int,
        neededPort: Int,
        components: List<Component>
    ): Int {
        var maxStrength = strength
        for (component in components.filter { it.fits(neededPort) }) {
            val otherPort = if (component.portA != neededPort) component.portA else component.portB
            val nextStrength = getStrength(sizeToStrength,
                length + 1, strength + component.getStrength(),
                otherPort, components.filter { it != component })
            maxStrength = maxStrength.coerceAtLeast(nextStrength)
        }
        if (maxStrength == strength) { // No longer bridges found.
            sizeToStrength.putIfAbsent(length, 0)
            sizeToStrength[length] = sizeToStrength[length]!!.coerceAtLeast(maxStrength)
        }
        return maxStrength
    }
}

data class Component(val portA: Int, val portB: Int) {
    /**
     * Returns true if this component has a specific port.
     */
    fun fits(port: Int): Boolean {
        return (portA == port || portB == port)
    }

    /**
     * Returns the strength of this component.
     */
    fun getStrength(): Int {
        return (portA + portB)
    }
}