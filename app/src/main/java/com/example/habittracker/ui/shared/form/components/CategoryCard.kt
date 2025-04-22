package com.example.habittracker.ui.shared.form.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.model.HabitCategory
import com.example.habittracker.ui.shared.FeatureWithBackgroundCard
import kotlin.enums.EnumEntries

@Composable
fun CategoryCard(
    selectedValue: HabitCategory,
    onOptionSelected: (HabitCategory) -> Unit = {},
    options: EnumEntries<HabitCategory>,
    label: String,
    modifier: Modifier = Modifier
) {
    FeatureWithBackgroundCard(
        title = label,
        modifier = Modifier.selectableGroup()
    ) {
        Column {
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
                        }
                    )
                    Text(text = stringResource(option.categoryName))
                }
            }
        }
    }
}


