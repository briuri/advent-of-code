package buri.aoc.y17.d24

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
        assertRun(31, 1)
        assertRun(1906, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(19, 1)
        assertRun(1824, 0, true)
    }

    private val sizeToStrength = mutableMapOf<Int, Int>()

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        sizeToStrength.clear()
        val components = mutableListOf<Component>()
        for (line in input) {
            val tokens = line.split("/")
            components.add(Component(tokens[0].toInt(), tokens[1].toInt()))
        }
        val maxStrength = getStrength(0, 0, 0, components)
        return if (part == ONE) maxStrength else sizeToStrength[sizeToStrength.keys.max()]!!
    }

    /**
     * Recursively calculates possible strength values to find the max.
     */
    private fun getStrength(length: Int, strength: Int, neededPort: Int, components: List<Component>): Int {
        var maxStrength = strength
        for (component in components.filter { it.fits(neededPort) }) {
            val otherPort = if (component.portA != neededPort) component.portA else component.portB
            val nextStrength = getStrength(length + 1, strength + component.getStrength(), otherPort,
                components.filter { it != component })
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