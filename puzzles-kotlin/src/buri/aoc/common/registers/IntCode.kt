package buri.aoc.common.registers

/**
 * Computer for IntCode problems
 * (y19d02, y19d05, y19d07, y19d09, y19d11, y19d13, y9d15, y19d17, y19d19)
 *
 * @author Brian Uri!
 */
class Computer(private val instructions: List<Long>, private val debug: Boolean = false) {
    private val inputs = mutableListOf<Long>()
    private val outputs = mutableListOf<Long>()
    val halted
        get() = (memory[ip] == 99L)

    private var ip = 0
    private var rb = 0
    private var memory = mutableListOf<Long>()

    init {
        reset()
    }

    /**
     * Restarts this computer.
     */
    fun reset() {
        ip = 0
        rb = 0
        memory = instructions.toMutableList()
        outputs.clear()
    }

    /**
     * External call to set a value at specific address.
     */
    fun set(address: Int, value: Long) {
        set(Param(address.toLong(), 0), value)
    }

    /**
     * Adds a number to the input
     */
    fun input(value: Long) {
        inputs.add(value)
    }

    /**
     * Converts a string to ASCII and adds it as input.
     */
    fun input(string: String) {
        for (value in string) {
            input(value.code.toLong())
        }
    }

    /**
     * Returns true if there are outputs available.
     */
    fun hasOutput(): Boolean = outputs.isNotEmpty()

    /**
     * Removes outputs in sequential order.
     */
    fun output(reverse: Boolean = false): Long = if (reverse) outputs.removeLast() else outputs.removeFirst()

    /**
     * Runs the IntCode. May suspend temporarily if it needs input.
     */
    fun run(): Long {
        while (!halted) {
            val c = Command(memory[ip].toInt(), memory.subList(ip + 1, (ip + 4).coerceAtMost(memory.lastIndex)))
            if (debug) {
                println(toString())
            }
            ip += c.numParams + 1
            val isWaiting = c.run()
            if (isWaiting) {
                break
            }
        }
        return memory[0]
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

    inner class Command(rawCommand: Int, rawParams: List<Long>) {
        private val opcode = rawCommand % 100
        private val params = mutableListOf<Param>()
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

        /**
         * Executes this command. Returns true if we're paused for input waiting.
         */
        fun run(): Boolean {
            when (opcode) {
                ADD -> set(params[2], params[0].resolve() + params[1].resolve())
                MUL -> set(params[2], params[0].resolve() * params[1].resolve())
                IN -> {
                    if (inputs.isEmpty()) {
                        ip -= numParams + 1
                        return true
                    } else {
                        set(params[0], inputs.removeFirst())
                    }
                }
                OUT -> outputs.add(params[0].resolve())
                JIT -> if (params[0].resolve() != 0L) {
                    ip = params[1].resolve().toInt()
                }
                JIF -> if (params[0].resolve() == 0L) {
                    ip = params[1].resolve().toInt()
                }
                LT -> set(params[2], if (params[0].resolve() < params[1].resolve()) 1 else 0)
                EQ -> set(params[2], if (params[0].resolve() == params[1].resolve()) 1 else 0)
                RBO -> rb += params[0].resolve().toInt()
            }
            return false
        }

        /**
         * Prints some output if debug mode is enabled.
         */
        override fun toString(): String {
            val output = StringBuilder("ip=$ip c=(${memory[ip]},${params.joinToString(",")}) ► ")
            val p0 = debugResolve(params[0])
            val p0Resolved = debugResolve(params[0], true)
            val p1 = if (numParams > 1) {
                debugResolve(params[1])
            } else {
                "?"
            }
            val p1Resolved = if (numParams > 1) {
                debugResolve(params[1], true)
            } else {
                "?"
            }
            val p2 = if (numParams > 2) {
                debugResolve(params[2])
            } else {
                "?"
            }
            when (opcode) {
                ADD, MUL -> {
                    val op = if (opcode == ADD) "+" else "*"
                    output.append("$p2 = $p0 $op $p1 ► $p0Resolved $op $p1Resolved")
                }
                IN -> {
                    output.append("$p0 = ${inputs[0]}")
                }
                OUT -> {
                    output.append("out = $p0 ► out = $p0Resolved")
                }
                JIT, JIF -> {
                    val op = if (opcode == JIT) "!=" else "=="
                    output.append("if ($p0 $op 0) ip = $p1 ► if ($p0Resolved $op 0) ip = $p1Resolved")
                }
                LT, EQ -> {
                    val op = if (opcode == LT) "<" else "=="
                    output.append("$p2 = if ($p0 $op $p1) 1 else 0 ► if ($p0Resolved $op $p1Resolved) 1 else 0")
                }
                RBO -> {
                    output.append("rb += $p0 ► $p0Resolved")
                }
            }
            output.append("\n")
            return (output.toString())
        }

        /**
         * Creates debug output for an address or value, depending on the mode.
         */
        private fun debugResolve(param: Param, resolveAddress: Boolean = false): String {
            return when (param.mode) {
                0 -> {
                    if (resolveAddress) {
                        "${param.resolve()}"
                    } else {
                        "[$param.value]"
                    }
                }
                1 -> {
                    "$param"
                }
                else -> {
                    if (resolveAddress) {
                        "${param.resolve()}"
                    } else {
                        "[rb + $param]"
                    }
                }
            }
        }
    }

    inner class Param(val value: Long, val mode: Int) {

        /**
         * Returns a value or the value at that address, depending on the mode.
         */
        fun resolve(): Long {
            return when (mode) {
                0 -> get(asAddress())
                1 -> value
                else -> get(rb + asAddress())
            }
        }

        /**
         * Returns this param's value as an int address.
         */
        fun asAddress(): Int = value.toInt()

        override fun toString(): String = value.toString()
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