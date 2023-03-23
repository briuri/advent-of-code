package buri.aoc.y20.d13

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
        assertRun(295, 1)
        assertRun(3035, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1068781, 1)
        assertRun(725169163285238, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val earliest = input[0].toLong()
        val buses = mutableSetOf<Bus>()
        for ((index, value) in input[1].split(",").withIndex()) {
            if (value != "x") {
                buses.add(Bus(value.toLong(), index.toLong()))
            }
        }

        if (part.isOne()) {
            val earliestBus = buses.minBy { it.getNextTimeAfter(earliest) }
            val waitTime = earliestBus.getNextTimeAfter(earliest) - earliest
            return (earliestBus.id * waitTime)
        }

        var increment = 1L
        var timestamp = 0L
        for (bus in buses) {
            while ((timestamp + bus.position) % bus.id != 0L) {
                timestamp += increment
            }
            increment *= bus.id
        }
        return timestamp
    }
}

class Bus(val id: Long, val position: Long) {

    /**
     * Returns the next time a bus leaves after a given time
     */
    fun getNextTimeAfter(time: Long): Long = ((time / id) + 1) * id

    override fun toString(): String = "$id,$position"
}