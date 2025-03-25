package com.example.habittracker.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.habittracker.R
import com.example.habittracker.ui.screens.home.HomeDestination
import com.example.habittracker.ui.screens.info.InfoDestination

data class DrawerItem(
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

val drawerItems = listOf(
    DrawerItem(
        title = R.string.habits,
        selectedIcon = Icons.Filled.Menu,
        unselectedIcon = Icons.Outlined.Menu,
        route = HomeDestination.route
    ),
    DrawerItem(
        title = R.string.info,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
        route = InfoDestination.route,
    )
)