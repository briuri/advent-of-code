package buri.aoc.y23.d20

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
        assertRun(32000000, 1)
        assertRun(11687500, 2)
        assertRun(788848550, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(228300182686739, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val modules = mutableMapOf<String, Module>()
        for (line in input) {
            val mod = Module(line)
            modules[mod.name] = mod
        }
        for (module in modules.values.filter { it.isConjunction() }) {
            for (inModule in modules.filter { it.value.to.contains(module.name) }.keys) {
                module.initMemory(inModule)
            }
        }

        // Part 1 answer
        var low = 0L
        var high = 0L

        // Part 2: These 4 modules feed into &vd, which is the sole input for rx.
        // When vd receives 4 high pulses, it sends a low to rx.
        val feeders = mutableListOf("rd", "bt", "fv", "pr")
        // Store the four cycle lengths in here.
        val cycles = mutableListOf<Long>()

        // Used 4000 for Part 2 because all feeders hit a cycle before this number.
        repeat(4000) {
            if (part.isOne() && it == 1000) {
                return low * high
            }

            val nextModules = mutableListOf<String>()
            // Account for the button push (1 low pulse)
            low++
            nextModules.add("broadcaster")
            modules["broadcaster"]!!.addPulse("button", false)

            while (nextModules.isNotEmpty()) {
                val name = nextModules.removeFirst()
                val module = modules[name]!!
                for (nextPulse in module.pulse()) {
                    for (t in module.to) {
                        // "output" and "rx" don't actually need to receive these pulses.
                        if (t in modules.keys) {
                            // Part 2: Check for cycles.
                            if (part.isTwo() && t == "vd" && name in feeders && nextPulse) {
                                cycles.add(it.toLong() + 1)
                                if (cycles.size == 4) {
                                    return getLCM(getLCM(getLCM(cycles[0], cycles[1]), cycles[2]), cycles[3])
                                }
                            }
                            modules[t]!!.addPulse(name, nextPulse)
                            // If the target module already had some pulses queued up, it will already be here.
                            if (t !in nextModules) {
                                nextModules.add(t)
                            }
                        }
                        // Count all pulses, even those going to "output" and "rx".
                        if (nextPulse) {
                            high++
                        } else {
                            low++
                        }
                    }
                }
            }
        }
        throw Exception("Did not reach end state correctly.")
    }
}

/**
 * Data class for a module.
 */
class Module(val line: String) {
    val type: Char
    val name: String
    val to = mutableListOf<String>()
    private val inputs = mutableListOf<Pair<String, Boolean>>()
    private val outputs = mutableListOf<Boolean>()
    private val pulseMemory = mutableMapOf<String, Boolean>()
    private var isOn = false

    init {
        val tokens = line.split(" -> ")
        if (tokens[0] == "broadcaster") {
            type = 'b'
            name = tokens[0]
        } else {
            type = tokens[0].first()
            name = tokens[0].drop(1)
        }
        to.addAll(tokens[1].split(", "))
    }

    /**
     * Queues up a pulse.
     */
    fun addPulse(from: String, isHigh: Boolean) {
        inputs.add(Pair(from, isHigh))
    }

    /**
     * Sets the initial value for a memorized pulse from an input.
     */
    fun initMemory(line: String) {
        val name = if (line.startsWith("%") || line.startsWith("&")) {
            line.drop(1)
        } else {
            line
        }
        pulseMemory[name] = false
    }

    /**
     * Returns true if this is a flip flop module.
     */
    private fun isFlipFlop() = (type == '%')

    /**
     * Returns true if this is a conjunction module.
     */
    fun isConjunction() = (type == '&')

    /**
     * Returns true if this is a braodcaster module.
     */
    private fun isBroadcaster() = (type == 'b')

    /**
     * Runs all queued up pulses and returns a possible output pulse.
     */
    fun pulse(): List<Boolean> {
        outputs.clear()
        val (_, lastPulse) = inputs.last()
        while (inputs.isNotEmpty()) {
            val (name, pulse) = inputs.removeFirst()
            if (isBroadcaster()) {
                outputs.add(lastPulse)
            }
            if (isFlipFlop() && !pulse) {
                isOn = !isOn
                outputs.add(isOn)
            }
            if (isConjunction()) {
                pulseMemory[name] = pulse
                outputs.add(!pulseMemory.values.all { it })
            }
        }
        return outputs
    }

    override fun toString() = "$name[$inputs]"
}