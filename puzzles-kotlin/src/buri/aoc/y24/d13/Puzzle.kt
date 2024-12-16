package buri.aoc.y24.d13

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import buri.aoc.common.position.Point2D
import org.junit.Test
import kotlin.math.floor

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(480, 1)
        assertRun(32067, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(92871736253789, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val tokens = mutableListOf<Long>()
        for (chunk in input.chunked(4)) {
            val aTokens = chunk[0].extractLongs()
            val bTokens = chunk[1].extractLongs()
            val prizeTokens = chunk[2].extractLongs()

            val aDiff = Point2D(aTokens[0], aTokens[1])
            val bDiff = Point2D(bTokens[0], bTokens[1])
            var prizePos = Point2D(prizeTokens[0], prizeTokens[1])
            if (part.isTwo()) {
                prizePos = Point2D(prizePos.x + 10000000000000, prizePos.y + 10000000000000)
            }

            // Did it with z3 in real-time, then went back and used Cramer's rule
            // https://en.wikipedia.org/wiki/Cramer's_rule

//            with(Context()) {
//                val solver = mkSolver()
//                val a = mkIntConst("a")
//                val b = mkIntConst("b")
//                // (a * aDiff.x) + (b * bDiff.x) = prizePos.x
//                val xEquation = mkEq(
//                    mkAdd(mkMul(a, mkReal(aDiff.x)), mkMul(b, mkReal(bDiff.x))),
//                    mkReal(prizePos.x)
//                )
//                // (a * aDiff.y) + (b * bDiff.y) = prizePos.y
//                val yEquation = mkEq(
//                    mkAdd(mkMul(a, mkReal(aDiff.y)), mkMul(b, mkReal(bDiff.y))),
//                    mkReal(prizePos.y)
//                )
//                solver.add(xEquation)
//                solver.add(yEquation)
//
//                if (solver.check() == Status.SATISFIABLE) {
//                    val solvedA = solver.model.eval(a, false).toString().toLong()
//                    val solvedB = solver.model.eval(b, false).toString().toLong()
//                    tokens.add(3 * solvedA + 1 * solvedB)
//                }
//            }

            val determinant = (aDiff.x * bDiff.y - aDiff.y * bDiff.x).toDouble()
            val a = (prizePos.x * bDiff.y - prizePos.y * bDiff.x) / determinant
            val b = (prizePos.y * aDiff.x - prizePos.x * aDiff.y) / determinant
            if (a == floor(a) && b == floor(b)) {
                tokens.add((3 * a + 1 * b).toLong())
            }
        }
        return tokens.sum()
    }
}