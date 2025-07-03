package com.example.habittracker.ui.screens.language

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habittracker.MainActivity
import com.example.habittracker.TestHabitTrackerApplication
import com.example.habittracker.ui.theme.HabitTrackerTheme
import com.example.habittracker.util.TestTags
import com.example.model.AppTheme
import com.example.model.Language
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LanguageScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: LanguageScreenViewModel

    private val appLanguages = listOf(
        Language("en", "English"),
        Language("es", "Español"),
        Language("ru", "Русский")
    )

    fun setup() {
        val app = ApplicationProvider.getApplicationContext<TestHabitTrackerApplication>()
        viewModel = app.component.getLanguageViewModel()

        composeRule.setContent {
            val navController = rememberNavController()

            HabitTrackerTheme(AppTheme.LIGHT) {
                NavHost(
                    navController = navController,
                    startDestination = LanguageDestination.route,
                ) {
                    composable(route = LanguageDestination.route) {
                        LanguageScreen(
                            viewModel = viewModel,
                            onClickOpenDrawer = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun clickChangeLanguage_isChangedLanguage() {
        composeRule.onNodeWithTag(TestTags.LANGUAGE_ITEM).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(appLanguages[1].displayName).performClick()
        composeRule.onNodeWithTag(TestTags.LANGUAGE_ITEM).assertIsDisplayed()
    }

    @Test
    fun shouldDisplayAvailableLanguages() {
        composeRule.onNodeWithText(appLanguages[0].displayName).assertIsDisplayed()
        composeRule.onNodeWithText(appLanguages[1].displayName).assertIsDisplayed()
        composeRule.onNodeWithText(appLanguages[2].displayName).assertIsDisplayed()
    }

    @Test
    fun shouldDisplayLanguage() {
        composeRule.onNodeWithText("random language").assertIsNotDisplayed()
    }

    @Test
    fun updatesUiWhenLanguageSelected() {
        composeRule.onNodeWithTag(appLanguages[0].displayName)
            .assertIsSelected() //сначала по дефолту выбран английский
        composeRule.onNodeWithText(appLanguages[1].displayName).performClick() // кликаем на русский

        composeRule.onNodeWithTag(appLanguages[1].displayName)
            .assertIsSelected() // проверяем - выбран русский
        composeRule.onNodeWithTag(appLanguages[1].displayName)
            .assertIsNotSelected() // и не выбран английский
    }

    @Test
    fun showsCheckIconForCurrentLanguage() {
        viewModel.onLanguageSelected(appLanguages[1].code)

        composeRule.onNodeWithTag(appLanguages[1].displayName)
            .onChild()
            .assertContentDescriptionEquals(appLanguages[1].displayName)
            .assertIsDisplayed()
    }
}