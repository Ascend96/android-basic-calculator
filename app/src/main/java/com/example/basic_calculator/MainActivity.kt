package com.example.basic_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.basic_calculator.ui.CalculatorScreen
import com.example.basic_calculator.ui.theme.BasiccalculatorTheme
import com.example.basic_calculator.viewmodel.CalculatorViewModel

class MainActivity : ComponentActivity() {

    // viewmodel scoped to activity
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BasiccalculatorTheme {
                // collect stateflow from viewmodel
                val state by viewModel.uiState.collectAsState()

                // Show ui
                CalculatorScreen(
                    state = state,
                    onButtonClick = viewModel::onAction
                )
            }
        }
    }
}