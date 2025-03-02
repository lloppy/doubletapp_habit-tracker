package com.example.habittracker.ui.screens.item

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.habittracker.R
import com.example.habittracker.ui.screens.navigation.NavigationDestination

object HabitEntryDestination: NavigationDestination {
    override val route = "entry_habit"
    override val title = R.string.entry
}

@Composable
fun HabitEntryScreen(modifier: Modifier = Modifier) {
    TextField(
        value = "text",
        onValueChange = { }
    )
}