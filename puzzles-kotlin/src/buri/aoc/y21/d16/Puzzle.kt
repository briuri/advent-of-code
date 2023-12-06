package buri.aoc.y21.d16

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.product
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(31, 1)
        assertRun(925, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(342997120375, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val rawPacket = StringBuilder()
        for (hex in input[0]) {
            val binary = hex.toString().toInt(16).toString(2).padStart(4, '0')
            rawPacket.append(binary)
        }
        val packet = Packet(rawPacket.toString(), 0)
        return if (part.isOne()) packet.versionSum() else packet.evaluate()
    }
}

class Packet(private val data: String, start: Int) {
    private val version = data.substring(start, start + 3).toInt(2)
    private val type = data.substring(start + 3, start + 6).toInt(2)
    var value: Long? = null
    private val subpackets = mutableListOf<Packet>()
    private val length: Int

    init {
        var i = start + 6
        // Unpack value or subpackets.
        if (type == 4) {
            val literal = StringBuilder()
            do {
                literal.append(data.substring(i + 1, i + 5))
                i += 5
            } while (data[i - 5] == '1')
            value = literal.toString().toLong(2)
        } else {
            if (data[i] == '0') {
                val sublength = data.substring(i + 1, i + 16).toInt(2)
                i += 16
                val packetStart = i
                while (i < packetStart + sublength) {
                    i = addSubpacket(data, i)
                }
            } else {
                val numPackets = data.substring(i + 1, i + 12).toInt(2)
                i += 12
                repeat(numPackets) {
                    i = addSubpacket(data, i)
                }
            }
        }
        length = i - start
    }

    /**
     * Adds all the versions together.
     */
    fun versionSum(): Long = version.toLong() + subpackets.sumOf { it.versionSum() }

    /**
     * Performs all operations
     */
    fun evaluate(): Long {
        return when (type) {
            // Sum
            0 -> subpackets.sumOf { it.evaluate() }
            // Product
            1 -> {
                subpackets.map { it.evaluate() }.product()
            }
            // Min
            2 -> subpackets.minOf { it.evaluate() }
            // Max
            3 -> subpackets.maxOf { it.evaluate() }
            // Literal
            4 -> value!!
            // Greater Than
            5 -> if (subpackets[0].evaluate() > subpackets[1].evaluate()) 1 else 0
            // Less Than
            6 -> if (subpackets[0].evaluate() < subpackets[1].evaluate()) 1 else 0
            // Equal To
            else -> if (subpackets[0].evaluate() == subpackets[1].evaluate()) 1 else 0
        }
    }

    /**
     * Adds a subpacket then returns the index of the next packet.
     */
    private fun addSubpacket(data: String, i: Int): Int {
        val subpacket = Packet(data, i)
        subpackets.add(subpacket)
        return i + subpacket.length
    }
}