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
        setMemory(1L, 0, noun)
        setMemory(2L, 0, verb)
    }

    /**
     * Runs the IntCode. May suspend temporarily if it needs input.
     */
    fun run(): Long {
        while (!halted) {
            val c = Command(memory[ip].toInt())
            val p = memory.subList(ip + 1, ip + 1 + c.numParams)
            debug(memory, ip, c, p)
            ip += c.numParams + 1
            when (c.opcode) {
                ADD -> setMemory(p[2], c.mode2, resolve(p[0], c.mode0) + resolve(p[1], c.mode1))
                MUL -> setMemory(p[2], c.mode2, resolve(p[0], c.mode0) * resolve(p[1], c.mode1))
                IN -> {
                    if (inputs.isEmpty()) {
                        ip -= c.numParams + 1
                        break
                    } else {
                        setMemory(p[0], c.mode0, inputs.removeFirst())
                    }
                }
                OUT -> outputs.add(resolve(p[0], c.mode0))
                JIT -> if (resolve(p[0], c.mode0) != 0L) {
                    ip = resolve(p[1], c.mode1).toInt()
                }
                JIF -> if (resolve(p[0], c.mode0) == 0L) {
                    ip = resolve(p[1], c.mode1).toInt()
                }
                LT -> setMemory(p[2], c.mode2, if (resolve(p[0], c.mode0) < resolve(p[1], c.mode1)) 1 else 0)
                EQ -> setMemory(p[2], c.mode2, if (resolve(p[0], c.mode0) == resolve(p[1], c.mode1)) 1 else 0)
                RBO -> rb += resolve(p[0], c.mode0).toInt()
            }
        }
        return memory[0]
    }

    /**
     * Prints some output if debug mode is enabled.
     */
    private fun debug(code: MutableList<Long>, ip: Int, c: Command, p: List<Long>) {
        if (debug) {
            print("ip=$ip c=(${code[ip]},${p.joinToString(",")}) ► ")
            val p0 = debugResolve(p[0], c.mode0)
            val p0Resolved = debugResolve(p[0], c.mode0, true)
            val p1 = if (c.numParams > 1) {
                debugResolve(p[1], c.mode1)
            } else {
                "?"
            }
            val p1Resolved = if (c.numParams > 1) {
                debugResolve(p[1], c.mode1, true)
            } else {
                "?"
            }
            val p2 = if (c.numParams > 2) {
                debugResolve(p[2], c.mode2)
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
    private fun resolve(param: Long, mode: Int): Long {
        return when (mode) {
            0 -> getMemory(param)
            1 -> param
            else -> getMemory(rb + param)
        }
    }

    /**
     * Creates debug output for an address or value, depending on the mode.
     */
    private fun debugResolve(param: Long, mode: Int, resolveAddress: Boolean = false): String {
        return when (mode) {
            0 -> {
                if (resolveAddress) {
                    "${resolve(param, mode)}"
                } else {
                    "[$param]"
                }
            }
            1 -> {
                "$param"
            }
            else -> {
                if (resolveAddress) {
                    "${resolve(param, mode)}"
                } else {
                    "[rb + $param]"
                }
            }
        }
    }

    /**
     * Gets a value.
     */
    private fun getMemory(address: Long): Long {
        addMemory(address.toInt())
        return memory[address.toInt()]
    }

    /**
     * Sets a value.
     */
    private fun setMemory(address: Long, mode: Int, value: Long) {
        if (mode == 0) {
            addMemory(address.toInt())
            memory[address.toInt()] = value
        }
        else if (mode == 2) {
            addMemory(rb + address.toInt())
            memory[rb + address.toInt()] = value
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

class Command(rawCommand: Int) {
    val opcode = rawCommand % 100
    val mode0: Int
    val mode1: Int
    val mode2: Int
    val numParams: Int
        get() = when (opcode) {
            IN, OUT, RBO -> 1
            JIT, JIF -> 2
            else -> 3
        }

    init {
        val fullCommand = rawCommand.toString().padStart(5, '0')
        mode0 = fullCommand[2].digitToInt()
        mode1 = fullCommand[1].digitToInt()
        mode2 = fullCommand[0].digitToInt()
    }
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