package com.example.habittracker.ui.shared.form.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.model.domain.HabitType
import com.example.habittracker.ui.shared.FeatureWithBackgroundCard
import kotlin.enums.EnumEntries

@Composable
fun TypeCard(
    selectedValue: com.example.model.domain.HabitType,
    onOptionSelected: (com.example.model.domain.HabitType) -> Unit,
    options: EnumEntries<com.example.model.domain.HabitType>,
    label: String,
    modifier: Modifier = Modifier
) {
    FeatureWithBackgroundCard(title = label) {
        Column(modifier = modifier.selectableGroup()) {
            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = { onOptionSelected(option) }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = option == selectedValue,
                        onClick = {
                            onOptionSelected(option)
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = option.getColor(),
                            unselectedColor = option.getColor().copy(alpha = 0.8f)
                        )
                    )
                    Text(
                        text = stringResource(option.impactName),
                        color = option.getColor()
                    )
                }
            }
        }
    }
}