package buri.aoc.y22.d16

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test
import kotlin.math.pow

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(1651, 1)
        assertRun(1947, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1707, 1)
        assertRun(2556, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val start = "AA"
        val valves = mutableMapOf<String, Valve>()
        val tunnels = mutableMapOf<String, MutableList<String>>()
        for (line in input) {
            val name = line.substring(6, 8)
            val flow = line.split("rate=")[1].split(";")[0].toInt()
            if (flow > 0) {
                valves[name] = Valve(name, flow, 2.toDouble().pow(valves.size).toLong())
            }

            tunnels[name] = mutableListOf()
            val separator = if (line.contains("tunnels")) " valves " else " valve "
            val tokens = line.split(separator)[1].split(", ")
            for (token in tokens) {
                tunnels[name]!!.add(token)
            }
        }

        val minutes = if (part.isOne()) 30 else 26
        val bestPressures = mutableMapOf<String, Long>()
        var frontier = mutableSetOf<State>()
        frontier.add(State(start, 0L, 0L))
        for (minute in 1..minutes) {
            val nextFrontier = mutableSetOf<State>()
            for (state in frontier) {
                val bestKey = state.location + state.opened
                if (state.pressure >= bestPressures.getOrDefault(bestKey, 0L)) {
                    bestPressures[bestKey] = state.pressure
                } else {
                    continue
                }

                if (valves[state.location] != null) {
                    // Try opening a valve.
                    val valve = valves[state.location]!!
                    if (valve.id and state.opened == 0L) {
                        val remainingFlow = valve.flow * (minutes - minute)
                        val nextOpened = valve.id or state.opened
                        nextFrontier.add(State(state.location, nextOpened, state.pressure + remainingFlow))
                    }
                }
                // Try moving.
                for (tunnel in tunnels[state.location]!!) {
                    nextFrontier.add(State(tunnel, state.opened, state.pressure))
                }
            }
            frontier = nextFrontier
        }

        if (part.isOne()) {
            val sortedFrontier = frontier.toMutableList().sortedByDescending { it.pressure }
            return sortedFrontier[0].pressure
        }

        // Combine two complementary paths.
        val groupedPressures = mutableMapOf<Long, Long>()
        for (state in frontier) {
            val bestPressure = groupedPressures.getOrDefault(state.opened, 0L)
            if (state.pressure > bestPressure) {
                groupedPressures[state.opened] = state.pressure
            }
        }

        var max = 0L
        for (iOpened in groupedPressures.keys) {
            for (eOpened in groupedPressures.keys.filter { it and iOpened == 0L }) {
                val combinedPressure = groupedPressures[iOpened]!! + groupedPressures[eOpened]!!
                max = max.coerceAtLeast(combinedPressure)
            }
        }
        return max
    }
}

data class Valve(val name: String, val flow: Int, val id: Long)
data class State(val location: String, val opened: Long, val pressure: Long)