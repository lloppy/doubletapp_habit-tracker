package com.example.habittracker.ui.shared.drawer

import androidx.compose.material3.DrawerValue

data class DrawerState(
    val selectedItemIndex: Int = 0,
    val openState: DrawerValue = DrawerValue.Closed,
    val drawerItems: List<DrawerItem>
)