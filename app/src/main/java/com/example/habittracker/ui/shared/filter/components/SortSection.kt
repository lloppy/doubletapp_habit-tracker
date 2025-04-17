package com.example.habittracker.ui.shared.filter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.habittracker.R
import com.example.habittracker.model.SortOption
import com.example.habittracker.ui.shared.FeatureWithBackgroundCard
import com.example.habittracker.ui.theme.Spacing

@Composable
fun SortSection(
    sortOption: SortOption,
    onSortOptionChanged: (SortOption) -> Unit
) {
    FeatureWithBackgroundCard(
        title = stringResource(R.string.sort_by)
    ) {
        SortOption.entries.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.small)
                    .clickable { onSortOptionChanged(option) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = sortOption == option,
                    onClick = { onSortOptionChanged(option) }
                )
                Spacer(modifier = Modifier.width(Spacing.small))
                Text(stringResource(option.displayName))
            }
        }
    }
}
