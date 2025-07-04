package com.example.habittracker.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.R
import com.example.habittracker.navigation.HabitNavigation
import com.example.habittracker.ui.screens.home.HomeDestination
import com.example.habittracker.ui.shared.drawer.HabitDrawer
import com.example.model.AppTheme

@Composable
fun HabitTrackerApp(
    viewModelFactory: ViewModelProvider.Factory,
    appTheme: AppTheme,
    navController: NavHostController = rememberNavController()
) {
    HabitDrawer(
        navController = navController,
        appTheme = appTheme
    ) { onClickOpenDrawer ->
        HabitNavigation(
            navController = navController,
            viewModelFactory = viewModelFactory,
            onClickOpenDrawer = onClickOpenDrawer
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HabitAppBar(
    title: String,
    canNavigateBack: Boolean,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onClickOpenDrawer: () -> Unit = { },
    onClickFilter: () -> Unit = { },
    navigateUp: () -> Unit = { }
) {
    val homeScreenTitle = stringResource(HomeDestination.title)

    CenterAlignedTopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.headlineSmall) },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            } else {
                IconButton(onClick = onClickOpenDrawer) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.menu_button)
                    )
                }
            }
        },
        actions = {
            if (title == homeScreenTitle) {
                IconButton(onClick = onClickFilter) {
                    Icon(
                        imageVector = Icons.Default.FilterAlt,
                        contentDescription = stringResource(R.string.filter)
                    )
                }
            }
        }
    )
}
