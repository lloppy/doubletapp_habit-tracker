package com.example.habittracker.data.repository

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TestRoot(
    viewModel: TestViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TestScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun TestScreen(
    state: TestState,
    onAction: (TestAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    HabitTrackerTheme {
        TestScreen(
            state = TestState(),
            onAction = {}
        )
    }
}