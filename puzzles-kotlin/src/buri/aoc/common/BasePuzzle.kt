package buri.aoc.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.math.absoluteValue

/**
 * Base class with shared functionality for all puzzles
 *
 * @author Brian Uri!
 */
abstract class BasePuzzle {

    /**
     * Abstract function for running a part of a puzzle
     */
    abstract fun run(part: Part, input: List<String>): Any

    /**
     * Runs part of a puzzle and compares the result to the expected value.
     */
    fun assertRun(expected: Any, fileIndex: Int, toConsole: Boolean = false) {
        val year = getYear()
        val day = getDay()
        val part = if (getPart() == "1") Part.ONE else Part.TWO

        val path = "data/y$year/$day-$fileIndex.txt"
        val input = try {
            File(path).readLines()
        } catch (e: FileNotFoundException) {
            File("data/zNew/$day-$fileIndex.txt").readLines()
        }
        val actual = this.run(part, input)
        if (toConsole) {
            toConsole(actual)
        }
        if (actual is Long && expected is Int) {
            assertEquals(expected.toLong(), actual)
        } else if (expected.toString().contains("■")) {
            assertTrue(actual.toString().startsWith(expected.toString()))
        } else {
            assertEquals(expected, actual)
        }
    }

    /**
     * Saves the answer to the console and the system clipboard.
     */
    private fun toConsole(value: Any) {
        val year = getYear()
        val day = getDay()
        val part = getPart()
        println("### 20$year Day $day Part $part ###")
        println(value)

        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(StringSelection(value.toString()), null)
    }

    /**
     * Extracts the puzzle year from the package name
     */
    private fun getYear(): String {
        val name = this.javaClass.name
        return name.substring(name.indexOf(".y") + 2, name.indexOf(".d"))
    }

    /**
     * Extracts the puzzle day from the package name
     */
    private fun getDay(): String {
        val name = this.javaClass.name
        return name.substring(name.indexOf(".d") + 2, name.indexOf(".Puzzle"))
    }

    /**
     * Extracts the puzzle part from the test method name
     */
    private fun getPart(): String {
        val trace = Thread.currentThread().stackTrace
        var testName = ""
        for (stackTraceElement in trace) {
            if (stackTraceElement.methodName.contains("runPart")) {
                testName = stackTraceElement.methodName
                break
            }
        }
        return testName.substring(testName.indexOf("Part") + 4)
    }

    /**
     * Generates every permutation for a list of objects.
     */
    fun <T> generatePermutations(input: List<T>, index: Int): MutableList<List<T>> {
        val permutations = mutableListOf<List<T>>()
        if (index == input.lastIndex) {
            permutations.add(input.toList())
        }
        for (i in index..input.lastIndex) {
            Collections.swap(input, index, i)
            permutations.addAll(generatePermutations(input, index + 1))
            Collections.swap(input, i, index)
        }
        return permutations
    }
}

/**
 * Enumeration of puzzle parts
 */
enum class Part(val number: Int) {
    ONE(1), TWO(2)
}

/**
 * Extension function for getting neighbors of a point.
 */
fun Pair<Int, Int>.getNeighbors(includeDiagonals: Boolean = false): MutableList<Pair<Int, Int>> {
    val list = mutableListOf<Pair<Int, Int>>()
    list.add(Pair(this.first - 1, this.second))
    list.add(Pair(this.first + 1, this.second))
    list.add(Pair(this.first, this.second - 1))
    list.add(Pair(this.first, this.second + 1))
    if (includeDiagonals) {
        list.add(Pair(this.first - 1, this.second - 1))
        list.add(Pair(this.first + 1, this.second - 1))
        list.add(Pair(this.first - 1, this.second + 1))
        list.add(Pair(this.first + 1, this.second + 1))
    }
    return list
}

/**
 * Extension function for the Manhattan distance of a 2D point from the origin.
 */
fun Pair<Int, Int>.getManhattanDistance(): Int {
    return this.first.absoluteValue + this.second.absoluteValue
}

/**
 * Extension function for the Manhattan distance of a 3D point from the origin.
 */
fun Triple<Long, Long, Long>.getManhattanDistance(): Long {
    return this.first.absoluteValue + this.second.absoluteValue + this.third.absoluteValue
}

/**
 * Extension function for collapsing multiple whitespace to a single whitespace.
 */
fun String.collapseWhitespace(): String {
    return this.replace("\\s+".toRegex(), " ").trim()
}