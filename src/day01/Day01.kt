package day01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach { string ->
            // first
            val startChar = string.find { it.isDigit() }
            val startString = startChar?.let { it1 -> string.substringBefore(it1) } ?: string
            val numberAppearancesFirst = Numbers.entries.filter { startString.contains(it.name, ignoreCase = true) }
            val first = numberAppearancesFirst.minByOrNull { startString.indexOf(it.name) }?.number ?: startChar

            // last
            val stringReverse = string.reversed()
            val endChar = stringReverse.find { it.isDigit() }
            val endString = endChar?.let { it1 -> stringReverse.substringBefore(it1) } ?: stringReverse
            val endStringReverse = endString.reversed()
            val numberAppearancesLast = Numbers.entries.filter { endStringReverse.contains(it.name, ignoreCase = true) }
            val last = numberAppearancesLast.maxByOrNull { endStringReverse.indexOf(it.name) }?.number ?: endChar

            total += "$first$last".toInt()
        }

        return total
    }

    val testInput = readInput("day01/Day01_test")
    "Test ${part1(testInput).toString().println()}"

    val input = readInput("day01/Day01")
    "Final ${part1(input).toString().println()}"
}

enum class Numbers(val number: Int) {
    one(1), two(2), three(3), four(4), five(5), six(6), seven(7), eight(8), nine(9)
}
