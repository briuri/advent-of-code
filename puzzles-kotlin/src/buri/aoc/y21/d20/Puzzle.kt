package buri.aoc.y21.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Bounds2D
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
        assertRun(35, 1)
        assertRun(5461, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3351, 1)
        assertRun(18226, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val algorithm = input[0].map { if (it == '#') 1 else 0 }
        var image = mutableMapOf<Point2D<Int>, Int>()
        for ((y, line) in input.drop(2).withIndex()) {
            for ((x, value) in line.withIndex()) {
                image[Point2D(x, y)] = if (value == '#') 1 else 0
            }
        }

        val times = if (part.isOne()) 2 else 50
        repeat(times) {
            val bounds = Bounds2D(image.keys)
            val nextImage = mutableMapOf<Point2D<Int>, Int>()
            for (y in bounds.y.first - 2..bounds.y.last + 2) {
                for (x in bounds.x.first - 2..bounds.x.last + 2) {
                    val index = buildIndex(image, x, y, algorithm[0], it)
                    nextImage[Point2D(x, y)] = algorithm[index]
                }
            }
            image = nextImage
        }
        return image.count { it.value == 1 }
    }

    /**
     * Builds the index for a point, taking into account the infinite ON state of the edges.
     */
    private fun buildIndex(image: Map<Point2D<Int>, Int>, x: Int, y: Int, alg0: Int, step: Int): Int {
        val rawIndex = StringBuilder()
        for (y1 in y - 1..y + 1) {
            for (x1 in x - 1..x + 1) {
                val defaultValue = if (alg0 == 1 && step % 2 == 1) 1 else 0
                rawIndex.append(image.getOrDefault(Point2D(x1, y1), defaultValue))
            }
        }
        return rawIndex.toString().toInt(2)
    }
}