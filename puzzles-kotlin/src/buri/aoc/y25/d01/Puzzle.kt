package buri.aoc.y25.d01

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
import buri.aoc.common.extractInts
import buri.aoc.common.position.Direction
import buri.aoc.common.position.Grid
import buri.aoc.common.position.MutablePosition
import buri.aoc.common.position.Point2D
import buri.aoc.common.position.Point3D
import buri.aoc.common.position.Point4D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(3, 1)
        assertRun(1031, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(6, 1)
        assertRun(5831, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val dialSize = 100
        var dial = 50
        var count = 0
        for (line in input) {
            val dir = when (line.first()) {
                'L' -> -1
                else -> 1
            }
            val amount = line.drop(1).toInt()
            repeat(amount) {
                dial = (dial + dir) % dialSize
                if (dial < 0) {
                    dial += dialSize
                }
                if (part.isTwo() && dial == 0) {
                    count++
                }
            }
            if (part.isOne() && dial == 0) {
                count++
            }
        }
        return count
    }
}