package com.elias.astra

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {
    private var display = "0"
    private var first = 0.0
    private var operator = ""
    private var newNumber = true
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 25, 20, 20)
            setBackgroundColor(Color.rgb(8, 15, 28))
        }

        val title = TextView(this).apply {
            text = "ASTRA"
            textSize = 28f
            gravity = Gravity.CENTER
            setTextColor(Color.CYAN)
        }
        root.addView(title)

        result = TextView(this).apply {
            text = "0"
            textSize = 42f
            gravity = Gravity.END or Gravity.CENTER_VERTICAL
            setTextColor(Color.WHITE)
            setPadding(10, 25, 10, 25)
        }
        root.addView(result)

        val keys = listOf(
            listOf("AC", "±", "%", "÷"),
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "−"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "⌫", "=")
        )

        keys.forEach { row ->
            val line = LinearLayout(this)
            row.forEach { key ->
                val button = Button(this).apply {
                    text = key
                    textSize = 20f
                    setTextColor(Color.WHITE)
                    setBackgroundColor(Color.rgb(27, 42, 62))
                    setOnClickListener { press(key) }
                }
                line.addView(button, LinearLayout.LayoutParams(0, 75, 1f))
            }
            root.addView(line)
        }

        val credits = TextView(this).apply {
            text = "Desenvolvido por Elias Douglas\nCriador do projeto Astra • 2026"
            gravity = Gravity.CENTER
            setTextColor(Color.LTGRAY)
            textSize = 12f
        }
        root.addView(credits)

        setContentView(root)
    }

    private fun press(key: String) {
        when (key) {
            "AC" -> {
                display = "0"
                operator = ""
                first = 0.0
            }
            "⌫" -> display =
                if (display.length > 1) display.dropLast(1) else "0"
            "±" -> display = (-display.toDouble()).toString()
            "%" -> display = (display.toDouble() / 100).toString()
            "." -> if (!display.contains(".")) display += "."
            "+", "−", "×", "÷" -> {
                first = display.toDouble()
                operator = key
                newNumber = true
            }
            "=" -> {
                val second = display.toDouble()
                val answer = when (operator) {
                    "+" -> first + second
                    "−" -> first - second
                    "×" -> first * second
                    "÷" -> if (second != 0.0) first / second else Double.NaN
                    else -> second
                }
                display = if (answer.isFinite()) answer.toString() else "Erro"
                operator = ""
                newNumber = true
            }
            else -> {
                display = if (newNumber || display == "0") key else display + key
                newNumber = false
            }
        }
        result.text = display
    }
}
