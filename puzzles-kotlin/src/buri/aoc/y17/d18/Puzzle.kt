package buri.aoc.y17.d18

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.Part.TWO
import buri.aoc.common.registers.NamedRegisters
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(4, 1)
        assertRun(7071, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3, 2)
        assertRun(8001, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val programA = IORegisters(0L, input)
        if (part == ONE) {
            programA.run(part)
            return programA.getLastMessage()
        }
        val programB = IORegisters(1L, input)
        programA.sender = programB
        programB.sender = programA
        var isDeadlocked: Boolean
        do {
            programA.run(part)
            programB.run(part)
            isDeadlocked = programA.isReceiving && !programB.hasMessages()
                    && programB.isReceiving && !programA.hasMessages()
        } while (!isDeadlocked)
        return programB.sendCount
    }
}

class IORegisters(pStart: Long, private val instructions: List<String>) {
    private val registers = NamedRegisters()
    private val sentMessages = mutableListOf<Long>()

    var sender: IORegisters? = null
    var sendCount = 0
    var isReceiving = false
    private var pointer = 0

    init {
        registers["p"] = pStart
    }

    /**
     * Executes a program
     */
    fun run(part: Part) {
        isReceiving = false
        while (pointer in instructions.indices) {
            val command = instructions[pointer].split(" ")
            var offset = 1
            if (command[0] == "snd") {
                sentMessages.add(registers.resolve(command[1]))
                sendCount++
            } else if (command[0] == "set") {
                registers[command[1]] = registers.resolve(command[2])
            } else if (command[0] == "add") {
                registers.add(command[1], registers.resolve(command[2]))
            } else if (command[0] == "mul") {
                registers.multiply(command[1], registers.resolve(command[2]))
            } else if (command[0] == "mod") {
                registers.mod(command[1], registers.resolve(command[2]))
            } else if (command[0] == "rcv") {
                // In part ONE, quit the first time this command works.
                if (part == ONE && registers.resolve(command[1]) != 0L) {
                    break
                }
                if (part == TWO) {
                    if (!sender!!.hasMessages()) {
                        isReceiving = true
                        break
                    }
                    registers[command[1]] = sender!!.getNextMessage()
                }
            } else if (command[0] == "jgz" && registers.resolve(command[1]) > 0) {
                offset = registers.resolve(command[2]).toInt()
            }
            pointer += offset
        }
    }

    /**
     * Returns true if this register set has messages ready to send.
     */
    fun hasMessages(): Boolean {
        return sentMessages.isNotEmpty()
    }

    /**
     * Grabs a value from the outgoing message queue.
     */
    private fun getNextMessage(): Long {
        return sentMessages.removeFirst()
    }

    /**
     * Grabs a value from the outgoing message queue.
     */
    fun getLastMessage(): Long {
        return sentMessages.removeLast()
    }
}