package com.example.habittracker.ui.shared.filter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import com.example.model.HabitPriority
import com.example.habittracker.ui.shared.SectionTitle
import com.example.habittracker.ui.theme.Spacing

@Composable
fun PrioritySection(
    selectedPriority: HabitPriority?,
    onPrioritySelected: (HabitPriority?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        SectionTitle(text = stringResource(R.string.priority))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.small)
        ) {
            HabitPriority.entries.forEach { priority ->
                FilterCard(
                    selected = selectedPriority == priority,
                    priorityName = stringResource(priority.priorityName),
                    onClick = {
                        onPrioritySelected(
                            if (selectedPriority == priority) null else priority
                        )
                    }
                )
            }
        }
    }
}


@Composable
private fun FilterCard(
    selected: Boolean,
    priorityName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = if (selected) {
        Pair(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.primary
        )
    } else {
        Pair(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }

    Surface(
        color = colors.first,
        contentColor = colors.second,
        modifier = modifier
            .clip(RoundedCornerShape(Spacing.corner))
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = priorityName)
        }
    }
}