package buri.aoc.y23.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Bounds3D
import buri.aoc.common.position.Point3D
import org.junit.Test
import java.util.*
import kotlin.math.absoluteValue

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(5, 1)
        assertRun(403, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(7, 1)
        assertRun(70189, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val bricks = input.map { Brick.fromString(it) }.toMutableList()
        bricks.sortBy { it.zLevel }
        // Drop all bricks down to initial rest state.
        bricks.fall()
        return bricks.getDisintegrationCount(part)
    }

    /**
     * Makes all bricks fall as far as possible. Returns a list of the indices of any brick that fell.
     */
    private fun MutableList<Brick>.fall(): List<Int> {
        val fallenIndexes = mutableListOf<Int>()
        for ((index, brick) in this.withIndex()) {
            var current = brick
            while (true) {
                val fallen = current.fall()
                // Optimization: Only check blocks within a reasonable distance for overlaps (picked 5)
                val otherBricks = this.filter { it != brick && (fallen.zLevel - it.zLevel).absoluteValue < 5 }
                // Brick can only fall if it stays above ground and doesn't overlap with any OTHER brick.
                if (fallen.isAboveGround() && otherBricks.none { it.overlapsWith(fallen) }) {
                    fallenIndexes.add(index)
                    current = fallen
                }
                else {
                    break
                }
            }
            this[index] = current
        }
        return fallenIndexes
    }

    /**
     * In part 1, returns the number of bricks that can be removed without danger.
     * In part 2, returns the sum of the number of bricks that would fall if each brick was removed one at a time.
     */
    private fun List<Brick>.getDisintegrationCount(part: Part): Int {
        var count = 0
        for (brick in this) {
            val otherBricks = this.filter { it != brick }.toMutableList()
            val fallenIndexes = otherBricks.fall()
            // Nothing fell after removing this brick.
            if (part.isOne() && fallenIndexes.isEmpty()) {
                count++
            }
            // Count the number of unique bricks that fell.
            else if (part.isTwo()) {
                count += fallenIndexes.toSet().size
            }
        }
        return count
    }
}

/**
 * Data class representing a single brick.
 */
data class Brick(val a: Point3D<Int>, val b: Point3D<Int>) {
    private val cubes = mutableSetOf<Point3D<Int>>()
    val zLevel = a.z.coerceAtMost(b.z)

    init {
        val bounds = Bounds3D(setOf(a, b))
        for (z in bounds.z) {
            for (y in bounds.y) {
                for (x in bounds.x) {
                    cubes.add(Point3D(x, y, z))
                }
            }
        }
    }

    /**
     * Returns this brick dropped 1 z-level lower.
     */
    fun fall() = Brick(a.copy(z = a.z - 1), b.copy(z = b.z - 1))

    /**
     * Returns true if this brick overlaps with a potential fallen brick.
     */
    fun overlapsWith(brick: Brick): Boolean = cubes.intersect(brick.cubes).isNotEmpty()

    /**
     * Returns true if this brick is above ground.
     */
    fun isAboveGround(): Boolean = cubes.none { it.z < 1 }

    override fun toString() = "[$a~$b]"

    companion object {

        /**
         * Generates a brick from an input string.
         */
        fun fromString(line: String): Brick {
            val rawA = line.split("~")[0].split(",").map { it.toInt() }
            val a = Point3D(rawA[0], rawA[1], rawA[2])
            val rawB = line.split("~")[1].split(",").map { it.toInt() }
            val b = Point3D(rawB[0], rawB[1], rawB[2])
            return Brick(a, b)
        }
    }
}