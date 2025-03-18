package com.example.habittracker.ui.screens.shared.components

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
import com.example.habittracker.model.HabitCategory
import com.example.habittracker.ui.screens.info.FeatureCard
import kotlin.enums.EnumEntries

@Composable
fun CategoryCard(
    selectedValue: String,
    onOptionSelected: (String) -> Unit = {},
    options: EnumEntries<HabitCategory>,
    label: String,
    modifier: Modifier = Modifier
) {
    FeatureCard(label) {
        Column(modifier.selectableGroup()) {
            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onOptionSelected(option.categoryName) }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option.categoryName == selectedValue),
                        onClick = { onOptionSelected(option.categoryName) }
                    )
                    Text(text = option.categoryName)
                }
            }
        }
    }
}


