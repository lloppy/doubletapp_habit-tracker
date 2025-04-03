package com.example.habittracker.ui.screens.language

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.R
import com.example.habittracker.ui.AppViewModelProvider
import com.example.habittracker.ui.navigation.NavigationDestination
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.theme.Spacing

object LanguageDestination : NavigationDestination {
    override val route = "language"
    override val title = R.string.language_settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    onClickOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LanguageScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            HabitAppBar(
                title = stringResource(LanguageDestination.title),
                canNavigateBack = false,
                onClickOpenDrawer = onClickOpenDrawer
            )
        },
        modifier = modifier
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.medium),
            contentPadding = paddingValue
        ) {
            items(state.availableLanguages) { language ->
                LanguageItem(
                    languageName = language.displayName,
                    isSelected = language.code == state.currentLanguage,
                    onClick = {
                        viewModel.onLanguageSelected(language.code)
                    }
                )
                Spacer(modifier = Modifier.height(Spacing.section))
            }
        }
    }
}

@Composable
private fun LanguageItem(
    languageName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = languageName,
                style = MaterialTheme.typography.bodyLarge
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = languageName
                )
            }
        }
    }
}