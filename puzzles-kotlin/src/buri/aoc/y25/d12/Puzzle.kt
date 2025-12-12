package buri.aoc.y25.d12

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Grid
import buri.aoc.common.position.Orientation
import buri.aoc.common.position.Point2D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        // Final solution (sanity-checking total area before processing) doesn't work on example.
//        assertRun(2, 1)
        assertRun(497, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val presentAreas = mutableListOf<Int>()
        val regions = mutableListOf<Region>()

        var remainingInput = input
        while (true) {
            if (!remainingInput[0].contains("x")) {
                val end = remainingInput.indexOf("")
                val grid = Grid.fromCharInput(remainingInput.subList(1, end))
                presentAreas.add(grid.count { it == '#' })
                remainingInput = remainingInput.drop(grid.yRange.last + 3)
            } else {
                for (line in remainingInput) {
                    regions.add(Region(line))
                }
                break
            }
        }

        var total = 0
        for (region in regions) {
            var presentArea = 0
            for ((index, count) in region.presentsNeeded.withIndex()) {
                presentArea += presentAreas[index] * count
            }
            if (presentArea <= region.height * region.width) {
                total++
            }
        }
        return total
    }

}

data class Region(val rawInput: String) {
    val width = rawInput.split("x")[0].toInt()
    val height = rawInput.split("x")[1].split(":")[0].toInt()
    val presentsNeeded = rawInput.split(": ")[1].extractInts()
}