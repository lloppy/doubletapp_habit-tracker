package com.example.habittracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.ViewList
import androidx.compose.ui.graphics.vector.ImageVector

data class PagerItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val pagerItems = listOf(
    PagerItem(
        title = "All",
        unselectedIcon = Icons.Outlined.ViewList,
        selectedIcon = Icons.Filled.ViewList
    ),
    PagerItem(
        title = "Positive",
        unselectedIcon = Icons.Outlined.ThumbUp,
        selectedIcon = Icons.Filled.ThumbUp
    ),
    PagerItem(
        title = "Negative",
        unselectedIcon = Icons.Outlined.ThumbDown,
        selectedIcon = Icons.Filled.ThumbDown
    )
)