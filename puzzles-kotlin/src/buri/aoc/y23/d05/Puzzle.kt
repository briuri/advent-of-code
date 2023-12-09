package buri.aoc.y23.d05

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
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
        assertRun(806029445, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(46, 1)
        assertRun(59370572, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val farmMaps = mutableListOf<FarmMap>()
        var rawMaps = input.subList(2, input.size).filter { !it.endsWith("map:") }
        // Added empty lines at the end of the input files to simplify parsing.
        while (rawMaps.isNotEmpty()) {
            val end = rawMaps.indexOf("")
            farmMaps.add(FarmMap(rawMaps.subList(0, end)))
            rawMaps = rawMaps.subList(end + 1, rawMaps.size)
        }

        var seedRanges = mutableListOf<LongRange>()
        // Turned part 1 into a special case of part 2 after submitting.
        if (part.isOne()) {
            seedRanges.addAll(input[0].extractLongs().map { it..it })
        } else {
            for ((start, range) in input[0].extractLongs().chunked(2)) {
                seedRanges.add(start until (start + range))
            }
        }

        for (farmMap in farmMaps) {
            val nextRanges = mutableListOf<LongRange>()
            for (range in farmMap.chunkRange(seedRanges)) {
                nextRanges.add(farmMap.map(range.first)..farmMap.map(range.last))
            }
            seedRanges = nextRanges
        }
        return seedRanges.minOf { it.first }
    }
}


class FarmMap(rawRanges: List<String>) {
    private val farmRanges = rawRanges.map { FarmRange(it.extractLongs()) }

    /**
     * Maps a single source value to a destination value.
     */
    fun map(value: Long): Long {
        for (farmRange in farmRanges) {
            if (value in farmRange.srcRange) {
                return value - farmRange.srcStart + farmRange.destStart
            }
        }
        return value
    }

    /**
     * Chunks an input range into smaller ranges that align with the farm ranges.
     */
    fun chunkRange(initialRanges: List<LongRange>): MutableList<LongRange> {
        val chunks = mutableListOf<LongRange>()
        val leftovers = mutableListOf<LongRange>()
        leftovers.addAll(initialRanges)
        while (leftovers.isNotEmpty()) {
            val chunksSize = chunks.size
            val range = leftovers.removeFirst()
            for (farmRange in farmRanges) {
                // Find areas of overlap and break ranges down into chunks.
                if (range.first in farmRange.srcRange || range.last in farmRange.srcRange) {
                    val left = range.first until farmRange.srcRange.first
                    val right = (farmRange.srcRange.last + 1)..range.last
                    val intersectLeft = farmRange.srcRange.first.coerceAtLeast(range.first)
                    val intersectRight = farmRange.srcRange.last.coerceAtMost(range.last)
                    if (!left.isEmpty()) {
                        leftovers.add(left)
                    }
                    chunks.add(intersectLeft..intersectRight)
                    if (!right.isEmpty()) {
                        leftovers.add(right)
                    }
                    break
                }
            }
            // If no new chunks were added, range is outside of all farm ranges and is a simple 1-to-1 mapping.
            if (chunks.size == chunksSize) {
                chunks.add(range)
            }
        }
        return chunks
    }

}

/**
 * Class representing a single range in a farm map.
 */
class FarmRange(values: List<Long>) {
    val destStart = values[0]
    val srcStart = values[1]
    val srcRange = srcStart until (srcStart + values[2])
}