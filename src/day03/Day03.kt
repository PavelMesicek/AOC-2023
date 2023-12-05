package day03

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        var previousString = ""
        var currentString: String
        input.forEach { inputString ->
            currentString = inputString
            // current line

            val numbersInCurrentLine = currentString.findNumbersInText()
            val symbolsInCurrentLine = currentString.findSymbolsInText()
            val symbolsInPreviousLine = previousString.findSymbolsInText()
            val numbersInPreviousLine = previousString.findNumbersInText()

            numbersInCurrentLine.forEach { number ->
                if ((symbolsInCurrentLine.contains(number.firstIndex - 1) || symbolsInCurrentLine.contains(number.lastIndex + 1))
                        || symbolsInPreviousLine.any { it in (number.firstIndex - 1)..(number.lastIndex + 1) }
                ) {
                    val replacement = ".".repeat(number.value.toString().length)
                    currentString = currentString.replaceRange(number.firstIndex, number.lastIndex + 1, replacement)
                    total += number.value
                }
            }

            // previous line
            numbersInPreviousLine.forEach { number ->
                if (symbolsInCurrentLine.any { it in (number.firstIndex - 1)..(number.lastIndex + 1) }) {
                    total += number.value
                }
            }

            // convert to previous string
            previousString = currentString
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        var prePreviousString = ""
        var previousString = ""
        var currentString: String
        input.forEach { inputString ->
            // current line
            currentString = inputString
            val numbersInPrePreviousLine = prePreviousString.findNumbersInText()
            val numbersInPreviousLine = previousString.findNumbersInText()
            val numbersInCurrentLine = currentString.findNumbersInText()
            val gearsInPreviousLine = previousString.findGearSymbolsInText()
            val gearsInCurrentLine = currentString.findGearSymbolsInText()

            // current line numbers and gears
            numbersInCurrentLine.zipWithNext { a, b ->
                if (gearsInCurrentLine.contains(a.lastIndex + 1) && gearsInCurrentLine.contains(b.firstIndex - 1)) {
                    total += a.value * b.value
                }
            }

            // current line numbers and previous gears
            numbersInCurrentLine.zipWithNext { a, b ->
                if (gearsInPreviousLine.contains(a.lastIndex + 1) && gearsInPreviousLine.contains(b.firstIndex - 1)) {
                    total += a.value * b.value
                }
            }

            // current line gears and previous numbers
            numbersInPreviousLine.zipWithNext { a, b ->
                if (gearsInCurrentLine.contains(a.lastIndex + 1) && gearsInCurrentLine.contains(b.firstIndex - 1)) {
                    total += a.value * b.value
                }
            }

            // current line gears, numbers and previous numbers
            numbersInCurrentLine.forEach { currentNumber ->
                when {
                    gearsInCurrentLine.contains(currentNumber.firstIndex - 1) -> {
                        numbersInPreviousLine.forEach { previousNumber ->
                            val expandedRange = (previousNumber.firstIndex - 1).coerceAtLeast(0)..(previousNumber.lastIndex + 1)
                            if (expandedRange.contains(currentNumber.firstIndex - 1)) {
                                total += currentNumber.value * previousNumber.value
                            }
                        }
                    }

                    gearsInCurrentLine.contains(currentNumber.lastIndex + 1) -> {
                        numbersInPreviousLine.forEach { previousNumber ->
                            val expandedRange = (previousNumber.firstIndex - 1).coerceAtLeast(0)..(previousNumber.lastIndex + 1)
                            if (expandedRange.contains(currentNumber.lastIndex + 1)) {
                                total += currentNumber.value * previousNumber.value
                            }
                        }
                    }

                    else -> {}
                }
            }

            // current line numbers and previous numbers and gears
            numbersInPreviousLine.forEach { previousNumber ->
                when {
                    gearsInPreviousLine.contains(previousNumber.firstIndex - 1) -> {
                        numbersInCurrentLine.forEach { currentNumber ->
                            val expandedRange = (currentNumber.firstIndex - 1).coerceAtLeast(0)..(currentNumber.lastIndex + 1)
                            if (expandedRange.contains(previousNumber.firstIndex - 1)) {
                                total += currentNumber.value * previousNumber.value
                            }
                        }
                    }

                    gearsInPreviousLine.contains(previousNumber.lastIndex + 1) -> {
                        numbersInCurrentLine.forEach { currentNumber ->
                            val expandedRange = (currentNumber.firstIndex - 1).coerceAtLeast(0)..(currentNumber.lastIndex + 1)
                            if (expandedRange.contains(previousNumber.lastIndex + 1)) {
                                total += currentNumber.value * previousNumber.value
                            }
                        }
                    }

                    else -> {}
                }
            }

            // current numbers, previous gear, and pre previous numbers
            numbersInPrePreviousLine.forEach { prePreviousNumber ->
                gearsInPreviousLine.forEach { gearsIndex ->
                    numbersInCurrentLine.forEach { currentNumber ->
                        if ((prePreviousNumber.firstIndex - 1 <= gearsIndex && gearsIndex <= prePreviousNumber.lastIndex + 1) &&
                                (currentNumber.firstIndex - 1 <= gearsIndex && gearsIndex <= currentNumber.lastIndex + 1)) {
                            total += prePreviousNumber.value * currentNumber.value
                        }
                    }
                }
            }

            // convert to pre and previous string
            prePreviousString = previousString
            previousString = currentString
        }

        return total
    }


    val testInput = readInput("day03/Day03_test")
    "Test part1 ${part1(testInput).toString().println()}"
    "Test part2 ${part2(testInput).toString().println()}"

    val input = readInput("day03/Day03")
    "Final part1 ${part1(input).toString().println()}"
    "Final part2 ${part2(input).toString().println()}"
    // 75519888
}

fun String.findNumbersInText(): List<NumbersAndSymbols> =
        "\\d+".toRegex().findAll(this).map { NumbersAndSymbols(it.range, it.range.first, it.range.last, it.value.toInt()) }.toList()

fun String.findSymbolsInText(): List<Int> =
        "[^\\s\\.\\w]".toRegex().findAll(this).map { it.range.first }.toList()

fun String.findGearSymbolsInText(): List<Int> =
        "\\*".toRegex().findAll(this).map { it.range.first }.toList()

data class NumbersAndSymbols(
        val range: IntRange,
        val firstIndex: Int,
        val lastIndex: Int,
        val value: Int,
)