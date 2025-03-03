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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.R
import com.example.habittracker.model.Habit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableCard(
    habit: Habit,
    onIncreaseRepeated: () -> Unit,
    onDecreaseRepeated: () -> Unit,
    onClickHabit: () -> Unit,
    onClickEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState()

    LaunchedEffect(dismissState.currentValue) {
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.StartToEnd -> {
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
                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(
                    visible = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart,
                    enter = fadeIn()
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }

            }
        }
    ) {
        HabitCard(
            habit = habit,
            onIncreaseRepeated = onIncreaseRepeated,
            onDecreaseRepeated = onDecreaseRepeated,
            onClickHabit = onClickHabit,
            modifier = modifier
        )
    }
}

@Composable
fun HabitCard(
    habit: Habit,
    onIncreaseRepeated: () -> Unit,
    onDecreaseRepeated: () -> Unit,
    onClickHabit: () -> Unit,
    modifier: Modifier
) {
    val emojiSize = dimensionResource(R.dimen.emoji_size)
    val paddingMedium = dimensionResource(R.dimen.padding_medium)
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    val lineHeight = dimensionResource(R.dimen.line_height)

    Card(modifier = modifier.clickable(onClick = onClickHabit), elevation = CardDefaults.cardElevation()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(paddingMedium)
        ) {
            EmojiBox(habit.color, habit.type.emoji, emojiSize, paddingMedium)

            Column(
                modifier = Modifier.weight(1.2f),
                verticalArrangement = Arrangement.spacedBy(paddingSmall)
            ) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.titleLarge,
                    lineHeight = lineHeight.value.sp
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
                verticalArrangement = Arrangement.spacedBy(paddingSmall)
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
                horizontalArrangement = Arrangement.spacedBy(paddingMedium)
            ) {
                IconButtonBox(habit.color, onDecreaseRepeated, R.drawable.ic_remove, emojiSize)
                IconButtonBox(habit.color, onIncreaseRepeated, Icons.Default.Add, emojiSize)
            }
        }
    }
}

@Composable
fun EmojiBox(color: Color, emoji: String, emojiSize: Dp, padding: Dp) {
    Box(
        modifier = Modifier
            .size(emojiSize + padding * 2)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = emojiSize.value.sp
        )
    }
}

@Composable
fun IconButtonBox(color: Color, onChangeRepeated: () -> Unit, icon: Any, emojiSize: Dp) {
    Box(
        modifier = Modifier
            .size(emojiSize)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onChangeRepeated) {
            when (icon) {
                is Int -> Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(emojiSize)
                )

                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(emojiSize)
                )
            }
        }
    }
}
