package buri.aoc.y19.d12

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Point3D
import org.junit.Test
import kotlin.math.absoluteValue

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(12082, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(295693702908636, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val moons = mutableListOf<Moon>()
        for (line in input) {
            moons.add(Moon(line.extractInts(true)))
        }
        if (part.isOne()) {
            repeat(1000) {
                moons.forEach { it.applyGravity(moons) }
                moons.forEach { it.move() }
            }
            return moons.sumOf { it.getEnergy() }
        }

        // Track each axis separately.
        val xStates = mutableSetOf<String>()
        val yStates = mutableSetOf<String>()
        val zStates = mutableSetOf<String>()
        var foundX = false
        var foundY = false
        var foundZ = false
        while (!foundX || !foundY || !foundZ) {
            if (!foundX) {
                val xState = moons.joinToString("") { it.xState }
                if (xState in xStates) {
                    foundX = true
                }
                xStates.add(xState)
            }
            if (!foundY) {
                val yState = moons.joinToString("") { it.yState }
                if (yState in yStates) {
                    foundY = true
                }
                yStates.add(yState)
            }
            if (!foundZ) {
                val zState = moons.joinToString("") { it.zState }
                if (zState in zStates) {
                    foundZ = true
                }
                zStates.add(zState)
            }
            moons.forEach { it.applyGravity(moons) }
            moons.forEach { it.move() }
        }
        return (getLCM(getLCM(xStates.size.toLong(), yStates.size.toLong()), zStates.size.toLong()))
    }
}

class Moon(numbers: List<Int>) {
    private var position: Point3D<Int>
    private var velocity: Point3D<Int>
    val xState: String
        get() = "${position.x}|${velocity.x} "
    val yState: String
        get() = "${position.y}|${velocity.y} "
    val zState: String
        get() = "${position.z}|${velocity.z} "

    init {
        position = Point3D(numbers[0], numbers[1], numbers[2])
        velocity = Point3D(0, 0, 0)
    }

    /**
     * Applies gravity of other moons
     */
    fun applyGravity(moons: List<Moon>) {
        for (moon in moons.filter { it != this }) {
            val dx = getGravityChange(position.x, moon.position.x)
            val dy = getGravityChange(position.y, moon.position.y)
            val dz = getGravityChange(position.z, moon.position.z)
            velocity = velocity.copy(x = velocity.x + dx, y = velocity.y + dy, z = velocity.z + dz)
        }
    }

    /**
     * Moves the moon
     */
    fun move() {
        position = position.copy(x = position.x + velocity.x, y = position.y + velocity.y, z = position.z + velocity.z)
    }

    /**
     * Calculates total energy
     */
    fun getEnergy(): Int {
        val pe = position.x.absoluteValue + position.y.absoluteValue + position.z.absoluteValue
        val ke = velocity.x.absoluteValue + velocity.y.absoluteValue + velocity.z.absoluteValue
        return (pe * ke)
    }

    /**
     * Calculates the change in velocity from gravity.
     */
    private fun getGravityChange(x1: Int, x2: Int): Int {
        return if (x1 > x2) {
            -1
        } else if (x1 < x2) {
            1
        } else {
            0
        }
    }
}