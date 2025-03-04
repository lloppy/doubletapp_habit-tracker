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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.habittracker.R
import com.example.habittracker.model.Habit

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
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            EmojiBox(habit.color, habit.type.emoji)

            Column(
                modifier = Modifier.weight(1.2f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.titleLarge,
                    lineHeight = dimensionResource(R.dimen.line_height).value.sp
                )
                Text(
                    text = habit.description,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = "${habit.periodicity.currentRepeated}/${habit.periodicity.repeatedTimes}",
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = stringResource(R.string.times),
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                IconButtonBox(habit.color, onDecreaseRepeated, R.drawable.ic_remove)
                IconButtonBox(habit.color, onIncreaseRepeated, Icons.Default.Add)
            }
        }
    }
}

@Composable
fun EmojiBox(color: Color, emoji: String) {
    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen.emoji_size) + dimensionResource(R.dimen.padding_medium))
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = dimensionResource(R.dimen.emoji_size).value.sp
        )
    }
}
