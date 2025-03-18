package com.example.habittracker.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.habittracker.R
import com.example.habittracker.model.Habit

@Composable
fun SwipeableCard(
    habit: Habit,
    onIncreaseRepeated: () -> Unit,
    onDecreaseRepeated: () -> Unit,
    onClickHabit: () -> Unit,
    onClickDelete: () -> Unit,
    onClickEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState()

    LaunchedEffect(dismissState.currentValue) {
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.StartToEnd -> {
                onClickDelete()
                dismissState.reset()
            }

            SwipeToDismissBoxValue.EndToStart -> {
                onClickEdit()
                dismissState.reset()
            }

            else -> { /* No action needed */ }
        }
    }

    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        backgroundContent = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd,
                    enter = fadeIn()
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(
                    visible = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart,
                    enter = fadeIn()
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit)
                    )
                }
            }
        }
    ) {
        HabitCard(
            habit = habit,
            onIncreaseRepeated = onIncreaseRepeated,
            onDecreaseRepeated = onDecreaseRepeated,
            modifier = modifier.clickable(onClick = onClickHabit)
        )
    }
}

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
            EmojiBox(habit.color, habit.category.getEmoji())

            Column(
                modifier = Modifier.weight(1.2f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.titleLarge,
                    lineHeight = dimensionResource(R.dimen.line_height).value.sp
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
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
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

@Composable
fun IconButtonBox(color: Color, onChangeRepeated: () -> Unit, icon: Any) {
    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen.emoji_size))
            .clip(CircleShape)
            .background(color.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onChangeRepeated) {
            when (icon) {
                is Int -> Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.emoji_size))
                )

                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.emoji_size))
                )
            }
        }
    }
}