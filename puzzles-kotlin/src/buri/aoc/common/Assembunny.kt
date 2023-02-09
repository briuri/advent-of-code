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
    fun process(input: List<String>) {
        var pointer = 0
        while (pointer in input.indices) {
            val command = input[pointer].split(" ")
            if (command[0] == "cpy") {
                registers[command[2]] = getValue(registers, command[1])
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
                val offset = if (getValue(registers, command[1]) != 0L) {
                    command[2].toInt()
                }
                else {
                    1
                }
                pointer += offset
            }
        }
    }

    /**
     * Converts an instruction token into a value from a register or a plain numeric value.
     */
    private fun getValue(registers: MutableMap<String, Long>, addressOrValue: String): Long {
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