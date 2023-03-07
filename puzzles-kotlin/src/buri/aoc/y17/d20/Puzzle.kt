package buri.aoc.y17.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
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
        assertRun(170, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(571, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val origin = Point3D(0L, 0L, 0L)
        val particles = mutableListOf<Particle>()
        for ((index, line) in input.withIndex()) {
            particles.add(Particle(index, line.extractInts().map { it.toLong() }))
        }
        repeat(1000) {
            particles.forEach { it.tick() }
            if (part.isTwo()) {
                val particlesAt = mutableMapOf<Point3D<Long>, MutableList<Particle>>()
                for (particle in particles) {
                    particlesAt.putIfAbsent(particle.position, mutableListOf())
                    particlesAt[particle.position]!!.add(particle)
                }
                for (collision in particlesAt.values.filter { it.size > 1 }) {
                    particles.removeAll(collision)
                }
            }
        }
        if (part.isOne()) {
            return particles.minByOrNull { it.position.getManhattanDistance(origin) }!!.num
        }
        return particles.size
    }
}

class Particle(val num: Int, numbers: List<Long>) {
    var position: Point3D<Long>
    private var velocity: Point3D<Long>
    private var acceleration: Point3D<Long>

    init {
        position = Point3D(numbers[0], numbers[1], numbers[2])
        velocity = Point3D(numbers[3], numbers[4], numbers[5])
        acceleration = Point3D(numbers[6], numbers[7], numbers[8])
    }

    /**
     * Updates particle position and velocity.
     */
    fun tick() {
        velocity = velocity.copy(
            x = velocity.x + acceleration.x,
            y = velocity.y + acceleration.y,
            z = velocity.z + acceleration.z
        )
        position = position.copy(
            x = position.x + velocity.x,
            y = position.y + velocity.y,
            z = position.z + velocity.z
        )
    }
}