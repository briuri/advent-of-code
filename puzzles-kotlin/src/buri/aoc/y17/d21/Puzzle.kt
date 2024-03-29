package buri.aoc.y17.d21

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Grid
import buri.aoc.common.position.Orientation.*
import buri.aoc.common.position.Point2D
import org.junit.Test
import kotlin.math.sqrt

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(117, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2026963, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val rules = mutableMapOf<String, String>()
        for (line in input) {
            val tokens = line.split(" => ")
            val grid = toGrid(tokens[0])
            // Expand each rule into every possible permutation
            rules[grid.toPattern()] = tokens[1]
            rules[grid.copy(CLOCKWISE_90).toPattern()] = tokens[1]
            rules[grid.copy(CLOCKWISE_180).toPattern()] = tokens[1]
            rules[grid.copy(CLOCKWISE_270).toPattern()] = tokens[1]
            rules[grid.copy(MIRROR_H).toPattern()] = tokens[1]
            rules[grid.copy(MIRROR_H).copy(CLOCKWISE_90).toPattern()] = tokens[1]
            rules[grid.copy(MIRROR_H).copy(CLOCKWISE_180).toPattern()] = tokens[1]
            rules[grid.copy(MIRROR_H).copy(CLOCKWISE_270).toPattern()] = tokens[1]
        }

        var grid = toGrid(".#./..#/###")
        repeat(if (part.isOne()) 5 else 18) {
            // Convert each starter chunk into a new grid based on the applicable rule.
            val chunks = grid.toChunks().map { rules[it.toPattern()]!! }.map { toGrid(it) }
            val chunksPerSide = sqrt(chunks.size.toDouble()).toInt()
            val newWidth = chunks[0].width * chunksPerSide
            grid = Grid(newWidth, newWidth, ' ')
            for ((index, chunk) in chunks.withIndex()) {
                val xOffset = chunk.width * (index % chunksPerSide)
                val yOffset = chunk.height * (index / chunksPerSide)
                for (y in 0 until chunk.height) {
                    for (x in 0 until chunk.width) {
                        grid[x + xOffset, y + yOffset] = chunk[x, y]
                    }
                }
            }
        }

        return grid.count { it == '#' }
    }

    /**
     * Converts a slash pattern into a grid.
     */
    private fun toGrid(pattern: String): Grid<Char> {
        val input = pattern.split("/")
        val grid = Grid(pattern.indexOf("/"), input.size, ' ')
        for (y in grid.yRange) {
            for (x in grid.xRange) {
                grid[x, y] = input[y][x]
            }
        }
        return grid
    }

    /**
     * Compresses a grid into the slash format.
     */
    private fun Grid<Char>.toPattern(): String {
        return this.toString().replace("\n", "/").dropLast(1)
    }

    /**
     * Chunks a grid into smaller patterns. Assumes square grids.
     */
    private fun Grid<Char>.toChunks(): List<Grid<Char>> {
        assert(this.width == this.height)
        val chunks = mutableListOf<Grid<Char>>()
        val size = if (this.width % 2 == 0) 2 else 3
        val numChunks = this.width / size
        for (yChunk in 0 until numChunks) {
            for (xChunk in 0 until numChunks) {
                val start = Point2D(xChunk * size, yChunk * size)
                chunks.add(this.getSubGrid(start, size, size))
            }
        }
        return chunks
    }
}