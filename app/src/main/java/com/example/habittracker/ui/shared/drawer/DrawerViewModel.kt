package com.example.habittracker.ui.shared.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.habittracker.R
import com.example.habittracker.model.DrawerItem
import com.example.habittracker.ui.screens.home.HomeDestination
import com.example.habittracker.ui.screens.info.InfoDestination
import com.example.habittracker.ui.screens.language.LanguageDestination

class DrawerViewModel : ViewModel() {
    var state by mutableStateOf(DrawerState(drawerItems = drawerItems))
        private set

    fun onItemSelected(index: Int) {
        state = state.copy(selectedItemIndex = index)
    }

    fun setOpen(openState: DrawerValue) {
        state = state.copy(openState = openState)
    }

    fun indexOf(item: DrawerItem): Int = this.state.drawerItems.indexOf(item)


    companion object {
        private val drawerItems = listOf(
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
            ),
            DrawerItem(
                title = R.string.language,
                selectedIcon = Icons.Filled.Language,
                unselectedIcon = Icons.Outlined.Language,
                route = LanguageDestination.route
            )
        )
    }
}