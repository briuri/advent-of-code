package buri.aoc.common.registers

/**
 * Computer for IntCode problems
 * (y19d02)
 *
 * @author Brian Uri!
 */
class Computer(private val instructions: List<Int>) {

    /**
     * Runs the IntCode
     */
    fun run(noun: Int, verb: Int, debug: Boolean = false): Int {
        if (debug) {
            println("Computer running with [1]=$noun [2]=$verb")
        }
        val code = instructions.toMutableList()
        code[1] = noun
        code[2] = verb

        var ip = 0
        while (code[ip] != 99) {
            val in1 = code[ip + 1]
            val in2 = code[ip + 2]
            val out = code[ip + 3]
            if (debug) {
                print("ip=$ip (${code[ip]},$in1,$in2,$out) | ")
                when (code[ip]) {
                    1 -> print("[$out] = [$in1] + [$in2] ► ${code[in1]} + ${code[in2]} ► ${code[in1] + code[in2]}")
                    2 -> print("[$out] = [$in1] * [$in2] ► ${code[in1]} * ${code[in2]} ► ${code[in1] * code[in2]}")
                    else -> continue
                }
                println()
            }
            code[out] = when (code[ip]) {
                1 -> code[in1] + code[in2]
                2 -> code[in1] * code[in2]
                else -> continue
            }
            ip += 4
        }
        return code[0]
    }
}