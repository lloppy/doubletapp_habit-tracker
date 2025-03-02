package com.example.habittracker.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.habittracker.model.Habit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableCard(
    habit: Habit,
    onClickHabit: () -> Unit,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState()

    LaunchedEffect(dismissState.currentValue) {
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.EndToStart -> {
                onClickEdit()
                dismissState.reset()
            }
            SwipeToDismissBoxValue.StartToEnd -> {
                onClickDelete()
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
                        contentDescription = "Delete"
                    )
                }

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
            onClickHabit = onClickHabit,
            modifier = modifier
        )
    }
}

