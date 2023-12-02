package day02

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0

        input.forEach { inputString ->
            val newString = inputString.substringAfter(":")
            val listStrings = newString.split(";")

            val parts = MutableList(listStrings.size) { false }

            listStrings.forEachIndexed { index, s ->
                var green = false
                var blue = false
                var red = false

                if (s.contains(Colours.red.name).not()) red = true
                if (s.contains(Colours.green.name).not()) green = true
                if (s.contains(Colours.blue.name).not()) blue = true

                val colourStrings = s.split(",")

                colourStrings.forEach { s1 ->
                    if (s1.contains(Colours.red.name) && s1.filter { it.isDigit() }.toInt() <= 12) red = true
                    if (s1.contains(Colours.green.name) && s1.filter { it.isDigit() }.toInt() <= 13) green = true
                    if (s1.contains(Colours.blue.name) && s1.filter { it.isDigit() }.toInt() <= 14) blue = true
                }

                if (red && green && blue) {
                    parts[index] = true
                }
            }

            if (parts.all { it }) {
                val startChar = inputString.substringBefore(":")
                total += startChar.filter { it.isDigit() }.toInt()
            }
        }

        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        input.forEach { inputString ->
            val newString = inputString.substringAfter(":")
            val listStrings = newString.split(",", ";")

            val redListString = listStrings.filter { it.contains(Colours.red.name) }
            val redListInt = redListString.map { s -> s.filter { it.isDigit() }.toInt() }
            val red = redListInt.max()

            val greenListString = listStrings.filter { it.contains(Colours.green.name) }
            val greenListInt = greenListString.map { s -> s.filter { it.isDigit() }.toInt() }
            val green = greenListInt.max()

            val blueListString = listStrings.filter { it.contains(Colours.blue.name) }
            val blueListInt = blueListString.map { s -> s.filter { it.isDigit() }.toInt() }
            val blue = blueListInt.max()


            total += (red * green * blue)
        }

        return total
    }


    val testInput = readInput("day02/Day02_test")
    "Test part1 ${part1(testInput).toString().println()}"
    "Test part2 ${part2(testInput).toString().println()}"

    val input = readInput("day02/Day02")
    "Final part1 ${part1(input).toString().println()}"
    "Final part2 ${part2(input).toString().println()}"
}

enum class Colours {
    red, blue, green
}
