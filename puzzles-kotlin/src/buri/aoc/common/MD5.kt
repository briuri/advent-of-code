package buri.aoc.common

import java.security.MessageDigest

/**
 * Utility class for MD5 hashes with a certain number of leading zeroes.
 *
 * @author Brian Uri!
 */
class MD5 {
    private val digest = MessageDigest.getInstance("MD5")

    /**
     * Creates an MD5 hash of some string. Tries to save time by not doing the hex conversion when
     * the leading zeroes aren't present. Returns empty string if the hash doesn't have the right
     * number of leading zeroes.
     */
    fun getHashWithLeadingZeroes(input: String, leadingZeroes: Int = 0): String {
        val bytes = digest.digest(input.toByteArray())
        // Checks for even number of zeroes.
        for (i in 0 until leadingZeroes / 2) {
            if (bytes[i] != 0.toByte()) {
                return ""
            }
        }
        // Handle the last odd zero.
        if (leadingZeroes % 2 != 0) {
            val index = leadingZeroes / 2
            if (bytes[index].toInt() !in 0..15) {
                return ""
            }
        }
        return toHex(bytes)
    }

    /**
     * Converts a byte array into a lowercase hex string.
     */
    private fun toHex(bytes: ByteArray): String {
        val builder = StringBuilder()
        for (b in bytes) {
            builder.append(Character.forDigit(b.toInt() shr 4 and 0xF, 16))
            builder.append(Character.forDigit(b.toInt() and 0xF, 16))
        }
        return builder.toString()
    }
}