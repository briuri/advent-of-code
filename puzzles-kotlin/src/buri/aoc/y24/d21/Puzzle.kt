package buri.aoc.y24.d21

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(126384, 1)
        assertRun(154208, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(188000493837892, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numericPaths = mutableMapOf<Int, Set<String>>()
        for (line in input) {
            var completeNumericPaths = mutableSetOf<String>()
            var current = 'A'
            for (i in 0..line.lastIndex) {
                val next = line[i]
                completeNumericPaths = concatSegments(completeNumericPaths, getNumericPaths(current, next))
                current = next
            }
            numericPaths[line.dropLast(1).toInt()] = completeNumericPaths
        }

        var sum = 0L
        for ((numericCode, paths) in numericPaths) {
            val minLength = paths.minOfOrNull { getMinLength(part, it) }!!
            sum += numericCode * minLength
        }
        return sum
    }

    /**
     * Recursively translates a path for the "deeper" directional keypad and finds the min length.
     */
    private fun getMinLength(
        part: Part,
        directionalPath: String,
        depth: Int = 0,
        cache: MutableMap<String, Long> = mutableMapOf()
    ): Long {
        val maxDepth = if (part.isOne()) 2 else 25
        if (depth == maxDepth) {
            return (directionalPath.length.toLong())
        }
        val cacheKey = "$directionalPath-$depth"
        if (cacheKey !in cache) {
            var totalMin = 0L
            var current = 'A'
            for (i in 0..directionalPath.lastIndex) {
                val next = directionalPath[i]
                val segmentMin = getDirectionalPaths(current, next).minOf { getMinLength(part, it, depth + 1, cache) }
                totalMin += segmentMin
                current = next
            }
            cache[cacheKey] = totalMin
        }
        return cache[cacheKey]!!
    }

    /**
     * Concatenates possible paths
     *
     * Doesn't work for the directional paths in part 2 because the strings get too long and we run out of memory.
     */
    private fun concatSegments(completePaths: MutableSet<String>, segments: Set<String>): MutableSet<String> {
        val newPaths = mutableSetOf<String>()
        if (completePaths.isEmpty() || segments.isEmpty()) {
            newPaths.addAll(segments)
        } else {
            for (path in segments) {
                for (completePath in completePaths) {
                    newPaths.add("$completePath$path")
                }
            }
        }
        return newPaths
    }

    /**
     * Calculates all possible paths and adds an Activate at the end.
     */
    private fun buildAllPaths(path: String, illegalPath: String? = null): Set<String> {
        val paths = mutableSetOf<String>()
        if (path.isEmpty()) {
            paths.add("A")
        } else {
            for (permutation in generatePermutations(path.toCharArray().map { it.toString() })) {
                paths.add("${permutation.joinToString("")}A")
            }
        }
        if (illegalPath != null) {
            paths.remove("${illegalPath}A")
        }
        return paths
    }

    /**
     * +---+---+---+
     * | 7 | 8 | 9 |
     * +---+---+---+
     * | 4 | 5 | 6 |
     * +---+---+---+
     * | 1 | 2 | 3 |
     * +---+---+---+
     *     | 0 | A | Starts at A
     *     +---+---+
     */
    private fun getNumericPaths(start: Char, end: Char): Set<String> {
        return when (start) {
            '7' -> when (end) {
                '7' -> buildAllPaths("")
                '8' -> buildAllPaths(">")
                '9' -> buildAllPaths(">>")
                '4' -> buildAllPaths("v")
                '5' -> buildAllPaths("v>")
                '6' -> buildAllPaths("v>>")
                '1' -> buildAllPaths("vv")
                '2' -> buildAllPaths("vv>")
                '3' -> buildAllPaths("vv>>")
                '0' -> buildAllPaths("vv>v", "vvv>")
                else -> buildAllPaths(">>vvv", "vvv>>")
            }

            '8' -> when (end) {
                '7' -> buildAllPaths("<")
                '8' -> buildAllPaths("")
                '9' -> buildAllPaths(">")
                '4' -> buildAllPaths("v<")
                '5' -> buildAllPaths("v")
                '6' -> buildAllPaths("v>")
                '1' -> buildAllPaths("vv<")
                '2' -> buildAllPaths("vv")
                '3' -> buildAllPaths("vv>")
                '0' -> buildAllPaths("vvv")
                else -> buildAllPaths("vvv>")
            }

            '9' -> when (end) {
                '7' -> buildAllPaths("<<")
                '8' -> buildAllPaths("<")
                '9' -> buildAllPaths("")
                '4' -> buildAllPaths("v<<")
                '5' -> buildAllPaths("v<")
                '6' -> buildAllPaths("v")
                '1' -> buildAllPaths("vv<<")
                '2' -> buildAllPaths("vv<")
                '3' -> buildAllPaths("vv")
                '0' -> buildAllPaths("vvv<")
                else -> buildAllPaths("vvv")
            }

            '4' -> when (end) {
                '7' -> buildAllPaths("^")
                '8' -> buildAllPaths("^>")
                '9' -> buildAllPaths("^>>")
                '4' -> buildAllPaths("")
                '5' -> buildAllPaths(">")
                '6' -> buildAllPaths(">>")
                '1' -> buildAllPaths("v")
                '2' -> buildAllPaths("v>")
                '3' -> buildAllPaths("v>>")
                '0' -> buildAllPaths("vv>", "vv>")
                else -> buildAllPaths(">>vv", "vv>>")
            }

            '5' -> when (end) {
                '7' -> buildAllPaths("^<")
                '8' -> buildAllPaths("^")
                '9' -> buildAllPaths("^>")
                '4' -> buildAllPaths("<")
                '5' -> buildAllPaths("")
                '6' -> buildAllPaths(">")
                '1' -> buildAllPaths("v<")
                '2' -> buildAllPaths("v")
                '3' -> buildAllPaths("v>")
                '0' -> buildAllPaths("vv")
                else -> buildAllPaths("vv>")
            }

            '6' -> when (end) {
                '7' -> buildAllPaths("^<<")
                '8' -> buildAllPaths("^<")
                '9' -> buildAllPaths("^")
                '4' -> buildAllPaths("<<")
                '5' -> buildAllPaths("<")
                '6' -> buildAllPaths("")
                '1' -> buildAllPaths("v<<")
                '2' -> buildAllPaths("v<")
                '3' -> buildAllPaths("v")
                '0' -> buildAllPaths("vv<")
                else -> buildAllPaths("vv")
            }

            '1' -> when (end) {
                '7' -> buildAllPaths("^^")
                '8' -> buildAllPaths("^^>")
                '9' -> buildAllPaths("^^>>")
                '4' -> buildAllPaths("^")
                '5' -> buildAllPaths("^>")
                '6' -> buildAllPaths("^>>")
                '1' -> buildAllPaths("")
                '2' -> buildAllPaths(">")
                '3' -> buildAllPaths(">>")
                '0' -> buildAllPaths("v>", "v>")
                else -> buildAllPaths(">>v", "v>>")
            }

            '2' -> when (end) {
                '7' -> buildAllPaths("^^<")
                '8' -> buildAllPaths("^^")
                '9' -> buildAllPaths("^^>")
                '4' -> buildAllPaths("^<")
                '5' -> buildAllPaths("^")
                '6' -> buildAllPaths("^>")
                '1' -> buildAllPaths("<")
                '2' -> buildAllPaths("")
                '3' -> buildAllPaths(">")
                '0' -> buildAllPaths("v")
                else -> buildAllPaths("v>")
            }

            '3' -> when (end) {
                '7' -> buildAllPaths("^^<<")
                '8' -> buildAllPaths("^^<")
                '9' -> buildAllPaths("^^")
                '4' -> buildAllPaths("^<<")
                '5' -> buildAllPaths("^<")
                '6' -> buildAllPaths("^")
                '1' -> buildAllPaths("<<")
                '2' -> buildAllPaths("<")
                '3' -> buildAllPaths("")
                '0' -> buildAllPaths("v<")
                else -> buildAllPaths("v")
            }

            '0' -> when (end) {
                '7' -> buildAllPaths("^^^<", "<^^^")
                '8' -> buildAllPaths("^^^")
                '9' -> buildAllPaths("^^^>")
                '4' -> buildAllPaths("^^<<")
                '5' -> buildAllPaths("^^")
                '6' -> buildAllPaths("^^>")
                '1' -> buildAllPaths("^<", "<^")
                '2' -> buildAllPaths("^")
                '3' -> buildAllPaths("^>")
                '0' -> buildAllPaths("")
                else -> buildAllPaths(">")
            }
            // A
            else -> when (end) {
                '7' -> buildAllPaths("^^^<<", "<<^^^")
                '8' -> buildAllPaths("^^^<")
                '9' -> buildAllPaths("^^^")
                '4' -> buildAllPaths("^^<<", "<<^^")
                '5' -> buildAllPaths("^^<")
                '6' -> buildAllPaths("^^")
                '1' -> buildAllPaths("^<<", "<<^")
                '2' -> buildAllPaths("<^")
                '3' -> buildAllPaths("^")
                '0' -> buildAllPaths("<")
                else -> buildAllPaths("")
            }
        }
    }

    /**
     *     +---+---+
     *     | ^ | A |
     * +---+---+---+
     * | < | v | > | Starts at A
     * +---+---+---+
     */
    private fun getDirectionalPaths(start: Char, end: Char): Set<String> {
        return when (start) {
            '^' -> when (end) {
                '^' -> buildAllPaths("")
                '<' -> buildAllPaths("v<", "<v")
                'v' -> buildAllPaths("v")
                '>' -> buildAllPaths("v>")
                else -> buildAllPaths(">")
            }

            '<' -> when (end) {
                '^' -> buildAllPaths("^>", "^>")
                '<' -> buildAllPaths("")
                'v' -> buildAllPaths(">")
                '>' -> buildAllPaths(">>")
                else -> buildAllPaths(">>^", "^>>")
            }

            'v' -> when (end) {
                '^' -> buildAllPaths("^")
                '<' -> buildAllPaths("<")
                'v' -> buildAllPaths("")
                '>' -> buildAllPaths(">")
                else -> buildAllPaths("^>")
            }

            '>' -> when (end) {
                '^' -> buildAllPaths("<^")
                '<' -> buildAllPaths("<<")
                'v' -> buildAllPaths("<")
                '>' -> buildAllPaths("")
                else -> buildAllPaths("^")
            }

            else -> when (end) {
                '^' -> buildAllPaths("<")
                '<' -> buildAllPaths("v<<", "<<v")
                'v' -> buildAllPaths("<v")
                '>' -> buildAllPaths("v")
                else -> buildAllPaths("")
            }
        }
    }
}