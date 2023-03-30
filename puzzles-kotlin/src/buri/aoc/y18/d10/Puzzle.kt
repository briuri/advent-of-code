package buri.aoc.y18.d10

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Bounds2D
import buri.aoc.common.position.Grid
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
        assertRun("■   ■  ■■■", 1)
        // FNRGPBHR
        assertRun("■■■■■■  ■    ■  ■■■■■    ■■■■   ■■■■■", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("3", 1)
        assertRun("10511", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val stars = mutableSetOf<Star>()
        for (line in input) {
            stars.add(Star(line.extractInts()))
        }

        var lastArea = Bounds2D(stars.getPositions()).area
        var ticks = 0
        while (true) {
            stars.forEach { it.tick() }
            val nextArea = Bounds2D(stars.getPositions()).area
            // When area starts growing again, go 1 step back.
            if (nextArea > lastArea) {
                stars.forEach { it.tick(true) }
                break
            }
            lastArea = nextArea
            ticks++
        }
        return if (part.isOne()) toGrid(stars).toString() else ticks.toString()
    }

    /**
     * Converts the stars into a grid.
     */
    private fun toGrid(stars: MutableSet<Star>): Grid<Char> {
        // Offset stars for grid rendering.
        val bounds = Bounds2D(stars.getPositions())
        stars.forEach {
            it.position = it.position.copy(
                x = it.position.x - bounds.x.first,
                y = it.position.y - bounds.y.first
            )
        }
        val width = stars.maxOf { it.position.x } + 1
        val height = stars.maxOf { it.position.y } + 1
        val grid = Grid(width, height, ' ')
        for (y in grid.yRange) {
            for (x in grid.xRange) {
                grid[x, y] = if (Point2D(x, y) in stars.getPositions()) '■' else ' '
            }
        }
        return grid
    }

    /**
     * Converts a set of stars into a set of positions.
     */
    private fun MutableSet<Star>.getPositions(): Set<Point2D<Int>> = this.map { it.position }.toSet()
}

class Star(numbers: List<Int>) {
    var position: Point2D<Int>
    private var velocity: Point2D<Int>

    init {
        position = Point2D(numbers[0], numbers[1])
        velocity = Point2D(numbers[2], numbers[3])
    }

    /**
     * Updates star position.
     */
    fun tick(inReverse: Boolean = false) {
        position = if (inReverse) {
            position.copy(
                x = position.x - velocity.x,
                y = position.y - velocity.y,
            )
        } else {
            position.copy(
                x = position.x + velocity.x,
                y = position.y + velocity.y,
            )
        }
    }

    override fun toString(): String = position.toString()
}