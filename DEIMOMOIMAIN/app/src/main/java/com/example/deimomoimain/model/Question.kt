package com.example.deimomoimain.model

import kotlin.random.Random

enum class Operator(val symbol: String) {
    ADD("+"), SUBTRACT("-"), MULTIPLY("×"), DIVIDE("÷");
}

data class Question(
    val operand1: Int,
    val operand2: Int,
    val operator: Operator
) {
    val answer: Int get() = when (operator) {
        Operator.ADD -> operand1 + operand2
        Operator.SUBTRACT -> operand1 - operand2
        Operator.MULTIPLY -> operand1 * operand2
        // 2. Add case for DIVIDE in the answer calculation
        Operator.DIVIDE -> {
            if (operand2 == 0) throw ArithmeticException("Division by zero") // Should be prevented by generation logic
            operand1 / operand2
        }
    }

    fun displayText(): String = "$operand1 ${operator.symbol} $operand2"

    companion object {
        fun random(): Question {
            // Choose operator first
            val op = Operator.values().random()
            return when (op) {
                Operator.ADD -> {
                    val a = Random.nextInt(1, 10)
                    val b = Random.nextInt(1, 10)
                    Question(a, b, op)
                }
                Operator.MULTIPLY -> {
                    val a = Random.nextInt(1, 10)
                    val b = Random.nextInt(1, 10)
                    Question(a, b, op)
                }
                Operator.SUBTRACT -> {
                    // Ensure non-negative result: operand1 >= operand2
                    val a = Random.nextInt(1, 10)
                    val b = Random.nextInt(1, a + 1) // b in 1..a
                    Question(a, b, op)
                }
                Operator.DIVIDE -> {
                    // Ensure operand2 is not 0 and that operand1 is a multiple of operand2
                    // to get a whole number answer.
                    val operand2 = Random.nextInt(1, 10) // Divisor from 1 to 9
                    val multiplier = Random.nextInt(1, 10) // Multiplier from 1 to 9 (keeps answers relatively small)
                    val operand1 = operand2 * multiplier   // Ensures operand1 is perfectly divisible by operand2

                    // This will result in answers from 1 up to 9 (e.g., 9/1=9, 81/9=9)
                    // Or answers up to the max multiplier if operand2 is 1 (e.g. 9*1=9, 9/1=9)
                    // Example: (operand2=3, multiplier=5) -> question 15 ÷ 3, answer 5
                    // Example: (operand2=1, multiplier=7) -> question 7 ÷ 1, answer 7
                    // Example: (operand2=8, multiplier=8) -> question 64 ÷ 8, answer 8
                    Question(operand1, operand2, op)
                }
            }
        }
    }
}
