package buri.aoc.y23.d11

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun(374, 1)
        assertRun(9329143, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(8410, 1)
        assertRun(710674907809, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        val galaxies = grid.filter { it == '#' }.toMutableList()
        val emptyRows = grid.xRange.filter { it !in galaxies.map { it2 -> it2.x } }
        val emptyCols = grid.yRange.filter { it !in galaxies.map { it2 -> it2.y } }

        // Adjust each galaxy based on how many empty rows/cols are above or to left.
        val growSize = when {
            part.isOne() -> 2
            part.isTwo() && galaxies.size == 9 -> 100
            else -> 1_000_000
        }
        for (i in galaxies.indices) {
            val g = galaxies[i]
            val dx = emptyRows.count { it < g.x } * (growSize - 1)
            val dy = emptyCols.count { it < g.y } * (growSize - 1)
            galaxies[i] = Point2D(g.x + dx, g.y + dy)
        }

        val mds = mutableMapOf<String, Long>()
        for (g1 in galaxies) {
            for (g2 in galaxies.filter { it != g1 }) {
                val id = "$g1-$g2"
                val id2 = "$g2-$g1"
                if (id !in mds && id2 !in mds) {
                    mds[id] = g1.getManhattanDistance(g2).toLong()
                }
            }
        }
        return mds.values.sum()
    }
}