package com.example.habittracker.ui.screens.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import com.example.habittracker.R
import com.example.habittracker.ui.navigation.NavigationDestination
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.screens.shared.components.FeatureCard

object InfoDestination : NavigationDestination {
    override val route = "info_destination"
    override val title = R.string.info_destination
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    onClickOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            HabitAppBar(
                title = stringResource(InfoDestination.title),
                canNavigateBack = false,
                onClickOpenDrawer = onClickOpenDrawer
            )
        },
        modifier = modifier
    ) { paddingValue ->

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(paddingValue)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            SectionTitle("О приложении")
            InfoText("Трекер привычек с современным интерфейсом на Jetpack Compose. Основные функции:")

            BulletPoint("Управление привычками")
            BulletPoint("Настройка параметров")
            BulletPoint("Отслеживаие прогресса")

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            SectionTitle("Технические особенности")
            InfoText("Архитектура приложения включает:")

            FeatureCard(
                title = "Навигация",
                content = {
                    CodeSnippet(
                        code = """
                        ModalNavigationDrawer {
                            NavHost(startDestination = "home") {
                                composable("home") { HomeScreen() }
                                composable("info") { InfoScreen() }
                            }
                        }
                        """
                    )
                }
            )

            FeatureCard(
                title = "Состояние интерфейса",
                content = {
                    InfoText(
                        "• Управление через ViewModel и StateFlow\n" +
                                "• Темная/светлая тема\n" +
                                "• Поддержка portrait/landscape ориентации\n" +
                                "• Адаптивные карточки привычек"
                    )
                }
            )

            SectionTitle("Пример реализации")
            CodeSnippet(
                code = """
                @Composable
                fun HabitInputForm(habitEntity: HabitEntity) {
                    OutlinedTextField(
                        value = habitEntity.name,
                        onValueChange = { /* Update logic */ },
                        label = { Text("Название привычки") }
                    )
                    // Остальные поля
                }
                """
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.section_padding))
    )
}

@Composable
private fun InfoText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.section_padding))
    )
}

@Composable
private fun BulletPoint(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_medium),
            bottom = dimensionResource(R.dimen.padding_small)
        )
    ) {
        Text("• ", style = MaterialTheme.typography.bodyMedium)
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun CodeSnippet(code: String) {
    Text(
        text = code.trimIndent(),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily.Monospace
        ),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(dimensionResource(R.dimen.padding_small))
            )
            .padding(dimensionResource(R.dimen.section_padding))
    )
}
