package buri.aoc.common.registers

/**
 * Computer for IntCode problems
 * (y19d02, y19d05, y19d07)
 *
 * @author Brian Uri!
 */
class Computer(private val instructions: List<Int>, private val debug: Boolean = false) {
    val inputs = mutableListOf<Int>()
    val outputs = mutableListOf<Int>()

    private var ip = 0
    private var code = mutableListOf<Int>()
    val halted
        get() = (code[ip] == 99)

    init {
        reset()
    }

    /**
     * Starts the program over.
     */
    fun reset() {
        ip = 0
        code = instructions.toMutableList()
        outputs.clear()
    }

    /**
     * Sets the noun and verb positions.
     */
    fun setNounVerb(noun: Int, verb: Int) {
        code[1] = noun
        code[2] = verb
    }

    /**
     * Runs the IntCode. May suspend temporarily if it needs input.
     */
    fun run(): Int {
        while (code[ip] != 99) {
            val c = Command(code[ip])
            val p = code.subList(ip + 1, ip + 1 + c.numParams)
            debug(code, ip, c, p)
            ip += c.numParams + 1
            when (c.opcode) {
                ADD -> code[p[2]] = code.resolve(p[0], c.mode0) + code.resolve(p[1], c.mode1)
                MUL -> code[p[2]] = code.resolve(p[0], c.mode0) * code.resolve(p[1], c.mode1)
                IN -> {
                    if (inputs.isEmpty()) {
                        ip -= c.numParams + 1
                        break
                    } else {
                        code[p[0]] = inputs.removeFirst()
                    }
                }
                OUT -> outputs.add(code.resolve(p[0], c.mode0))
                JIT -> if (code.resolve(p[0], c.mode0) != 0) {
                    ip = code.resolve(p[1], c.mode1)
                }
                JIF -> if (code.resolve(p[0], c.mode0) == 0) {
                    ip = code.resolve(p[1], c.mode1)
                }
                LT -> code[p[2]] = if (code.resolve(p[0], c.mode0) < code.resolve(p[1], c.mode1)) 1 else 0
                EQ -> code[p[2]] = if (code.resolve(p[0], c.mode0) == code.resolve(p[1], c.mode1)) 1 else 0
            }
        }
        return code[0]
    }

    /**
     * Prints some output if debug mode is enabled.
     */
    private fun debug(code: MutableList<Int>, ip: Int, c: Command, p: List<Int>) {
        if (debug) {
            print("ip=$ip c=(${code[ip]},${p.joinToString(",")}) ► ")
            val p0 = code.debugResolve(p[0], c.mode0)
            val p0Resolved = code.debugResolve(p[0], c.mode0, true)
            val p1 = if (c.numParams > 1) {
                code.debugResolve(p[1], c.mode1)
            } else {
                "?"
            }
            val p1Resolved = if (c.numParams > 1) {
                code.debugResolve(p[1], c.mode1, true)
            } else {
                "?"
            }
            val p2 = if (c.numParams > 2) {
                code.debugResolve(p[2], c.mode2)
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
            }
            println()
        }
    }

    /**
     * Returns a value or the value at that address, depending on the mode.
     */
    private fun MutableList<Int>.resolve(param: Int, mode: Int): Int = if (mode == 0) this[param] else param

    /**
     * Creates debug output for an address or value, depending on the mode.
     */
    private fun MutableList<Int>.debugResolve(param: Int, mode: Int, resolveAddress: Boolean = false): String {
        return if (mode == 0) {
            if (resolveAddress) {
                "${this[param]}"
            } else {
                "[$param]"
            }
        } else {
            "$param"
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
            IN, OUT -> 1
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