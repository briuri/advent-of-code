package buri.aoc.common

/**
 * Interpreter code for y16 Assembunny puzzles
 * (y16d12, y16d23)
 */
class Assembunny {
    private val registers = mutableMapOf<String, Long>()

    init {
        registers["a"] = 0L
        registers["b"] = 0L
        registers["c"] = 0L
        registers["d"] = 0L
    }

    /**
     * Executes an assembunny program
     */
    fun process(input: MutableList<String>) {
        var pointer = 0
        while (pointer in input.indices) {
            val command = input[pointer].split(" ")
            if (command[0] == "cpy") {
                // y16d23: Skip if command has been toggled and is now invalid
                if (command[2].toIntOrNull() == null) {
                    registers[command[2]] = getValue(command[1])
                }
                pointer++
            }
            else if (command[0] == "inc") {
                registers[command[1]] = registers[command[1]]!! + 1
                pointer++
            }
            else if (command[0] == "dec") {
                registers[command[1]] = registers[command[1]]!! - 1
                pointer++
            }
            else if (command[0] == "jnz") {
                val offset = if (getValue(command[1]) != 0L) {
                    getValue(command[2]).toInt()
                }
                else {
                    1
                }
                pointer += offset
            }
            // y16d23: New tgl command
            else if (command[0] == "tgl") {
                val address = (pointer + getValue(command[1])).toInt()
                if (address in input.indices) {
                    val instruction = input[address].split(" ")
                    if (instruction.size == 2) {
                        val change = if (instruction[0] == "inc") "dec" else "inc"
                        input[address] = "$change " + instruction[1]
                    } else if (instruction.size == 3) {
                        val change = if (instruction[0] == "jnz") "cpy" else "jnz"
                        input[address] = "$change " + instruction[1] + " " + instruction[2]
                    }
                }
                pointer++
            }
            // y16d23: New mul command
            else if (command[0] == "mul") {
                registers[command[3]] = getValue(command[1]) * getValue(command[2])
                pointer++
            }
        }
    }

    /**
     * Converts an instruction token into a value from a register or a plain numeric value.
     */
    private fun getValue(addressOrValue: String): Long {
        return if (addressOrValue.toIntOrNull() == null) {
            registers[addressOrValue]!!
        }
        else {
            addressOrValue.toLong()
        }
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