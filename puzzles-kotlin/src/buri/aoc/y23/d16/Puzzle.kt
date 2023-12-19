package buri.aoc.y23.d16

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction
import buri.aoc.common.position.Grid
import buri.aoc.common.position.MutablePosition
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
        assertRun(46, 1)
        assertRun(7046, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(51, 1)
        assertRun(7313, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        val startPositions = mutableListOf<MutablePosition>()
        if (part.isOne()) {
            startPositions.add(MutablePosition(Point2D(0, 0), Direction.EAST))
        } else {
            for (y in grid.yRange) {
                startPositions.add(MutablePosition(Point2D(0, y), Direction.EAST))
                startPositions.add(MutablePosition(Point2D(grid.xRange.last, y), Direction.WEST))
            }
            for (x in grid.xRange) {
                startPositions.add(MutablePosition(Point2D(x, 0), Direction.SOUTH))
                startPositions.add(MutablePosition(Point2D(x, grid.yRange.last), Direction.NORTH))
            }
        }
        return startPositions.maxOf { grid.shootBeam(it) }
    }

    /**
     * Fires a beam from a starting position and counts the energized squares.
     */
    private fun Grid<Char>.shootBeam(start: MutablePosition): Int {
        val beams = mutableListOf(start.copy())
        val energized = mutableSetOf<Point2D<Int>>()
        val visited = mutableSetOf<MutablePosition>()

        while (beams.isNotEmpty()) {
            val beam = beams.removeFirst()
            while (beam !in visited && isInBounds(beam.coords)) {
                visited.add(beam.copy())
                energized.add(beam.coords)
                val tile = get(beam.coords)
                if ((tile == '|' && beam.facing in listOf(Direction.EAST, Direction.WEST))
                    || (tile == '-' && beam.facing in listOf(Direction.NORTH, Direction.SOUTH))
                ) {
                    val newBeam = beam.copy()
                    beams.add(newBeam)
                    newBeam.turnLeft()
                    newBeam.move()
                    beam.turnRight()
                }
                if (tile == '/') {
                    when (beam.facing) {
                        Direction.NORTH, Direction.SOUTH -> beam.turnRight()
                        else -> beam.turnLeft()
                    }
                }
                if (tile == '\\') {
                    when (beam.facing) {
                        Direction.NORTH, Direction.SOUTH -> beam.turnLeft()
                        else -> beam.turnRight()
                    }
                }
                beam.move()
            }
        }
        return energized.size
    }
}