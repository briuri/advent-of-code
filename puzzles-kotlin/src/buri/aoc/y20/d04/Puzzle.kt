package buri.aoc.y20.d04

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
        assertRun(233, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(0, 1)
        assertRun(111, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var valid = 0
        var i = 0
        val data = mutableListOf<String>()
        while (i <= input.lastIndex) {
            if (input[i].isEmpty()) {
                if (Passport(data).isValid(part)) {
                    valid++
                }
                data.clear()
            } else {
                data.addAll(input[i].split(" "))
            }
            i++
        }
        // One more passport in the queue that doesn't end with a blank line.
        if (Passport(data).isValid(part)) {
            valid++
        }
        return valid
    }
}

class Passport(data: List<String>) {
    private val map = mutableMapOf<String, String>()
    private val required = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    private val eyeColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    private val hclPattern = "^#[a-z0-9]{6}$".toPattern()
    private val pidPattern = "^\\d{9}$".toPattern()

    init {
        for (item in data.sorted()) {
            val tokens = item.split(":")
            if (tokens[0] != "cid") {
                map[tokens[0]] = tokens[1]
            }
        }
    }

    /**
     * Returns true if the passport is valid.
     */
    fun isValid(part: Part): Boolean {
        val allPresent = map.keys.containsAll(required)
        if (part.isOne() || !allPresent) {
            return allPresent
        }

        var valid = true

        // byr
        val byr = get("byr").toInt()
        valid = valid && (byr in 1920..2002)

        // iyr
        val iyr = get("iyr").toInt()
        valid = valid && (iyr in 2010..2020)

        // eyr
        val eyr = get("eyr").toInt()
        valid = valid && (eyr in 2020..2030)

        // hgt
        val unit = get("hgt").takeLast(2)
        valid = valid && (unit == "cm" || unit == "in")
        if (valid) {
            val hgt = get("hgt").dropLast(2).toInt()
            valid = if (unit == "cm") {
                (hgt in 150..193)
            } else {
                (hgt in 59..76)
            }
        }

        // hcl
        valid = valid && hclPattern.matcher(get("hcl")).matches()

        // ecl
        valid = valid && get("ecl") in eyeColors

        // pid
        valid = valid && pidPattern.matcher(get("pid")).matches()

        return valid
    }

    /**
     * Returns the field matching this name.
     */
    private fun get(name: String): String {
        return map[name]!!
    }

    override fun toString(): String = map.size.toString() + " " + map.toString()
}