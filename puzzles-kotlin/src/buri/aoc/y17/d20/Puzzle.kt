package buri.aoc.y17.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.Part.TWO
import buri.aoc.common.extractInts
import buri.aoc.common.getManhattanDistance
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
        val particles = mutableListOf<Particle>()
        for ((index, line) in input.withIndex()) {
            particles.add(Particle(index, line))
        }
        repeat(1000) {
            particles.forEach { it.tick() }
            if (part == TWO) {
                val particlesAt = mutableMapOf<Triple<Long, Long, Long>, MutableList<Particle>>()
                for (particle in particles) {
                    particlesAt.putIfAbsent(particle.position, mutableListOf())
                    particlesAt[particle.position]!!.add(particle)
                }
                for (collision in particlesAt.values.filter { it.size > 1 }) {
                    particles.removeAll(collision)
                }
            }
        }
        if (part == ONE) {
            return particles.minByOrNull { it.position.getManhattanDistance() }!!.num
        }
        return particles.size
    }
}

data class Particle(val num: Int, val input: String) {
    var position: Triple<Long, Long, Long>
    private var velocity: Triple<Long, Long, Long>
    private var acceleration: Triple<Long, Long, Long>

    init {
        val numbers = input.extractInts().map { it.toLong() }
        position = Triple(numbers[0], numbers[1], numbers[2])
        velocity = Triple(numbers[3], numbers[4], numbers[5])
        acceleration = Triple(numbers[6], numbers[7], numbers[8])
    }

    /**
     * Updates particle position and velocity.
     */
    fun tick() {
        velocity = velocity.copy(
            first = velocity.first + acceleration.first,
            second = velocity.second + acceleration.second,
            third = velocity.third + acceleration.third
        )
        position = position.copy(
            first = position.first + velocity.first,
            second = position.second + velocity.second,
            third = position.third + velocity.third
        )
    }
}