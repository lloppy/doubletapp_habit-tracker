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
import com.example.habittracker.model.HabitType
import com.example.habittracker.ui.shared.FeatureWithBackgroundCard
import kotlin.enums.EnumEntries

@Composable
fun TypeCard(
    selectedValue: HabitType,
    onOptionSelected: (HabitType) -> Unit,
    options: EnumEntries<HabitType>,
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