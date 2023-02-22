package buri.aoc.y16.d07

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
        assertRun(2, 1)
        assertRun(118, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3, 2)
        assertRun(260, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var count = 0
        for (line in input) {
            val supernets = line.replace("\\[[a-z]+]".toRegex(), " ").split(" ")
            val hypernets = line.replace("^[a-z]+\\[".toRegex(), "")
                .replace("][a-z]+\\[".toRegex(), " ")
                .replace("][a-z]+$".toRegex(), "").trim().split(" ")
            if ((part.isOne() && containsAbba(supernets) && !containsAbba(hypernets))
                || part.isTwo() && containsBab(hypernets, getAbas(supernets))
            ) {
                count++
            }
        }
        return count
    }

    /**
     * Returns true if the list of tokens contains at least one ABBA
     */
    private fun containsAbba(tokens: List<String>): Boolean {
        for (token in tokens) {
            for (i in 0 until token.length - 3) {
                if (token[i] != token[i + 1] && token[i] == token[i + 3] && token[i + 1] == token[i + 2]) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Returns any ABAs in the string.
     */
    private fun getAbas(tokens: List<String>): List<String> {
        val abas = mutableListOf<String>()
        for (token in tokens) {
            for (i in 0 until token.length - 2) {
                if (token[i] != token[i + 1] && token[i] == token[i + 2]) {
                    abas.add(token.substring(i, i + 3))
                }
            }
        }
        return abas
    }

    /**
     * Checks for any BABs based on the provided ABAs.
     */
    private fun containsBab(tokens: List<String>, abas: List<String>): Boolean {
        for (token in tokens) {
            for (aba in abas) {
                val bab = aba.substring(1) + aba[1]
                if (token.contains(bab)) {
                    return true
                }
            }
        }
        return false
    }
}