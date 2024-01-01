package buri.aoc.viz.util

import java.io.File

/**
 * Simple utility to count lines of code in competition files. Ignores whitespace lines.
 *
 * @author Brian Uri!
 */
fun main() {
    val pathPrefix = "C:\\workspace\\advent-of-code"
    val javaPrefix = "$pathPrefix\\puzzles-java\\src\\buri\\aoc"
    val kotlinPrefix = "$pathPrefix\\puzzles-kotlin\\src\\buri\\aoc"

    println("Java Solutions:")
    for (year in 15..22) {
        val loc = countLines("$javaPrefix\\y$year", year)
        println("20$year\t$loc")
    }
    println("Kotlin Solutions:")
    for (year in 15..23) {
        val loc = countLines("$kotlinPrefix\\y$year", year)
        println("20$year\t$loc")
    }
}

/**
 * Counts the lines of code in a particular path for the given years
 */
fun countLines(prefix: String, year: Int): Int {
    var loc = 0
    for (day in 1..25) {
        val paddedDay = if (day < 10) "0$day" else "$day"
        val dirPath = "$prefix\\d$paddedDay"
        for (file in File("$dirPath").listFiles()) {
            val lines = file.readLines()
            val count = lines.filter { it != "" }.size
            loc += count
        }
    }
    return loc
}