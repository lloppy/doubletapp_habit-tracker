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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import com.example.habittracker.R
import com.example.habittracker.ui.navigation.NavigationDestination
import com.example.habittracker.ui.screens.HabitAppBar
import com.example.habittracker.ui.shared.form.FeatureCard
import com.example.habittracker.ui.theme.Spacing

object InfoDestination : NavigationDestination {
    override val route = "info_destination"
    override val title = R.string.about_app
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
                .padding(paddingValue)
                .padding(Spacing.medium)
                .verticalScroll(scrollState)
        ) {
            SectionTitle(text = stringResource(R.string.about_app))
            InfoText(text = stringResource(R.string.app_description))

            BulletList(
                items = listOf(
                    stringResource(R.string.feature_habit_management),
                    stringResource(R.string.feature_settings),
                    stringResource(R.string.feature_progress_tracking),
                )
            )
            Spacer(modifier = Modifier.height(Spacing.medium))

            SectionTitle(text = stringResource(R.string.technical_features))
            InfoText(text = stringResource(R.string.architecture_description))

            FeatureCard(
                title = stringResource(R.string.navigation_title),
                content = { CodeSnippet(code = stringResource(R.string.navigation_code_snippet)) }
            )

            FeatureCard(
                title = stringResource(R.string.interface_state_title),
                content = {
                    BulletList(
                        items = listOf(
                            stringResource(R.string.state_management_viewmodel),
                            stringResource(R.string.state_management_theme),
                            stringResource(R.string.state_management_orientation),
                            stringResource(R.string.state_management_cards)
                        )
                    )
                }
            )

            SectionTitle(text = stringResource(R.string.implementation_example))
            CodeSnippet(code = stringResource(R.string.implementation_code_snippet))
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(
            vertical = Spacing.section
        )
    )
}

@Composable
private fun InfoText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = Spacing.section)
    )
}

@Composable
private fun BulletList(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        items.forEach { item ->
            BulletPoint(item)
        }
    }
}

@Composable
private fun BulletPoint(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = Spacing.medium,
            bottom = Spacing.small
        )
    ) {
        Text(text = stringResource(R.string.bullet_item, text))
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
                shape = RoundedCornerShape(Spacing.small)
            )
            .padding(Spacing.section)
    )
}
