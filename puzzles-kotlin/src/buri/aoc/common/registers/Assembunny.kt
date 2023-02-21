package buri.aoc.common.registers

/**
 * Interpreter code for y16 Assembunny puzzles
 * (y16d12, y16d23, y16d25)
 *
 * @author Brian Uri!
 */
class Assembunny(input: List<String>) {
    private val instructions = input.toMutableList()
    private val registers = mutableMapOf<String, Long>("a" to 0L, "b" to 0L, "c" to 0L, "d" to 0L)

    /**
     * Executes an assembunny program
     */
    fun run() {
        var pointer = 0
        while (pointer in instructions.indices) {
            val command = instructions[pointer].split(" ")
            if (command[0] == "cpy") {
                // y16d23: Skip if command has been toggled and is now invalid
                if (command[2].toIntOrNull() == null) {
                    registers[command[2]] = resolve(command[1])
                }
                pointer++
            } else if (command[0] == "inc") {
                registers[command[1]] = registers[command[1]]!! + 1
                pointer++
            } else if (command[0] == "dec") {
                registers[command[1]] = registers[command[1]]!! - 1
                pointer++
            } else if (command[0] == "jnz") {
                val offset = if (resolve(command[1]) == 0L) 1 else resolve(command[2]).toInt()
                pointer += offset
            }
            // y16d23: New tgl command
            else if (command[0] == "tgl") {
                val address = (pointer + resolve(command[1])).toInt()
                if (address in instructions.indices) {
                    val instruction = instructions[address].split(" ")
                    if (instruction.size == 2) {
                        val change = if (instruction[0] == "inc") "dec" else "inc"
                        instructions[address] = "$change ${instruction[1]}"
                    } else if (instruction.size == 3) {
                        val change = if (instruction[0] == "jnz") "cpy" else "jnz"
                        instructions[address] = "$change ${instruction[1]} ${instruction[2]}"
                    }
                }
                pointer++
            }
            // y16d23: New mul command
            else if (command[0] == "mul") {
                registers[command[3]] = resolve(command[1]) * resolve(command[2])
                pointer++
            }
            // y16d25: New out command
            else if (command[0] == "out") {
                println(resolve(command[1]))
            }
        }
    }

    /**
     * Converts an instruction token into a value from a register or a plain numeric value.
     */
    private fun resolve(addressOrValue: String): Long {
        return if (addressOrValue.toIntOrNull() == null) get(addressOrValue) else addressOrValue.toLong()
    }

    /**
     * Enables array-like access to the registers
     */
    operator fun get(name: String): Long {
        return registers[name]!!
    }

    /**
     * Enables array-like access to the registers
     */
    operator fun set(name: String, value: Long) {
        registers[name] = value
    }
}