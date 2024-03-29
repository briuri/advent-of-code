package buri.aoc.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File
import java.io.FileNotFoundException
import java.math.BigInteger
import java.util.*

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
        with(this.javaClass) {
            return name.substring(name.indexOf(".y") + 2, name.indexOf(".d"))
        }
    }

    /**
     * Extracts the puzzle day from the package name
     */
    private fun getDay(): String {
        with(this.javaClass) {
            return name.substring(name.indexOf(".d") + 2, name.indexOf(".Puzzle"))
        }
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
    fun <T> generatePermutations(input: List<T>, index: Int = 0): MutableList<List<T>> {
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

    /**
     * Returns the least common multiple of two numbers
     */
    fun getLCM(a: Long, b: Long): Long {
        val highValue = a.coerceAtLeast(b)
        var lcm = highValue
        while (true) {
            if ((lcm % a == 0L) && (lcm % b == 0L)) {
                break
            }
            lcm += highValue
        }
        return lcm
    }
}

/**
 * Enumeration of puzzle parts
 */
enum class Part(val number: Int) {
    ONE(1), TWO(2);

    /**
     * Returns true if this is Part ONE.
     */
    fun isOne(): Boolean = (number == 1)

    /**
     * Returns true if this is Part TWO.
     */
    fun isTwo(): Boolean = (number == 2)
}

/**
 * Extension function for extracting the numbers out of a line of input.
 */
fun String.extractLongs(allowNegative: Boolean = true): List<Long> {
    val pattern = if (allowNegative) "[^0-9\\-]" else "[^0-9]"
    val string = this.replace(pattern.toRegex(), " ").replace("\\s+".toRegex(), " ").trim()
    return (string.split(" ").map { it.toLong() })
}

fun String.extractInts(allowNegative: Boolean = true): List<Int> {
    return this.extractLongs(allowNegative).map { it.toInt() }
}

/**
 * Extension function for building Java BigIntegers
 */
fun Long.toBigInt(): BigInteger = BigInteger.valueOf(this)

/**
 * Extension function for calculating the product of a collection
 */
fun Collection<Number>.product(): Long = this.map { it.toLong() }.reduce { acc, i -> acc * i }