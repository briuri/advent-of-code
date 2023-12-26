package buri.aoc.y23.d24

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import buri.aoc.common.position.Point3D
import com.microsoft.z3.Context
import com.microsoft.z3.Status
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(2, 1)
        assertRun(14672, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(47, 1)
        assertRun(646_810_057_104_753, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val stones = mutableListOf<Stone>()
        for (line in input) {
            stones.add(Stone(line))
        }
        val range = if (input.size == 5) (7L..27L) else (200_000_000_000_000..400_000_000_000_000)

        if (part.isOne()) {
            var count = 0
            val visited = mutableSetOf<Stone>()
            for (stoneA in stones) {
                visited.add(stoneA)
                for (stoneB in stones.filter { it !in visited }) {
                    val x =
                        (stoneA.b * stoneB.c - stoneB.b * stoneA.c) / (stoneA.a * stoneB.b - stoneB.a * stoneA.b)
                    val y =
                        (stoneA.c * stoneB.a - stoneB.c * stoneA.a) / (stoneA.a * stoneB.b - stoneB.a * stoneA.b)
                    val aTime = (x - stoneA.position.x) / stoneA.velocity.x
                    val bTime = (x - stoneB.position.x) / stoneB.velocity.x
                    if (x.toLong() in range && y.toLong() in range && aTime > 0 && bTime > 0) {
                        count++
                    }
                }
            }
            return count
        }

        /*
            During my solve, I ended up switching to Python and using Z3.
            Afterwards, I learned how to call Z3 from Kotlin and ported my solution over.

            I did some exploratory work on finding a good code-y solution but have not (yet)
            made great progress. For example:

            1) You can get away with just plotting 3 of the hailstones.
            2) Over time, the sum of the Manhattan distances between hailstones shrinks, holds steady, then grows.
               The inflection point is probably where some of the intersections will occur.
         */

        // Use Z3 to solve the equations.
        with(Context()) {
            val solver = mkSolver()
            val posX = mkRealConst("posX")
            val posY = mkRealConst("posY")
            val posZ = mkRealConst("posZ")
            val velX = mkRealConst("velX")
            val velY = mkRealConst("velY")
            val velZ = mkRealConst("velZ")
            for ((index, stone) in stones.withIndex()) {
                val time = mkRealConst("time$index")
                // posX + velX * time = posX0 + velX0 * time
                val xEquation = mkEq(
                    mkAdd(posX, mkMul(velX, time)),
                    mkAdd(mkReal(stone.position.x), mkMul(mkReal(stone.velocity.x), time))
                )
                // posY + velY * time = posY0 + velY0 * time
                val yEquation = mkEq(
                    mkAdd(posY, mkMul(velY, time)),
                    mkAdd(mkReal(stone.position.y), mkMul(mkReal(stone.velocity.y), time))
                )
                // posZ + velZ * time = posZ0 + velZ0 * time
                val zEquation = mkEq(
                    mkAdd(posZ, mkMul(velZ, time)),
                    mkAdd(mkReal(stone.position.z), mkMul(mkReal(stone.velocity.z), time))
                )
                solver.add(xEquation)
                solver.add(yEquation)
                solver.add(zEquation)
            }
            if (solver.check() == Status.SATISFIABLE) {
                val solvedX = solver.model.eval(posX, false).toString().toLong()
                val solvedY = solver.model.eval(posY, false).toString().toLong()
                val solvedZ = solver.model.eval(posZ, false).toString().toLong()
                return solvedX + solvedY + solvedZ
            }
        }
        throw Exception("Could not solve equations.")
    }
}

data class Stone(val line: String) {
    val position: Point3D<Long>
    val velocity: Point3D<Long>

    init {
        val numbers = line.extractLongs(true)
        position = Point3D(numbers[0], numbers[1], numbers[2])
        velocity = Point3D(numbers[3], numbers[4], numbers[5])
    }

    /*
        Values used to calculate the intersection position (independent of time) in Part 1.

        y = mx + b
        0 = mx + b - y
        0 = mx + (-1) y + b

        0 = a1x0 + b1y0 + c1
        0 = a2x0 + b2y0 + c2
    */
    val a = (velocity.y.toDouble() / velocity.x.toDouble())
    val b = -1
    val c = (position.y - a * position.x)

    override fun toString() = "[$line]"
}
