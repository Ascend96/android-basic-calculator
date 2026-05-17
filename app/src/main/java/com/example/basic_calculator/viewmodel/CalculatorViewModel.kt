package com.example.basic_calculator.viewmodel

import androidx.lifecycle.ViewModel
import com.example.basic_calculator.state.CalculatorUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel : ViewModel() {

    // private mutable state
    private val _uiState = MutableStateFlow(CalculatorUiState())

    // public immutable state (ui observes this)
    val uiState: StateFlow<CalculatorUiState> = _uiState

    // handles all button presses
    fun onAction(action: String) {
        when (action) {

            "C" -> clear()

            "=" -> calculate()

            "+", "-", "×", "÷" -> setOperation(action)

            else -> appendNumber(action)
        }
    }

    // handles logic for numeric input
    private fun appendNumber(number: String) {
        val current = _uiState.value

        val newDisplay = if (
            current.display == "0" ||
            current.waitingForSecondNumber
        ) {
            number
        } else {
            current.display + number
        }

        _uiState.value = current.copy(
            display = newDisplay,
            waitingForSecondNumber = false,
            errorMessage = null
        )
    }


    // sets operation based on button pressed
    private fun setOperation(op: String) {
        val current = _uiState.value

        val number = current.display.toDoubleOrNull()
        if (number == null) {
            showError("Invalid number")
            return
        }

        _uiState.value = current.copy(
            firstNumber = number,
            operation = op,
            waitingForSecondNumber = true
        )
    }

    // main calculation method (=)
    private fun calculate() {
        val current = _uiState.value

        val first = current.firstNumber
        val second = current.display.toDoubleOrNull()

        if (first == null || second == null) {
            showError("Missing values")
            return
        }

        // handle arithmetic operations
        val result = when (current.operation) {

            "+" -> first + second
            "-" -> first - second
            "×" -> first * second

            "÷" -> {
                // handle divide by zero error
                if (second == 0.0) {
                    showError("Cannot divide by zero")
                    return
                }
                first / second
            }

            else -> second
        }

        _uiState.value = current.copy(
            display = result.toString(),
            firstNumber = null,
            operation = null,
            waitingForSecondNumber = false,
            errorMessage = null
        )
    }


    // clear calculator value
    private fun clear() {
        _uiState.value = CalculatorUiState()
    }

    // handle errors
    private fun showError(message: String) {
        _uiState.value = _uiState.value.copy(
            display = "Error",
            errorMessage = message,
            firstNumber = null,
            operation = null,
            waitingForSecondNumber = false
        )
    }
}