package com.example.habittracker.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.LocalTheme
import com.example.habittracker.R
import com.example.habittracker.ui.screens.home.HomeDestination
import com.example.habittracker.ui.screens.navigation.HabitNavigation
import com.example.habittracker.ui.theme.AppTheme
import com.example.habittracker.ui.theme.LocalThemeChange

@Composable
fun HabitTrackerApp(
    navController: NavHostController = rememberNavController()
) {
    HabitNavigation(
        navController = navController
    )
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HabitAppBar(
    title: String,
    canNavigateBack: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateUp: () -> Unit = { }
) {
    val isDark = LocalTheme.current.isDark
    val onChangeTheme = LocalThemeChange.current

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
            }
        },
        actions = {
            if (title == homeScreenTitle) {
                IconButton(onClick = {
                    onChangeTheme?.invoke(if (isDark) AppTheme.MODE_DAY else AppTheme.MODE_NIGHT)
                }) {
                    Icon(
                        painter = painterResource(
                            if (isDark) R.drawable.baseline_dark_mode_24
                            else R.drawable.baseline_light_mode_24
                        ),
                        contentDescription = "switch theme"
                    )
                }
            }
        }
    )
}



