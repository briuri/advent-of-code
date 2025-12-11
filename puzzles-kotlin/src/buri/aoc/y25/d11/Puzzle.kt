package buri.aoc.y25.d11

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
        assertRun(5, 1)
        assertRun(571, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2, 2)
//        assertRun(0, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val devices = mutableListOf<Device>()
        for (line in input) {
            val id = line.split(": ")[0]
            val outputs = line.split(": ")[1].split(" ")
            devices.add(Device(id, outputs))
        }

        if (part.isOne()) {
            return devices.getCount("you", "out")
        }

        // Part Two

        var midCount = devices.getCount("dac", "fft")
        val midA = if (midCount == 0) {
            "fft"
        } else {
            "dac"
        }
        val midB = if (midCount == 0) {
            "dac"
        } else {
            "fft"
        }
        if (midCount == 0) {
            midCount = devices.getCount("fft", "dac")
        }
        val startCount = devices.getCount("svr", midA)
        val endCount = devices.getCount(midB, "out")
        return startCount * midCount * endCount
    }

    fun List<Device>.getCount(start: String, end: String): Int {
        val frontier = ArrayDeque<String>()
        frontier.add(start)

        var count = 0
        while (frontier.isNotEmpty()) {
            val current = frontier.removeFirst()
            if (current == end) {
                count++
            } else {
                val device = this.firstOrNull { it.id == current }
                if (device == null) {
                    break
                }
                for (out in device.outputs) {
                    if (out !in current) {
                        frontier.add(out)
                    }
                }
            }
        }
        return count
    }

}

data class Device(val id: String, val outputs: List<String>)