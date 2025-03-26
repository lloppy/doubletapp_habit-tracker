package com.example.habittracker.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.habittracker.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalSheet(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = onDismiss
        ) {
            Column(modifier = modifier.padding(Spacing.medium)) {
                Text(text = "filter1")

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onSubmit()
                        onDismiss()
                    }
                ) {
                    Text("Apply filter")
                }
            }
        }
    }
}