package buri.aoc.y21.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(474140, 1)
        assertRun(609563, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2758514936282235, 1)
        assertRun(1234650223944734, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val cuboids = mutableListOf<Cuboid>()
        for (line in input) {
            val on = line.startsWith("on")
            val numbers = line.extractInts()
            cuboids.add(Cuboid(numbers[0], numbers[1], numbers[2], numbers[3], numbers[4], numbers[5], on))
        }

        val onCuboids = mutableSetOf<Cuboid>()
        val discarded = mutableSetOf<Cuboid>()
        for (cuboid in cuboids) {
            if (part.isOne() && !cuboid.inInitArea()) {
                continue
            }
            for (other in onCuboids.filter { it != cuboid }) {
                val intersection = cuboid.intersection(other)
                if (intersection != null) {
                    discarded.add(other)
                    onCuboids.addAll(other.remove(intersection))
                }
            }
            onCuboids.removeAll(discarded)
            if (cuboid.on) {
                onCuboids.add(cuboid)
            }
        }
        return onCuboids.sumOf { it.volume() }
    }
}

data class Cuboid(
    val minX: Int, val maxX: Int,
    val minY: Int, val maxY: Int,
    val minZ: Int, val maxZ: Int,
    val on: Boolean
) {

    /**
     * Returns a new cuboid representing the intersection with another cuboid.
     */
    fun intersection(other: Cuboid): Cuboid? {
        if (maxX < other.minX || other.maxX < minX || maxY < other.minY || other.maxY < minY || maxZ < other.minZ || other.maxZ < minZ) {
            return null
        }

        val xs = mutableListOf<Int>()
        xs.add(minX)
        xs.add(maxX)
        xs.add(other.minX)
        xs.add(other.maxX)
        xs.sort()

        val ys = mutableListOf<Int>()
        ys.add(minY)
        ys.add(maxY)
        ys.add(other.minY)
        ys.add(other.maxY)
        ys.sort()

        val zs = mutableListOf<Int>()
        zs.add(minZ)
        zs.add(maxZ)
        zs.add(other.minZ)
        zs.add(other.maxZ)
        zs.sort()

        return Cuboid(xs[1], xs[2], ys[1], ys[2], zs[1], zs[2], on)
    }

    /**
     * Removes a cuboid from this cuboid and returns the remaining chunks.
     */
    fun remove(chunk: Cuboid): List<Cuboid> {
        val chunks = mutableListOf<Cuboid>()
        if (minX < chunk.minX) {
            chunks.add(Cuboid(minX, chunk.minX - 1, minY, maxY, minZ, maxZ, on))
        }
        if (chunk.maxX < maxX) {
            chunks.add(Cuboid(chunk.maxX + 1, maxX, minY, maxY, minZ, maxZ, on))
        }
        if (minY < chunk.minY) {
            chunks.add(Cuboid(chunk.minX, chunk.maxX, minY, chunk.minY - 1, minZ, maxZ, on))
        }
        if (chunk.maxY < maxY) {
            chunks.add(Cuboid(chunk.minX, chunk.maxX, chunk.maxY + 1, maxY, minZ, maxZ, on))
        }
        if (minZ < chunk.minZ) {
            chunks.add(Cuboid(chunk.minX, chunk.maxX, chunk.minY, chunk.maxY, minZ, chunk.minZ - 1, on))
        }
        if (chunk.maxZ < maxZ) {
            chunks.add(Cuboid(chunk.minX, chunk.maxX, chunk.minY, chunk.maxY, chunk.maxZ + 1, maxZ, on))
        }
        return chunks
    }

    /**
     * Returns true if this cuboid is in the init area.
     */
    fun inInitArea(): Boolean {
        return minX >= -50 && minY >= -50 && minZ >= -50 && maxX <= 50 && maxY <= 50 && maxZ <= 50
    }

    /**
     * Calculates the volume of this cuboid.
     */
    fun volume(): Long {
        val x = maxX - minX + 1L
        val y = maxY - minY + 1L
        val z = maxZ - minZ + 1L
        return x * y * z
    }
}