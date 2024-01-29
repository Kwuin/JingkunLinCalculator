package com.example.jingkunlincalculator2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlin.math.exp
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)

        // Set up buttons and click listeners
        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSub,
            R.id.buttonMul, R.id.buttonDiv, R.id.buttonEqual, R.id.buttonClear, R.id.buttonSqrt,
            R.id.buttonDot
        )

        buttons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { onButtonClick(it) }
        }
    }


    private fun onButtonClick(view: View) {
        if (view is Button) {
            when (view.id) {
                R.id.buttonClear -> editText.text.clear()
                R.id.buttonEqual -> calculateResult()
                else -> editText.append(view.text)
            }
        }
    }


    private fun calculateResult() {
        try {
            // Simple expression evaluation
            val result = evaluateExpression(editText.text.toString())
            editText.setText(result.toString())
        } catch (e: Exception) {
            editText.setText(e.toString())
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val (numbers, operators) = extractNumbersAndOperators(expression)

        if (numbers.isEmpty()) throw ArithmeticException("No numbers")

        var result = numbers.first()

        var numIndex = 1

        for (operator in operators) {
            when (operator) {
                '+' -> result += numbers.getOrElse(numIndex++) { 0.0 }
                '-' -> result -= numbers.getOrElse(numIndex++) { 0.0 }
                '*' -> result *= numbers.getOrElse(numIndex++) { 1.0 }
                '/' -> {
                    val divisor = numbers.getOrElse(numIndex++) { 1.0 }
                    if (divisor == 0.0) {
                        throw ArithmeticException("Error: Division by zero")
                    }
                    result /= divisor
                }

                '√' -> result = sqrt(result)
            }
        }

        return result
    }

    private fun extractNumbersAndOperators(expression: String): Pair<List<Double>, List<Char>> {
        val modifiedExpression = if (expression.startsWith('-')) {
            expression.substring(1)
        } else {
            expression
        }

        val numberRegex = """\d+(\.\d+)?""".toRegex()
        val operatorRegex = """[-+*/√]""".toRegex()


        val numbers = numberRegex.findAll(modifiedExpression).map { it.value.toDouble() }.toMutableList()
        val operators = operatorRegex.findAll(modifiedExpression).map { it.value[0] }.toList()

        if (expression.startsWith('-')) {
            numbers[0] = -numbers[0]
        }



        return Pair(numbers, operators)
    }


//    private fun evaluateExpression(expression: String): Double? {
//        val (numbers, operators) = extractNumbersAndOperators(expression)
//
//        if (numbers.isEmpty()) return null
//
//        var result = numbers.first()
//        var operatorIndex = 0
//
//        for (i in 1 until numbers.size) {
//            when (operators.getOrNull(operatorIndex)) {
//                '+' -> result += numbers[i]
//                '-' -> result -= numbers[i]
//                '*' -> result *= numbers[i]
//                '/' -> {
//                    if (numbers[i] == 0.0) {
//                        println("Error: Division by zero")
//                        return null
//                    }
//                    result /= numbers[i]
//                }
//                '√' -> result = Math.sqrt(numbers[i])
//            }
//            if (operators.getOrNull(operatorIndex) != null) {
//                operatorIndex++
//            }
//        }

//        return result
//    }


//    private fun extractNumbersAndOperators(expression: String): Pair<List<Double>, List<Char>> {
//        val modifiedExpression = expression.replace("√", "√1*")
//        val numberRegex = """-?\d+(\.\d+)?|√1\*\d+(\.\d+)?""".toRegex()
//        val operatorRegex = """[+\-*/√]""".toRegex()
//
//        val numbers = mutableListOf<Double>()
//        val operators = mutableListOf<Char?>()
//
//        numberRegex.findAll(modifiedExpression).forEach {
//            val match = it.value
//            if (match.startsWith("√")) {
//                operators.add('√')
//                numbers.add(match.drop(2).toDouble())
//            } else {
//                numbers.add(match.toDouble())
//                operators.add(null)
//            }
//        }
//        return Pair(numbers, operators.filterNotNull())
//    }
//}


//fun extractDouble(str: String): Double? {
//    val regex = """-?\d+(\.\d+)?""".toRegex()
//    val match = regex.find(str)
//    return match?.value?.toDoubleOrNull()
//}
//
//fun extractAllDoubles(str: String): List<Double> {
//    val regex = """-?\d+(\.\d+)?""".toRegex()
//    return regex.findAll(str).mapNotNull { it.value.toDoubleOrNull() }.toList()
//}
//
//fun evaluateExpression(numbers: List<Double>, operators: List<Char>): Double {
//    var result = numbers.first()
//    for (i in 1 until numbers.size) {
//        when (operators[i - 1]) {
//            '+' -> result += numbers[i]
//            '-' -> result -= numbers[i]
//            '*' -> result *= numbers[i]
//            '/' -> result /= numbers[i]
//        }
//    }
//    return result
//}
//
//fun extractOperators(expression: String): List<Char> {
//    val operatorRegex = """[+-]""".toRegex()
//    return operatorRegex.findAll(expression).map { it.value[0] }.toList()
//}

}