package buri.aoc.y20.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import buri.aoc.common.position.*
import buri.aoc.common.position.Direction.*
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(20899048083289, 1)
        assertRun(28057939502729, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(273, 1)
        assertRun(2489, 0, true)
    }

    private val monsterSize = 15
    private val monsterWidth = 20
    private val monsterHeight = 3

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        // Load the tiles in their initial orientation.
        val remainingTiles = mutableListOf<Tile>()
        var i = 0
        while (i in input.indices) {
            if (input[i].startsWith("Tile ")) {
                val id = input[i].extractLongs()[0]
                val size = input[i + 1].length
                val gridInput = input.subList(i + 1, i + 1 + size)
                remainingTiles.add(Tile(id, Grid.fromCharInput(gridInput)))
                i += size + 2
            }
        }

        // Start building the image, using the first tile in its initial orientation.
        val image = mutableMapOf<Point2D<Int>, Tile>()
        val origin = Point2D(0, 0)
        image[origin] = remainingTiles.removeFirst()

        // Initialize frontier to be the 4 edges of the first tile.
        val frontier = mutableListOf<Pair<Point2D<Int>, Direction>>()
        for (direction in Direction.values()) {
            frontier.add(Pair(origin, direction))
        }

        // Try all pieces in all orientations for each edge.
        while (frontier.isNotEmpty() && remainingTiles.isNotEmpty()) {
            val (point, direction) = frontier.removeFirst()
            val tile = image[point]!!
            val neighbor = when (direction) {
                NORTH -> point.copy(y = point.y - 1)
                SOUTH -> point.copy(y = point.y + 1)
                WEST -> point.copy(x = point.x - 1)
                EAST -> point.copy(x = point.x + 1)
            }
            // Skip any edges that have already been matched.
            if (image[neighbor] != null) {
                continue
            }

            // Try each permutation of each remaining tile.
            for (testTile in remainingTiles) {
                for (permutation in testTile.permutations) {
                    if (tile.connects(direction, permutation)) {
                        // Save the correct orientation and store in the image.
                        testTile.grid = permutation
                        image[neighbor] = testTile
                        // Add new tile's edges to the frontier.
                        for (neighborDirection in Direction.values()) {
                            frontier.add(Pair(neighbor, neighborDirection))
                        }
                        break
                    }
                }
            }
            remainingTiles.removeIf { it in image.values }
        }

        val bounds = Bounds2D(image.keys)
        // Multiply corner IDs in part one.
        if (part.isOne()) {
            val ulId = image[Point2D(bounds.x.first, bounds.y.first)]!!.id
            val urId = image[Point2D(bounds.x.last, bounds.y.first)]!!.id
            val llId = image[Point2D(bounds.x.first, bounds.y.last)]!!.id
            val lrId = image[Point2D(bounds.x.last, bounds.y.last)]!!.id
            return ulId * urId * llId * lrId
        }

        // Remove borders
        val subGridStart = Point2D(1, 1)
        for (tile in image.values) {
            val fullGrid = tile.grid
            tile.grid = fullGrid.getSubGrid(subGridStart, fullGrid.width - 2, fullGrid.height - 2)
        }

        // Only 1 permutation will have monsters.
        val stitchedTile = stitchTiles(image, bounds)
        for (permutation in stitchedTile.permutations) {
            val monsterCount = countMonsters(permutation)
            if (monsterCount > 0) {
                return permutation.count { it == '#' } - monsterCount * monsterSize
            }
        }
        return -1
    }

    /**
     * Stitches final permutations into one big tile.
     */
    private fun stitchTiles(image: Map<Point2D<Int>, Tile>, bounds: Bounds2D): Tile {
        val tileSize = image.values.first().grid.width
        val tilesPerEdge = bounds.x.last - bounds.x.first + 1
        val stitchedGrid = Grid(tileSize * tilesPerEdge, tileSize * tilesPerEdge, '?')
        for (y in 0 until tilesPerEdge) {
            for (x in 0 until tilesPerEdge) {
                val tile = image[Point2D(bounds.x.first + x, bounds.y.first + y)]!!
                for (tileY in 0 until tileSize) {
                    for (tileX in 0 until tileSize) {
                        val newX = x * tileSize + tileX
                        val newY = y * tileSize + tileY
                        stitchedGrid[newX, newY] = tile.grid[tileX, tileY]
                    }
                }
            }
        }
        return Tile(0L, stitchedGrid)
    }

    /**
     * Returns the number of monsters in the grid.
     */
    private fun countMonsters(grid: Grid<Char>): Int {
        var monsterCount = 0
        for (x in 0 until grid.width - monsterWidth) {
            for (y in 0 until grid.height - monsterHeight) {
                val found = grid[x, y + 1] == '#' &&
                        grid[x + 1, y + 2] == '#' &&
                        grid[x + 4, y + 2] == '#' &&
                        grid[x + 5, y + 1] == '#' &&
                        grid[x + 6, y + 1] == '#' &&
                        grid[x + 7, y + 2] == '#' &&
                        grid[x + 10, y + 2] == '#' &&
                        grid[x + 11, y + 1] == '#' &&
                        grid[x + 12, y + 1] == '#' &&
                        grid[x + 13, y + 2] == '#' &&
                        grid[x + 16, y + 2] == '#' &&
                        grid[x + 17, y + 1] == '#' &&
                        grid[x + 18, y] == '#' &&
                        grid[x + 18, y + 1] == '#' &&
                        grid[x + 19, y + 1] == '#'
                if (found) {
                    monsterCount++
                }
            }
        }
        return monsterCount
    }
}

class Tile(val id: Long, var grid: Grid<Char>) {
    val permutations = mutableListOf<Grid<Char>>()

    init {
        permutations.add(grid)
        permutations.add(grid.copy(Orientation.CLOCKWISE_90))
        permutations.add(grid.copy(Orientation.CLOCKWISE_180))
        permutations.add(grid.copy(Orientation.CLOCKWISE_270))
        val copy = grid.copy(Orientation.MIRROR_H)
        permutations.add(copy)
        permutations.add(copy.copy(Orientation.CLOCKWISE_90))
        permutations.add(copy.copy(Orientation.CLOCKWISE_180))
        permutations.add(copy.copy(Orientation.CLOCKWISE_270))
    }

    /**
     * Returns true if the two edges align.
     */
    fun connects(direction: Direction, permutation: Grid<Char>): Boolean {
        var connects = true
        when (direction) {
            NORTH -> {
                for (x in grid.xRange) {
                    connects = connects && (grid[x, 0] == permutation[x, permutation.height - 1])
                }
            }
            SOUTH -> {
                for (x in grid.xRange) {
                    connects = connects && (grid[x, grid.height - 1] == permutation[x, 0])
                }
            }
            WEST -> {
                for (y in grid.yRange) {
                    connects = connects && (grid[0, y] == permutation[grid.width - 1, y])
                }
            }
            EAST -> {
                for (y in grid.yRange) {
                    connects = connects && (grid[grid.width - 1, y] == permutation[0, y])
                }
            }
        }
        return connects
    }

    override fun toString(): String = "$id:\n$grid"
}