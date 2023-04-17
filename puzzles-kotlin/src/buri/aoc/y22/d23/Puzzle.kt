package buri.aoc.y22.d23

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Bounds2D
import buri.aoc.common.position.Point2D
import buri.aoc.common.position.getNeighbors
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(110, 1)
        assertRun(4195, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(20, 1)
        assertRun(1069, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var elves = mutableSetOf<Point2D<Int>>()
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                if (value == '#') {
                    elves.add(Point2D(x, y))
                }
            }
        }

        val rounds = if (part.isOne()) 10 else 2000
        for (round in 1..rounds) {
            var elfMoved = false
            val nextElves = mutableSetOf<Point2D<Int>>()
            val proposedMoves = mutableMapOf<Point2D<Int>, Point2D<Int>?>()
            val proposedCounts = mutableMapOf<Point2D<Int>, Int>()

            // Proposal phase
            for (elf in elves) {
                val neighborElves = elf.getNeighbors(true).filter { it in elves }
                if (neighborElves.isNotEmpty()) {
                    val move = getProposedMove(elves, elf, round)
                    if (move != null) {
                        proposedMoves[elf] = move
                        val count = proposedCounts.getOrDefault(move, 0)
                        proposedCounts[move] = count + 1
                    }
                }
            }

            // Move phase
            for (elf in elves) {
                val target = proposedMoves[elf]
                if (target == null || proposedCounts[target]!! > 1) {
                    nextElves.add(elf)
                } else {
                    elfMoved = true
                    nextElves.add(target)
                }
            }

            // Check if no one moved.
            if (part.isTwo() && !elfMoved) {
                return round
            }
            elves = nextElves
        }

        val bounds = Bounds2D(elves)
        var count = 0
        for (y in bounds.y) {
            for (x in bounds.x) {
                if (Point2D(x, y) !in elves) {
                    count++
                }
            }
        }
        return count
    }

    /**
     * Determine which move to propose.
     */
    private fun getProposedMove(elves: Set<Point2D<Int>>, elf: Point2D<Int>, round: Int): Point2D<Int>? {
        val target = when ((round - 1) % 4) {
            0 -> testNorth(elves, elf) ?: testSouth(elves, elf) ?: testWest(elves, elf) ?: testEast(elves, elf)
            1 -> testSouth(elves, elf) ?: testWest(elves, elf) ?: testEast(elves, elf) ?: testNorth(elves, elf)
            2 -> testWest(elves, elf) ?: testEast(elves, elf) ?: testNorth(elves, elf) ?: testSouth(elves, elf)
            else -> testEast(elves, elf) ?: testNorth(elves, elf) ?: testSouth(elves, elf) ?: testWest(elves, elf)
        }
        return target
    }

    /**
     * See if we can propose a north move.
     */
    private fun testNorth(elves: Set<Point2D<Int>>, elf: Point2D<Int>): Point2D<Int>? {
        val neighbors = elf.getNeighbors(true).filter { it.y == elf.y - 1 }
        return if (neighbors.intersect(elves).isNotEmpty()) null else Point2D(elf.x, elf.y - 1)
    }

    /**
     * See if we can propose a south move.
     */
    private fun testSouth(elves: Set<Point2D<Int>>, elf: Point2D<Int>): Point2D<Int>? {
        val neighbors = elf.getNeighbors(true).filter { it.y == elf.y + 1 }
        return if (neighbors.intersect(elves).isNotEmpty()) null else Point2D(elf.x, elf.y + 1)
    }

    /**
     * See if we can propose an east move.
     */
    private fun testEast(elves: Set<Point2D<Int>>, elf: Point2D<Int>): Point2D<Int>? {
        val neighbors = elf.getNeighbors(true).filter { it.x == elf.x + 1 }
        return if (neighbors.intersect(elves).isNotEmpty()) null else Point2D(elf.x + 1, elf.y)
    }

    /**
     * See if we can propose a west move.
     */
    private fun testWest(elves: Set<Point2D<Int>>, elf: Point2D<Int>): Point2D<Int>? {
        val neighbors = elf.getNeighbors(true).filter { it.x == elf.x - 1 }
        return if (neighbors.intersect(elves).isNotEmpty()) null else Point2D(elf.x - 1, elf.y)
    }
}