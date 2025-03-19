package com.example.habittracker.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.habittracker.R

data class PagerItem(
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val pagerItems = listOf(
    PagerItem(
        title = R.string.all,
        unselectedIcon = Icons.AutoMirrored.Outlined.ViewList,
        selectedIcon = Icons.AutoMirrored.Filled.ViewList
    ),
    PagerItem(
        title = R.string.positive,
        unselectedIcon = Icons.Outlined.ThumbUp,
        selectedIcon = Icons.Filled.ThumbUp
    ),
    PagerItem(
        title = R.string.negative,
        unselectedIcon = Icons.Outlined.ThumbDown,
        selectedIcon = Icons.Filled.ThumbDown
    )
)