package buri.aoc.y18.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Grid
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(57, 1)
        assertRun(39367, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(29, 1)
        assertRun(33061, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        // Calculate grid bounds
        var maxX = 0
        var minY = Int.MAX_VALUE
        var maxY = 0
        for (line in input) {
            val numbers = line.extractInts()
            if (line.startsWith("x")) {
                maxX = maxX.coerceAtLeast(numbers[0])
                minY = minY.coerceAtMost(numbers[1])
                maxY = maxY.coerceAtLeast(numbers[2])
            } else {
                minY = minY.coerceAtMost(numbers[0])
                maxY = maxY.coerceAtLeast(numbers[0])
                maxX = maxX.coerceAtLeast(numbers[2])
            }
        }
        val grid = SandGrid(maxX + 1, maxY + 1)
        // Add walls
        for (line in input) {
            val numbers = line.extractInts()
            val xBounds = if (line.startsWith("x")) {
                numbers[0]..numbers[0]
            } else {
                numbers[1]..numbers[2]
            }
            val yBounds = if (line.startsWith("y")) {
                numbers[0]..numbers[0]
            } else {
                numbers[1]..numbers[2]
            }
            for (y in yBounds) {
                for (x in xBounds) {
                    grid[x, y] = '#'
                }
            }
        }

        var previousWaterCount = 0
        while (true) {
            grid.flowDown(mutableSetOf(), Pair(500, 0))
            // Stop flowing when no new standing water appears.
            if (previousWaterCount == grid.waterCount) {
                break
            }
            previousWaterCount = grid.waterCount
        }
        // Only count squares in between minY and maxY.
        val countGrid = grid.getSubGrid(Pair(0, minY), grid.width, grid.height - minY)
        return if (part.isOne()) {
            previousWaterCount + countGrid.count('|')
        } else {
            previousWaterCount
        }

    }
}

class SandGrid(width: Int, height: Int) : Grid<Char>(width, height, '.') {
    var waterCount = 0

    /**
     * Returns the first point below some other point that is not water or clay. Marks interim points as reachable.
     */
    fun flowDown(visitedFlowDowns: MutableSet<Pair<Int, Int>>, top: Pair<Int, Int>) {
        visitedFlowDowns.add(top)
        val bottomY = getFirstOpenY(top)
        if (bottomY < height - 1) {
            val flowDowns = fillRow(top.first, bottomY).filter { it !in visitedFlowDowns }
            for (flowDown in flowDowns) {
                flowDown(visitedFlowDowns, flowDown)
            }
        }
    }

    /**
     * Returns the first point below some other point that is not water or clay. Marks interim points as reachable.
     */
    private fun getFirstOpenY(top: Pair<Int, Int>): Int {
        val x = top.first
        var y = top.second + 1
        while (isInBounds(x, y) && get(x, y) in ".|") {
            set(x, y, '|')
            y++
        }
        return (y - 1)
    }

    /**
     * If the point's row is enclosed, fill with water and return an empty list. Otherwise return nearby flow down points.
     */
    private fun fillRow(bottomX: Int, bottomY: Int): List<Pair<Int, Int>> {
        // Check for floors
        val floorY = bottomY + 1
        var leftFloorX = bottomX
        while (get(leftFloorX - 1, floorY) in "#~") {
            leftFloorX--
        }
        var rightFloorX = bottomX
        while (get(rightFloorX + 1, floorY) in "#~") {
            rightFloorX++
        }

        // Check for enclosing walls.
        var hasLeftWall = true
        var leftWallX = bottomX
        while (get(leftWallX, bottomY) != '#') {
            leftWallX--
            if (leftWallX < leftFloorX) {
                hasLeftWall = false
                break
            }
        }
        var hasRightWall = true
        var rightWallX = bottomX
        while (get(rightWallX, bottomY) != '#') {
            rightWallX++
            if (rightWallX > rightFloorX) {
                hasRightWall = false
                break
            }
        }

        val flowDowns = mutableListOf<Pair<Int, Int>>()
        if (hasLeftWall && hasRightWall) {
            for (x in (leftWallX + 1) until rightWallX) {
                this[x, bottomY] = '~'
            }
            waterCount += (rightWallX - (leftWallX + 1))
        }
        // Mark this row as reachable until we hit new flow down points.
        else {
            val minReachable = if (hasLeftWall) (leftWallX + 1) else (leftFloorX - 1)
            val maxReachable = if (hasRightWall) (rightWallX - 1) else (rightFloorX + 1)
            for (x in minReachable..maxReachable) {
                this[x, bottomY] = '|'
            }
            if (!hasLeftWall) {
                flowDowns.add(Pair(leftFloorX - 1, bottomY))
            }
            if (!hasRightWall) {
                flowDowns.add(Pair(rightFloorX + 1, bottomY))
            }
        }
        return flowDowns
    }
}