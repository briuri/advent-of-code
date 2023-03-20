package buri.aoc.y19.d19

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Point2D
import buri.aoc.common.registers.Computer
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(150, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(12201460, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val computer = Computer(input)
        if (part.isOne()) {
            var inBeam = 0
            for (y in 0..49) {
                for (x in 0..49) {
                    if (computer.inBeam(x, y)) {
                        inBeam++
                    }
                }
            }
            return inBeam
        }

        val size = 100
        var y = size + 1
        var x = 0
        while (true) {
            x = computer.firstXInBeam(y, x)
            val ul = Point2D(x, y - size + 1)
            val ur = Point2D(x + size - 1, y - size + 1)
            val lr = Point2D(x + size - 1, y)
            if (computer.inBeam(ur) && computer.inBeam(lr) && computer.inBeam(ul)) {
                return (ul.x * 10_000 + ul.y)
            }
            y++
        }
    }

    /**
     * Returns true if the point is in the tractor beam.
     */
    private fun Computer.inBeam(x: Int, y: Int): Boolean {
        this.input(x.toLong())
        this.input(y.toLong())
        this.reset()
        this.run()
        return (this.output() == 1L)
    }

    private fun Computer.inBeam(point: Point2D<Int>): Boolean = inBeam(point.x, point.y)

    /**
     * Finds the first point in a row that is in the tractor beam. Since the beam is moving diagonally down, we don't
     * have to reset X back to zero on each row. The next X will always be greater than or equal to the previous one.
     */
    private fun Computer.firstXInBeam(y: Int, previousX: Int): Int {
        var x = previousX
        while (true) {
            if (inBeam(x, y)) {
                return x
            }
            x++
        }
    }
}