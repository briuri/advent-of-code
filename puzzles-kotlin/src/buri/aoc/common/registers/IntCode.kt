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
    fun run(noun: Int, verb: Int): Int {
        val code = instructions.toMutableList()
        code[1] = noun
        code[2] = verb

        var pointer = 0
        while (code[pointer] != 99) {
            val in1 = code[pointer + 1]
            val in2 = code[pointer + 2]
            val out = code[pointer + 3]
            code[out] = when (code[pointer]) {
                1 -> code[in1] + code[in2]
                2 -> code[in1] * code[in2]
                else -> continue
            }
            pointer += 4
        }
        return code[0]
    }
}