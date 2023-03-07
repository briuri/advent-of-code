package buri.aoc.y15.d06

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Grid
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(543903, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(14687245, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val lights = Grid(1000, 1000, 0)
        for (line in input) {
            val numbers = line.extractInts()
            val onOff = line.split(" ")[1]
            val xRange = numbers[0]..numbers[2]
            val yRange = numbers[1]..numbers[3]

            for (x in xRange) {
                for (y in yRange) {
                    val value = lights[x, y]
                    if (part.isOne()) {
                        when (onOff) {
                            "on" -> lights[x, y] = 1
                            "off" -> lights[x, y] = 0
                            else -> {
                                val newValue = if (value == 0) 1 else 0
                                lights[x, y] = newValue
                            }
                        }
                    } else {
                        when (onOff) {
                            "on" -> lights[x, y] = value + 1
                            "off" -> lights[x, y] = (value - 1).coerceAtLeast(0)
                            else -> lights[x, y] = value + 2
                        }
                    }
                }
            }
        }
        return lights.sum()
    }
}