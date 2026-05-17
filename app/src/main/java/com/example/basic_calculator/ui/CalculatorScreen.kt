package com.example.basic_calculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.basic_calculator.state.CalculatorUiState
import com.example.basic_calculator.ui.components.CalculatorButton

@Composable
fun CalculatorScreen(
    state: CalculatorUiState,
    onButtonClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {


        // display area
        Column(
            // takes up full width
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = state.display, // shows current input/result
                style = MaterialTheme.typography.displayMedium // changes typography to larger text for main calculator
            )

            // only show the error if one exists
            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error, // changes color to error
                    style = MaterialTheme.typography.bodyMedium // change typography to smaller text for error message
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp)) // add space between display and buttons


        // grid of buttons: 4 rows
        val buttons = listOf(
            listOf("7", "8", "9", "÷"),
            listOf("4", "5", "6", "×"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp) // defines space between the rows
        ) {
            buttons.forEach { row -> // for each to loop through each row
                Row(
                    modifier = Modifier.fillMaxWidth(), // each row fills the width evenly
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // defines space between each button
                ) {
                    row.forEach { label ->
                        CalculatorButton(
                            label = label, // text of each button
                            modifier = Modifier
                                .weight(1f) // spaces buttons evenly
                                .aspectRatio(1f), // makes buttons square
                            onClick = { onButtonClick(label) } // handles on click event sent to view model
                        )
                    }
                }
            }
        }
    }
}
