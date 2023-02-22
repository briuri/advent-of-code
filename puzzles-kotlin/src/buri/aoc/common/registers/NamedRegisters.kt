package buri.aoc.common.registers

/**
 * Code for various puzzles where values are stored in registers with String names.
 *
 * Used directly in:
 * (y15d23, y17d08, y17d18, y17d23)
 *
 * Used in Assembunny for:
 * (y16d12, y16d23, y16d25)
 *
 * @author Brian Uri!
 */
class NamedRegisters {
    private val registers = mutableMapOf<String, Long>()

    /**
     * Converts an instruction token into a value from a register or a plain numeric value.
     */
    fun resolve(addressOrValue: String): Long {
        return if (addressOrValue.toIntOrNull() == null) get(addressOrValue) else addressOrValue.toLong()
    }

    /**
     * Returns the max value in any register.
     */
    fun max(): Long {
        return registers.values.max()
    }

    /**
     * Adds a value to the current value in a register.
     */
    fun add(name: String, change: Long) {
        registers[name] = get(name) + change
    }

    /**
     * Subtracts a value to the current value in a register.
     */
    fun subtract(name: String, change: Long) {
        registers[name] = get(name) - change
    }

    /**
     * Multiplies the current value in a register by another.
     */
    fun multiply(name: String, change: Long) {
        registers[name] = get(name) * change
    }

    /**
     * Divides the current value in a register by another.
     */
    fun divide(name: String, change: Long) {
        registers[name] = get(name) / change
    }

    /**
     * Mods the current value in a register by another.
     */
    fun mod(name: String, change: Long) {
        registers[name] = get(name) % change
    }

    /**
     * Enables array-like access to the registers
     */
    operator fun get(name: String): Long {
        registers.putIfAbsent(name, 0L)
        return registers[name]!!
    }

    /**
     * Enables array-like access to the registers
     */
    operator fun set(name: String, value: Long) {
        registers[name] = value
    }
}