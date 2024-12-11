package buri.aoc.y24.d09

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
        assertRun(1928, 1)
        assertRun(6330095022244, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2858, 1)
        assertRun(6359491814941, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val spaces = ArrayDeque<Space>()
        val files = ArrayDeque<File>()
        var id = 0
        var driveIndex = 0
        for ((index, item) in input.first().withIndex()) {
            val length = item.digitToInt()
            if (index % 2 == 0) {
                files.add(File(id, driveIndex, length))
                id++
            } else if (length > 0) {
                spaces.add(Space(driveIndex, length))
            }
            driveIndex += length
        }

        if (part.isOne()) {
            while (spaces.isNotEmpty()) {
                val space = spaces.removeFirst()
                // Space is at end of drive so we're done.
                if (space.index > files.last().index) {
                    break
                }

                val file = files.removeLast()
                // Whole file fits in space
                if (space.length >= file.length) {
                    files.addFirst(File(file.id, space.index, file.length))
                    if (space.length - file.length > 0) {
                        spaces.addFirst(Space(space.index + file.length, space.length - file.length))
                    }
                }
                // Partial file fits in space and a chunk remains to be processed
                else {
                    files.addFirst(File(file.id, space.index, space.length))
                    files.addLast(File(file.id, file.index, file.length - space.length))
                }
            }
            return files.sumOf { it.checksum() }
        }
        // Part TWO
        while (files.last().id > 0) {
            val file = files.removeLast()
            val space = spaces.firstOrNull { it.length >= file.length && it.index < file.index }
            if (space != null) {
                files.addFirst(File(file.id, space.index, file.length))
                if (space.length - file.length > 0) {
                    spaces[spaces.indexOf(space)] = Space(space.index + file.length, space.length - file.length)
                }
                else {
                    spaces.remove(space)
                }
            } else {
                files.addFirst(file)
            }
        }
        return files.sumOf { it.checksum() }
    }
}

data class File(val id: Int, val index: Int, val length: Int) {
    fun checksum(): Long {
        var sum = 0L
        for (i in 0..<length) {
            sum += (index + i) * id
        }
        return sum
    }
}
data class Space(val index: Int, val length: Int)