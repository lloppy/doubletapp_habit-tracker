package com.example.habittracker.ui.shared.pager

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class PagerItem(
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
