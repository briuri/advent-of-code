package buri.aoc.y22.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun(3068, 1)
        assertRun(3102, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1514285714288, 1)
        assertRun(1539823008825, 0, true)
    }

    // Don't save the whole gameboard in part 2 snapshots.
    private val snapshotHeight = 375

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val factory = Factory(input[0])

        var rocksFallen = 0L
        var currentHeight = 0L
        val maxRocks = if (part.isOne()) 2022L else 1_000_000_000_000L
        val fallenRocks = mutableSetOf<Point2D<Long>>()

        // For Part 2
        val states = mutableMapOf<String, Pair<Long, Long>>()
        var simulationHeight = 0L

        while (rocksFallen < maxRocks) {
            var rock = factory.nextRock(currentHeight + 4)
            while (!fallenRocks.containsAll(rock)) {
                rock = push(fallenRocks, factory.nextJet(), rock)
                rock = fall(fallenRocks, rock)
            }
            currentHeight = currentHeight.coerceAtLeast(rock.maxOfOrNull { it.y }!!)

            // Search for a Part 2 cycle
            if (part.isTwo() && currentHeight > snapshotHeight) {
                val stateKey = factory.stateKey + buildSnapshot(fallenRocks, currentHeight)
                // When we repeat, simulate until we're close.
                if (stateKey in states.keys) {
                    val heightPerCycle = currentHeight - states[stateKey]!!.second
                    val rocksPerCycle = rocksFallen - states[stateKey]!!.first
                    val cyclesNeeded = (maxRocks - rocksFallen) / rocksPerCycle
                    // Update height and zoom rock counter ahead.
                    simulationHeight += cyclesNeeded * heightPerCycle
                    rocksFallen += cyclesNeeded * rocksPerCycle
                }
                states[stateKey] = Pair(rocksFallen, currentHeight)
            }
            rocksFallen++
        }
        return if (part.isOne()) currentHeight else (currentHeight + simulationHeight)
    }

    /**
     * Push a rock in the jetstream and returns the new rock coordinates.
     */
    private fun push(fallenRocks: Set<Point2D<Long>>, jet: Char, rock: Set<Point2D<Long>>): Set<Point2D<Long>> {
        val xIncrement = if (jet == '<') -1 else 1
        var isLegal = true
        for (point in rock) {
            val nextX = point.x + xIncrement
            val testPoint = Point2D(nextX, point.y)
            isLegal = isLegal && (nextX in 0..6 && testPoint !in fallenRocks)
        }
        if (!isLegal) {
            return rock
        }
        return rock.map { Point2D(it.x + xIncrement, it.y) }.toSet()
    }

    /**
     * Drops a rock 1 level if not blocked and returns the new rock coordinates.
     */
    private fun fall(fallenRocks: MutableSet<Point2D<Long>>, rock: Set<Point2D<Long>>): Set<Point2D<Long>> {
        var isLegal = true
        for (point in rock) {
            val nextY = point.y - 1
            val testPoint = Point2D(point.x, nextY)
            if (testPoint !in rock) {
                isLegal = isLegal && (nextY > 0 && testPoint !in fallenRocks)
            }
        }
        return if (!isLegal) {
            fallenRocks.addAll(rock)
            rock
        } else {
            rock.map { Point2D(it.x, it.y - 1) }.toSet()
        }
    }

    /**
     * Grabs the top rows of blocks for use in a state key.
     */
    private fun buildSnapshot(fallenRocks: Set<Point2D<Long>>, height: Long): String {
        val output = StringBuilder()
        for (y in height downTo (height - snapshotHeight)) {
            for (x in 0L until 7L) {
                output.append(if (Point2D(x, y) in fallenRocks) '#' else '.')
            }
        }
        return output.toString()
    }
}

class Factory(private val pattern: String) {
    private var jetIndex = 0
    private var rockIndex = 0
    val stateKey: String
        get() = "$jetIndex-$rockIndex-"

    /**
     * Returns the next jet character.
     */
    fun nextJet(): Char {
        val next = pattern[jetIndex]
        jetIndex = (jetIndex + 1) % pattern.length
        return next
    }

    /**
     * Returns the next rock
     */
    fun nextRock(height: Long): Set<Point2D<Long>> {
        val shape = mutableSetOf<Point2D<Long>>()
        when (rockIndex) {
            0 -> { // Dash
                shape.add(Point2D(2, height))
                shape.add(Point2D(3, height))
                shape.add(Point2D(4, height))
                shape.add(Point2D(5, height))
            }

            1 -> { // Cross
                shape.add(Point2D(3, height))
                shape.add(Point2D(2, 1 + height))
                shape.add(Point2D(3, 1 + height))
                shape.add(Point2D(4, 1 + height))
                shape.add(Point2D(3, 2 + height))
            }

            2 -> { // Backwards L
                shape.add(Point2D(2, height))
                shape.add(Point2D(3, height))
                shape.add(Point2D(4, height))
                shape.add(Point2D(4, 1 + height))
                shape.add(Point2D(4, 2 + height))
            }

            3 -> { // I
                shape.add(Point2D(2, height))
                shape.add(Point2D(2, 1 + height))
                shape.add(Point2D(2, 2 + height))
                shape.add(Point2D(2, 3 + height))
            }

            else -> { // Square
                shape.add(Point2D(2, height))
                shape.add(Point2D(3, height))
                shape.add(Point2D(2, 1 + height))
                shape.add(Point2D(3, 1 + height))
            }
        }
        rockIndex = (rockIndex + 1) % 5
        return shape
    }
}