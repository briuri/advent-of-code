package buri.aoc.y23.d13

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun(405, 1)
        assertRun(30802, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(400, 1)
        assertRun(37876, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grids = mutableListOf<Grid<Char>>()
        var remainingInput = input.toMutableList()
        while (remainingInput.isNotEmpty()) {
            var index = remainingInput.indexOf("")
            if (index == -1) {
                index = remainingInput.size
            }
            grids.add(Grid.fromCharInput(remainingInput.subList(0, index)))
            remainingInput = remainingInput.drop(index + 1).toMutableList()
        }

        var totalCols = 0
        var totalRows = 0
        for (grid in grids) {
            // Do the horizontal checking with a rotated grid so the same algorithm applies.
            var cols = grid.findMirrorLine()
            var rows = grid.copy(Orientation.CLOCKWISE_270).findMirrorLine()

            if (part.isTwo()) {
                val smudge = grid.getSmudge()
                val cleanGrid = grid.copy()
                cleanGrid[smudge] = if (cleanGrid[smudge] == '.') '#' else '.'

                // Find the mirror line again, ignoring the part 1 lines.
                cols = cleanGrid.findMirrorLine(cols)
                rows = cleanGrid.copy(Orientation.CLOCKWISE_270).findMirrorLine(rows)
            }
            totalCols += cols
            totalRows += rows
        }
        return (100 * totalRows + totalCols)
    }

    /**
     * From left to right, create test mirror lines that divide the grid into 2 chunks of equal width (left/right).
     * By flipping the right one horizontally, we can do a simple point-to-point comparison with less bug-prone
     * index transformation. This same algorithm works for the column check if we rotate the grid 90 degress CCW.
     *
     * Returns the number of columns to the left of the mirror line or 0 if nothing works.
     */
    private fun Grid<Char>.findMirrorLine(skipLine: Int = -1): Int {
        for (mirrorLine in 0 until xRange.last) {
            if (mirrorLine + 1 == skipLine) {
                continue
            }

            val (left, right) = splitVertical(mirrorLine)
            var isSame = true
            for (y in left.yRange) {
                for (x in left.xRange) {
                    isSame = isSame && (left[x, y] == right[x, y])
                }
            }
            if (isSame) {
                return (mirrorLine + 1)
            }
        }
        return 0
    }

    /**
     * Splits a grid vertically along a test mirror line.
     */
    private fun Grid<Char>.splitVertical(mirrorLeft: Int): Pair<Grid<Char>, Grid<Char>> {
        val leftFullWidth = mirrorLeft + 1
        val chunkWidth = leftFullWidth.coerceAtMost(xRange.last - mirrorLeft)
        val left = getSubGrid(Point2D(leftFullWidth - chunkWidth, 0), chunkWidth, height)
        val right = getSubGrid(Point2D(leftFullWidth, 0), chunkWidth, height).copy(Orientation.MIRROR_H)
        return Pair(left, right)
    }

    /**
     * Finds the smudge on the grid that allows a new mirror line to appear, based on which tests have exactly 1
     * different value.
     */
    private fun Grid<Char>.getSmudge(): Point2D<Int> {
        // Check based on column mirroring first.
        for (m in 0 until xRange.last) {
            val (left, right) = splitVertical(m)
            val differences = getDifferences(left, right)
            if (differences.size == 1) {
                val chunkPoint = differences.first()
                // Shift point from a chunk back to the original grid.
                return (Point2D(chunkPoint.x + m + 1 - left.width, chunkPoint.y))
            }
        }
        // Then check based on row mirroring.
        // I could refactor this to use a rotated 90 CCW grid (like the mirror line algorithm) but I didn't
        // want to deal with the math of rotating the smudge point back to its position in the original grid.
        for (m in 0 until yRange.last) {
            val topFullHeight = m + 1
            val chunkHeight = topFullHeight.coerceAtMost(yRange.last - m)
            val top = getSubGrid(Point2D(0, topFullHeight - chunkHeight), width, chunkHeight)
            val bottom = getSubGrid(Point2D(0, topFullHeight), width, chunkHeight).copy(Orientation.MIRROR_V)

            val differences = getDifferences(top, bottom)
            if (differences.size == 1) {
                val chunkPoint = differences.first()
                // Shift point from a chunk back to the original grid.
                return (Point2D(chunkPoint.x, chunkPoint.y + topFullHeight - chunkHeight))
            }
        }
        throw Exception("Could not find smudge!")
    }

    /**
     * Compares two grids (assumed to be equal size) and returns any points that are different.
     */
    private fun getDifferences(grid1: Grid<Char>, grid2: Grid<Char>): Set<Point2D<Int>> {
        val differences = mutableSetOf<Point2D<Int>>()
        for (y in grid1.yRange) {
            for (x in grid1.xRange) {
                if (grid1[x, y] != grid2[x, y]) {
                    differences.add(Point2D(x, y))
                }
            }
        }
        return differences
    }
}