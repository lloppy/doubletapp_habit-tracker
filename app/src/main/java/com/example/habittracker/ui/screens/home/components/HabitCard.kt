package com.example.habittracker.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.habittracker.R
import com.example.habittracker.mappers.getEmoji
import com.example.habittracker.ui.theme.Spacing
import com.example.model.Habit

@Composable
fun HabitCard(
    habit: Habit,
    onIncreaseRepeated: () -> Unit,
    onDecreaseRepeated: () -> Unit,
    modifier: Modifier
) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            EmojiBox(habit.color, habit.category.getEmoji())

            Column(
                modifier = Modifier.weight(1.2f),
                verticalArrangement = Arrangement.spacedBy(Spacing.small)
            ) {
                Text(
                    text = if (habit.frequency.isBlank()) {
                        habit.name
                    } else {
                        "${habit.name} ${habit.frequency.lowercase()}"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = Spacing.line.value.sp
                )
                if (habit.description.isNotBlank()) {
                    Text(
                        text = habit.description,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.small)
            ) {
                Text(
                    text = "${habit.quantity}/${habit.repeatedTimes}",
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = stringResource(R.string.times),
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
            ) {
                IconButtonBox(habit.color, onDecreaseRepeated, R.drawable.ic_remove)
                IconButtonBox(habit.color, onIncreaseRepeated, Icons.Default.Add)
            }
        }
    }
}

@Composable
private fun EmojiBox(color: Color, emoji: String) {
    Box(
        modifier = Modifier
            .size(Spacing.emoji + Spacing.medium)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = Spacing.emoji.value.sp
        )
    }
}

@Composable
private fun IconButtonBox(color: Color, onChangeRepeated: () -> Unit, icon: Any) {
    Box(
        modifier = Modifier
            .size(Spacing.emoji)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onChangeRepeated) {
            when (icon) {
                is Int -> Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(Spacing.emoji)
                )

                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(Spacing.emoji)
                )
            }
        }
    }
}