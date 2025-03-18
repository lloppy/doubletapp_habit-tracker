package com.example.habittracker.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
