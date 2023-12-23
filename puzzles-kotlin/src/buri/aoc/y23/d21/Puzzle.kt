package buri.aoc.y23.d21

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Grid
import buri.aoc.common.position.Point2D
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
        assertRun(16, 1)
        assertRun(3782, 0, true)
    }

    @Test
    fun runPart2() {
        // Example has different characteristics that don't work with this approach.
        assertRun(630661863455116, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val steps = when (input.size) {
            11 -> if (part.isOne()) 6 else 10
            else -> if (part.isOne()) 64 else 26_501_365
        }
        val grid = Grid.fromCharInput(input)
        val maxX = grid.xRange.last
        val maxY = grid.yRange.last
        val start = grid.filter { it == 'S' }.first()
        grid[start] = '.'

        if (part.isOne()) {
            return countPlots(grid, start, steps)
        }

        /*
            Input is a square with no barriers in cardinal directions from S.
            Because the paths grow evenly in all four directions, we end up with a diamond having different squares:

            Full Squares:
            1) _FO_: Full squares, starting with the original center square.
            2) _FE_: Full squares 1 step later (because stepsToEdge is odd).

            Partial Squares at Points:
            3) _PU_: Partial square that started filling in from center-bottom, ends in a point at top.
            4) _PD_: Partial square that started filling in from center-top, ends in a point at bottom.
            5) _PL_: Partial square that started filling in from center-right, ends in a point at left.
            6) _PR_: Partial square that started filling in from center-bottom, ends in a point at right.

            Partial Squares Along Diagonal with Points (majority filled):
            7) PUL>: Partial square that started filling in from down-right, along diagonal from a point.
            8) PDL>: Partial square that started filling in from up-right, along diagonal from a point.
            9) PUR>: Partial square that started filling in from down-left, along diagonal from a point.
            10) PDR>: Partial square that started filling in from up-left, along diagonal from a point.

            Partial Squares Adjacent the Diagonals (minority filled):
            11) PUL<: Partial square that started filling in from down-right, adjacent to a point.
            12) PDL<: Partial square that started filling in from up-right, adjacent to a point.
            13) PUR<: Partial square that started filling in from down-left, adjacent to a point.
            14) PDR<: Partial square that started filling in from up-left, adjacent to a point.
         */

        val fullLength = input.size             // 131 steps to cross a new square in cardinal directions.
        val halfLength = (fullLength - 1) / 2   // 65 steps to get from midpoint to edge. 66 to enter next square.

        // Count active squares in each cardinal direction after 26_501_365 steps (not including original middle one).
        val diamondRadius = ((steps - halfLength) / fullLength).toLong() // (26_501_365 - 65) / 131 = 202_300

        /*
            We can use this to figure out how many of each type of square we'll have.
            Base Case diamondRadius=2
                1 fullOdd, 4 fullEven, 1 of each partialPoint, 1 of each partialDiagonal, 2 of each partialAdjacent

                 PUL< _PU_ PUL<          ./^\.
            PUL< PUL> _FE_ PUR> PUR<     //#\\
            _PL_ _FE_ _FO_ _FE_ _PR_     <###>
            PDL< PDL> _FE_ PDR> PDR<     \\#//
                 PDL< _PD_ PDL<          .\v/.
         */
        val fullOddCount = (diamondRadius - 1).toDouble().pow(2).toLong()
        val fullEvenCount = (diamondRadius).toDouble().pow(2).toLong()
        val partialPointCount = 1L
        val partialDiagonalCount = diamondRadius - 1L
        val partialAdjacentCount = partialDiagonalCount + 1L

        var plots = 0L

        /*
            Running the part 1 algorithm on the original square (without overflow) shows that at step 129,
            the original square is full and alternates between two values (7717, 7693). These values can be used for
            any full square in the pattern (_FO_, _FE_).
         */
        val fullOddSize = countPlots(grid, start, fullLength - 2)
        val fullEvenSize = countPlots(grid, start, fullLength - 1)
        plots += (fullOddCount * fullOddSize) + (fullEvenCount * fullEvenSize)

        /*
            Compute how full the other types of squares will be, based on how many more steps the cardinal path goes
            after this square starts up.
         */

        // For diamond points, we cross the entire length (minus 1 for the starting tile).
        val pointLength = fullLength - 1
        val pointSize = countPlots(grid, Point2D(start.x, maxY), pointLength) + // U
                countPlots(grid, Point2D(start.x, 0), pointLength) +         // D
                countPlots(grid, Point2D(maxX, start.y), pointLength) +         // L
                countPlots(grid, Point2D(0, start.y), pointLength)          // R
        plots += partialPointCount * pointSize

        // Majority-filled edges (along the diagonals) go 1.5 squares before cardinal path reaches end.
        val diagonalLength = halfLength + fullLength - 1
        val diagonalSize = countPlots(grid, Point2D(maxX, maxY), diagonalLength) + // UL
                countPlots(grid, Point2D(maxX, 0), diagonalLength) +            // DL
                countPlots(grid, Point2D(0, maxY), diagonalLength) +            // UR
                countPlots(grid, Point2D(0, 0), diagonalLength)             // DR
        plots += partialDiagonalCount * diagonalSize

        // Minority-filled edges (adjacent to diagonals) go 0.5 squares before cardinal path reaches end.
        val adjacentLength = halfLength - 1
        val adjacentSize = countPlots(grid, Point2D(maxX, maxY), adjacentLength) + // UL
                countPlots(grid, Point2D(maxX, 0), adjacentLength) +            // DL
                countPlots(grid, Point2D(0, maxY), adjacentLength) +            // UR
                countPlots(grid, Point2D(0, 0), adjacentLength)             // DR
        plots += partialAdjacentCount * adjacentSize
        return plots
    }

    /**
     * Counts the number of garden plots reached in the original grid without going out of bounds.
     */
    private fun countPlots(grid: Grid<Char>, start: Point2D<Int>, steps: Int): Int {
        val frontier = ArrayDeque<Point2D<Int>>()
        frontier.add(start)
        repeat(steps) {
            val next = mutableSetOf<Point2D<Int>>()
            for (point in frontier) {
                next.addAll(grid.getNeighbors(point).filter { grid[it] == '.' })
            }
            frontier.clear()
            frontier.addAll(next)
        }
        return frontier.size
    }
}
