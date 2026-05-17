package com.example.basic_calculator.state

// holds the ui state for the calculator screen
data class CalculatorUiState(
    val display: String = "0",
    val firstNumber: Double? = null,
    val operation: String? = null,
    val waitingForSecondNumber: Boolean = false,
    val errorMessage: String? = null
)