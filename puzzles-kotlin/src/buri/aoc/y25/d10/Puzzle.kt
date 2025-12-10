package buri.aoc.y25.d10

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
        assertRun(7, 1)
        assertRun(491, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(33, 1)
        // Did Part 2 in Python with Z3 (naive solution below).
        // Still fighting the Java Z3 syntax to get the solution ported to Kotlin...
//        assertRun(20617, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val machines = mutableListOf<Machine>()
        for (line in input) {
            val end = line.split("] ")[0].drop(1)
            val buttons = line.split("] ")[1].split(" {")[0]
            val voltage = line.split(" {")[1].dropLast(1)
            machines.add(Machine(end, buttons, voltage))
        }

        var sum = 0L
        for (machine in machines) {
            sum += if (part.isOne()) {
                machine.getBestLights()
            } else {
                machine.getBestJolts()
            }
        }
        return sum
    }
}

data class Machine(val endLights: String, val rawButtons: String, val rawJolts: String) {
    val startLights = ".".repeat(endLights.length)

    val buttons = mutableListOf<List<Int>>()

    init {
        for (each in rawButtons.split(" ")) {
            val numbers = each.extractInts()
            buttons.add(numbers)
        }
    }

    val endJolts = rawJolts.extractInts().toMutableList()
    val startJolts = mutableListOf<Int>()

    init {
        repeat(endJolts.size) {
            startJolts.add(0)
        }
    }

    fun getBestLights(): Int {
        val frontier = mutableListOf<LightState>()
        frontier.add(LightState(startLights, 0))

        val best = mutableMapOf<String, Int>()
        while (frontier.isNotEmpty()) {
            val current = frontier.removeFirst()
            if (current.lights !in best || best[current.lights]!! > current.steps) {
                best[current.lights] = current.steps
                for (button in buttons) {
                    frontier.add(LightState(toggleLights(current.lights, button), current.steps + 1))
                }
            }
        }
        return best[endLights]!!
    }

    fun toggleLights(current: String, button: List<Int>): String {
        val builder = StringBuilder(current)
        for (toggle in button) {
            val old = builder[toggle]
            builder[toggle] = when (old) {
                '#' -> '.'
                else -> '#'
            }
        }
        return builder.toString()
    }

    fun getBestJolts(): Int {
        val frontier = mutableListOf<JoltState>()
        frontier.add(JoltState(startJolts, 0))

        val best = mutableMapOf<String, Int>()
        while (frontier.isNotEmpty()) {
            val current = frontier.removeFirst()
            if (current.key !in best || best[current.key]!! > current.steps) {

                best[current.key] = current.steps
                for (button in buttons) {
                    val next = toggleJolts(current.jolts, button)
                    var inBounds = true
                    for (i in next.indices) {
                        if (next[i] > endJolts[i]) {
                            inBounds = false
                            break
                        }
                    }
                    if (inBounds) {
                        frontier.add(JoltState(next, current.steps + 1))
                    }
                }
            }
        }
        return best[endJolts.toString()]!!
    }

    fun toggleJolts(current: List<Int>, button: List<Int>): List<Int> {
        val list = mutableListOf<Int>()
        list.addAll(current)
        for (toggle in button) {
            list[toggle] = list[toggle] + 1
        }
        return list
    }
}

data class LightState(val lights: String, val steps: Int)
data class JoltState(val jolts: List<Int>, val steps: Int) {
    val key = jolts.toString()
}