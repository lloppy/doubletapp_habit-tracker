package com.example.habittracker.ui.shared.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.habittracker.LocalTheme
import com.example.habittracker.R
import com.example.habittracker.model.DrawerItem
import com.example.habittracker.ui.theme.AppTheme
import com.example.habittracker.ui.theme.LocalThemeChange
import com.example.habittracker.ui.theme.Spacing
import kotlinx.coroutines.launch

@Composable
fun HabitDrawer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DrawerViewModel = viewModel(),
    content: @Composable (onClickOpenDrawer: () -> Unit) -> Unit
) {
    val state = viewModel.state
    val drawerState = rememberDrawerState(initialValue = state.openState)

    val scope = rememberCoroutineScope()

    val isDark = LocalTheme.current.isDark
    val onChangeTheme = LocalThemeChange.current

    LaunchedEffect(drawerState.isOpen) {
        viewModel.setOpen(drawerState.currentValue)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    drawerItems = state.drawerItems,
                    selectedItemIndex = state.selectedItemIndex,
                    onDrawerClick = { item ->
                        viewModel.onItemSelected(viewModel.indexOf(item))
                        navController.navigate(item.route)
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    onChangeThemeClick = {
                        onChangeTheme?.invoke(
                            if (isDark) AppTheme.MODE_DAY else AppTheme.MODE_NIGHT
                        )
                    },
                    isDark = isDark,
                    modifier = Modifier.padding(Spacing.medium)
                )
            }
        },
        modifier = modifier
    ) {
        content {
            scope.launch { drawerState.open() }
        }
    }
}

@Composable
fun DrawerContent(
    drawerItems: List<DrawerItem>,
    selectedItemIndex: Int,
    onDrawerClick: (DrawerItem) -> Unit,
    onChangeThemeClick: () -> Unit,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = modifier) {
            Image(painterResource(R.drawable.ic_launcher_foreground), contentDescription = null)
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        IconButton(
            onClick = onChangeThemeClick,
            modifier = modifier
        ) {
            Icon(
                painter = painterResource(
                    if (isDark) R.drawable.ic_dark_mode
                    else R.drawable.ic_light_mode
                ),
                contentDescription = stringResource(R.string.switch_theme)
            )
        }
    }
    HorizontalDivider()

    Spacer(modifier = Modifier.height(Spacing.medium))
    drawerItems.forEachIndexed { index, item ->
        NavigationDrawerItem(
            label = { Text(text = stringResource(item.title)) },
            selected = index == selectedItemIndex,
            onClick = { onDrawerClick(item) },
            icon = {
                Icon(
                    imageVector = if (index == selectedItemIndex) {
                        item.selectedIcon
                    } else item.unselectedIcon,
                    contentDescription = stringResource(item.title)
                )
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

