package buri.aoc.y20.d24

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Point3D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(10, 1)
        assertRun(450, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2208, 1)
        assertRun(4059, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var tiles = mutableMapOf<Point3D<Int>, Int>()
        for (line in input) {
            var x = 0
            var y = 0
            var z = 0
            var i = 0
            while (i in line.indices) {
                var command: String?
                if (line[i] in "ew") {
                    command = line[i].toString()
                    i += 1
                } else {
                    command = line.substring(i, i + 2)
                    i += 2
                }
                when (command) {
                    // https://www.redblobgames.com/grids/hexagons/#coordinates-cube
                    "e" -> {
                        y -= 1
                        z += 1
                    }
                    "se" -> {
                        x += 1
                        y -= 1
                    }
                    "sw" -> {
                        x += 1
                        z -= 1
                    }
                    "w" -> {
                        y += 1
                        z -= 1
                    }
                    "nw" -> {
                        x -= 1
                        y += 1
                    }
                    "ne" -> {
                        x -= 1
                        z += 1
                    }
                }
            }
            val point = Point3D(x, y, z)
            if (point in tiles) {
                tiles[point] = 0
            } else {
                tiles[point] = 1
            }
        }

        if (part.isTwo()) {
            repeat(100) {
                val testTiles = mutableSetOf<Point3D<Int>>()
                testTiles.addAll(tiles.keys)
                for (tile in tiles.keys) {
                    testTiles.addAll(getNeighbors(tile))
                }

                val nextTiles = mutableMapOf<Point3D<Int>, Int>()
                for (tile in testTiles) {
                    val neighbors = getNeighbors(tile)
                    val count = neighbors.count { tiles[it] == 1 }
                    var value = tiles[tile] ?: 0
                    if (value == 1 && (count == 0 || count > 2)) {
                        value = 0
                    } else if (value == 0 && count == 2) {
                        value = 1
                    }
                    nextTiles[tile] = value
                }
                tiles = nextTiles
            }
        }
        return tiles.values.sum()
    }

    /**
     * Returns the 6 neighbors of some point.
     */
    private fun getNeighbors(tile: Point3D<Int>): List<Point3D<Int>> {
        val list = mutableListOf<Point3D<Int>>()
        list.add(tile.copy(y = tile.y - 1, z = tile.z + 1))
        list.add(tile.copy(x = tile.x + 1, y = tile.y - 1))
        list.add(tile.copy(x = tile.x + 1, z = tile.z - 1))
        list.add(tile.copy(y = tile.y + 1, z = tile.z - 1))
        list.add(tile.copy(x = tile.x - 1, y = tile.y + 1))
        list.add(tile.copy(x = tile.x - 1, z = tile.z + 1))
        return list
    }
}