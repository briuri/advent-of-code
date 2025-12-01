package buri.aoc.y25.d02

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
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
        assertRun(0, 1)
        assertRun(0, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(0, 1)
        assertRun(0, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {

        for (line in input) {

        }
        return -1
    }
}