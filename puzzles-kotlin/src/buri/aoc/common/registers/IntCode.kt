package buri.aoc.common.registers

/**
 * Computer for IntCode problems
 * (y19d02, y19d05, y19d07, y19d09)
 *
 * @author Brian Uri!
 */
class Computer(private val instructions: List<Int>, private val debug: Boolean = false) {
    val inputs = mutableListOf<Long>()
    val outputs = mutableListOf<Long>()

    private var ip = 0
    private var rb = 0
    private var memory = mutableListOf<Long>()
    val halted
        get() = (memory[ip] == 99L)

    init {
        reset()
    }

    /**
     * Starts the program over.
     */
    fun reset() {
        ip = 0
        rb = 0
        memory = instructions.map { it.toLong() }.toMutableList()
        outputs.clear()
    }

    /**
     * Sets the noun and verb positions.
     */
    fun setNounVerb(noun: Long, verb: Long) {
        set(Param(1, 0), noun)
        set(Param(2, 0), verb)
    }

    /**
     * Runs the IntCode. May suspend temporarily if it needs input.
     */
    fun run(): Long {
        while (!halted) {
            val maxParams = (ip + 4).coerceAtMost(memory.lastIndex)
            val c = Command(memory[ip].toInt(), memory.subList(ip + 1, maxParams))
            debug(c)
            ip += c.numParams + 1
            when (c.opcode) {
                ADD -> set(c.params[2], resolve(c.params[0]) + resolve(c.params[1]))
                MUL -> set(c.params[2], resolve(c.params[0]) * resolve(c.params[1]))
                IN -> {
                    if (inputs.isEmpty()) {
                        ip -= c.numParams + 1
                        break
                    } else {
                        set(c.params[0], inputs.removeFirst())
                    }
                }
                OUT -> outputs.add(resolve(c.params[0]))
                JIT -> if (resolve(c.params[0]) != 0L) {
                    ip = resolve(c.params[1]).toInt()
                }
                JIF -> if (resolve(c.params[0]) == 0L) {
                    ip = resolve(c.params[1]).toInt()
                }
                LT -> set(c.params[2], if (resolve(c.params[0]) < resolve(c.params[1])) 1 else 0)
                EQ -> set(c.params[2], if (resolve(c.params[0]) == resolve(c.params[1])) 1 else 0)
                RBO -> rb += resolve(c.params[0]).toInt()
            }
        }
        return memory[0]
    }

    /**
     * Prints some output if debug mode is enabled.
     */
    private fun debug(c: Command) {
        if (debug) {
            print("ip=$ip c=(${memory[ip]},${c.params.joinToString(",")}) ► ")
            val p0 = debugResolve(c.params[0])
            val p0Resolved = debugResolve(c.params[0], true)
            val p1 = if (c.numParams > 1) {
                debugResolve(c.params[1])
            } else {
                "?"
            }
            val p1Resolved = if (c.numParams > 1) {
                debugResolve(c.params[1], true)
            } else {
                "?"
            }
            val p2 = if (c.numParams > 2) {
                debugResolve(c.params[2])
            } else {
                "?"
            }
            when (c.opcode) {
                ADD, MUL -> {
                    val op = if (c.opcode == ADD) "+" else "*"
                    print("$p2 = $p0 $op $p1 ► $p0Resolved $op $p1Resolved")
                }
                IN -> {
                    print("$p0 = ${inputs[0]}")
                }
                OUT -> {
                    print("out = $p0 ► out = $p0Resolved")
                }
                JIT, JIF -> {
                    val op = if (c.opcode == JIT) "!=" else "=="
                    print("if ($p0 $op 0) ip = $p1 ► if ($p0Resolved $op 0) ip = $p1Resolved")
                }
                LT, EQ -> {
                    val op = if (c.opcode == LT) "<" else "=="
                    print("$p2 = if ($p0 $op $p1) 1 else 0 ► if ($p0Resolved $op $p1Resolved) 1 else 0")
                }
                RBO -> {
                    print("rb += $p0 ► $p0Resolved")
                }
            }
            println()
        }
    }

    /**
     * Returns a value or the value at that address, depending on the mode.
     */
    private fun resolve(param: Param): Long {
        return when (param.mode) {
            0 -> get(param.asAddress())
            1 -> param.value
            else -> get(rb + param.asAddress())
        }
    }

    /**
     * Creates debug output for an address or value, depending on the mode.
     */
    private fun debugResolve(param: Param, resolveAddress: Boolean = false): String {
        return when (param.mode) {
            0 -> {
                if (resolveAddress) {
                    "${resolve(param)}"
                } else {
                    "[$param.value]"
                }
            }
            1 -> {
                "$param"
            }
            else -> {
                if (resolveAddress) {
                    "${resolve(param)}"
                } else {
                    "[rb + $param]"
                }
            }
        }
    }

    /**
     * Gets a value.
     */
    private fun get(address: Int): Long {
        addMemory(address)
        return memory[address]
    }

    /**
     * Sets a value.
     */
    private fun set(param: Param, value: Long) {
        when (param.mode) {
            0 -> {
                addMemory(param.asAddress())
                memory[param.asAddress()] = value
            }
            1 -> {
                throw IllegalArgumentException("Cannot store a value in a value.")
            }
            else -> {
                val address = rb + param.asAddress()
                addMemory(address)
                memory[address] = value
            }
        }
    }

    /**
     * Adds memory to the end of the memory area.
     */
    private fun addMemory(address: Int) {
        while (address >= memory.size) {
            memory.add(0L)
        }
    }
}

class Command(rawCommand: Int, rawParams: List<Long>) {
    val opcode = rawCommand % 100
    val params = mutableListOf<Param>()
    val numParams: Int
        get() = when (opcode) {
            IN, OUT, RBO -> 1
            JIT, JIF -> 2
            else -> 3
        }

    init {
        val fullCommand = rawCommand.toString().padStart(5, '0')
        for (i in 0 until numParams) {
            params.add(Param(rawParams[i], fullCommand[2 - i].digitToInt()))
        }
    }
}

class Param(val value: Long, val mode: Int) {

    /**
     * Returns this param's value as an int address.
     */
    fun asAddress(): Int = value.toInt()

    override fun toString(): String = value.toString()
}

const val ADD = 1
const val MUL = 2
const val IN = 3
const val OUT = 4
const val JIT = 5
const val JIF = 6
const val LT = 7
const val EQ = 8
const val RBO = 9