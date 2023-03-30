package buri.aoc.y21.d13

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Bounds2D
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
        assertRun("17", 1)
        assertRun("765", 0, true)
    }

    @Test
    fun runPart2() {
        // RZKZLPGH
        assertRun("■■■  ■■■■ ■  ■ ■■■■ ■    ■■■   ■■  ■  ■ ", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val points = mutableSetOf<Point2D<Int>>()
        val spaceIndex = input.indexOf("")
        for (line in input.subList(0, spaceIndex)) {
            val numbers = line.extractInts()
            points.add(Point2D(numbers[0], numbers[1]))
        }
        val bounds = Bounds2D(points)
        var grid = Grid(bounds.x.last + 1, bounds.y.last + 1, ' ')
        for (point in points) {
            grid[point] = '■'
        }

        for (line in input.subList(spaceIndex + 1, input.size)) {
            val command = line.split(" ")[2].split("=")
            val coord = command[1].toInt()
            val newWidth = when (command[0]) {
                "y" -> grid.width
                else -> grid.width - coord - 1
            }
            val newHeight = when (command[0]) {
                "y" -> grid.height - coord - 1
                else -> grid.height
            }
            val newStart = when (command[0]) {
                "y" -> Point2D(0, coord + 1)
                else -> Point2D(coord + 1, 0)
            }
            val orientation = when (command[0]) {
                "y" -> Orientation.MIRROR_V
                else -> Orientation.MIRROR_H
            }
            val fold = grid.getSubGrid(newStart, newWidth, newHeight).copy(orientation)
            for (point in fold.filter { it == '■' }) {
                grid[point] = '■'
            }
            grid = grid.getSubGrid(Point2D(0, 0), newWidth, newHeight)
            if (part.isOne()) {
                return grid.count { it == '■' }.toString()
            }
        }
        return grid.toString()
    }
}