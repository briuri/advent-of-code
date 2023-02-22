package buri.aoc.y18.d04

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
        assertRun(240, 1)
        assertRun(87681, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(4455, 1)
        assertRun(136461, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val sortedInput = input.sorted()
        val records = mutableMapOf<Int, Record>()

        var i = 0
        while (i in sortedInput.indices) {
            if (sortedInput[i].contains("Guard #")) {
                val id = sortedInput[i].drop(15).extractInts()[1]
                records.putIfAbsent(id, Record(id))
                i++
                while (i in sortedInput.indices && !sortedInput[i].contains("Guard #")) {
                    val start = sortedInput[i].drop(15).extractInts()[0]
                    val end = sortedInput[i + 1].drop(15).extractInts()[0]
                    records[id]!!.addSleep(start, end)
                    i += 2
                }
            }
        }
        if (part.isOne()) {
            val record = records.values.maxByOrNull { it.totalSleep }!!
            return record.id * record.sleepiestMinute.first
        }

        val maxTimes = mutableMapOf<Int, Record>()
        for (record in records.values) {
            maxTimes[record.sleepiestMinute.second] = record
        }
        val record = maxTimes.toList().maxByOrNull { it.first }!!.second
        return record.id * record.sleepiestMinute.first
    }
}

data class Record(val id: Int) {
    private val sleepMinutes = mutableMapOf<Int, Int>()
    val totalSleep: Int
        get() = sleepMinutes.values.sum()
    val sleepiestMinute: Pair<Int, Int>
        get() = sleepMinutes.toList().maxByOrNull { it.second }!!

    init {
        repeat(60) {
            sleepMinutes[it] = 0
        }
    }

    /**
     * Adds a range of sleeping (awake minute is exclusive).
     */
    fun addSleep(asleep: Int, awake: Int) {
        for (i in asleep until awake) {
            sleepMinutes[i] = sleepMinutes[i]!! + 1
        }
    }
}