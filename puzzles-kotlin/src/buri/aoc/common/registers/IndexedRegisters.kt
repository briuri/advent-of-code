package buri.aoc.common.registers

/**
 * Interpreter code for y18 assembly puzzles
 * (y18d16, y18d19, y18d21)
 *
 * @author Brian Uri!
 */
class IndexedRegisters {
    private val registers = NamedRegisters()

    /**
     * Runs a command using the provided input numbers.
     */
    fun run(code: String, inputs: List<String>) {
        val a = inputs[1]
        val b = inputs[2]
        val c = inputs[3]
        when (code) {
            "addr" -> registers[c] = registers[a] + registers[b]
            "addi" -> registers[c] = registers[a] + b.toLong()
            "mulr" -> registers[c] = registers[a] * registers[b]
            "muli" -> registers[c] = registers[a] * b.toLong()
            "banr" -> registers[c] = registers[a] and registers[b]
            "bani" -> registers[c] = registers[a] and b.toLong()
            "borr" -> registers[c] = registers[a] or registers[b]
            "bori" -> registers[c] = registers[a] or b.toLong()
            "setr" -> registers[c] = registers[a]
            "seti" -> registers[c] = a.toLong()
            "gtir" -> registers[c] = if (a.toLong() > registers[b]) 1 else 0
            "gtri" -> registers[c] = if (registers[a] > b.toLong()) 1 else 0
            "gtrr" -> registers[c] = if (registers[a] > registers[b]) 1 else 0
            "eqir" -> registers[c] = if (a.toLong() == registers[b]) 1 else 0
            "eqri" -> registers[c] = if (registers[a] == b.toLong()) 1 else 0
            "eqrr" -> registers[c] = if (registers[a] == registers[b]) 1 else 0
        }
    }

    /**
     * Enables array-like access to the registers
     */
    operator fun get(name: Int): Long {
        return registers[name.toString()]
    }

    /**
     * Enables array-like access to the registers
     */
    operator fun set(name: Int, value: Long) {
        registers[name.toString()] = value
    }

    override fun toString(): String {
        return registers.toString()
    }
}