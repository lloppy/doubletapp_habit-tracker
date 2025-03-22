package com.example.habittracker.ui.screens.item

import androidx.compose.ui.graphics.Color
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EditHabitViewModelTest {

    private lateinit var viewModel: EditHabitViewModel

    @Before
    fun setUp() {
        val repository = FakeHabitsRepository()
        viewModel = EditHabitViewModel(SavedStateHandle(), repository)
    }

    @Test
    fun testValidateInput() {
        val validHabitEntity = HabitEntity(
            id = 1,
            name = "Test Habit",
            description = "Test Description",
            type = "Спорт",
            priority = "Высокий",
            frequency = "Ежедневно",
            repeatedTimes = "5",
            quantity = "2",
            color = Color.Red
        )

        val invalidHabitEntity = HabitEntity(
            id = 1,
            name = "",
            description = "Test Description",
            type = "Спорт",
            priority = "Высокий",
            frequency = "Ежедневно",
            repeatedTimes = "invalid",
            quantity = "2",
            color = Color.Red
        )

        assertEquals(true, viewModel.validateInput(validHabitEntity))
        assertEquals(false, viewModel.validateInput(invalidHabitEntity))
    }

    @Test
    fun testUpdateItem() = runBlocking {
        val habitEntity = HabitEntity(
            id = 1,
            name = "Test Habit",
            description = "Test Description",
            type = "Спорт",
            priority = "Высокий",
            frequency = "Ежедневно",
            repeatedTimes = "5",
            quantity = "2",
            color = Color.Red
        )

        viewModel.updateUiState(habitEntity)
        viewModel.updateItem()

        val updatedHabit = viewModel.repository.getHabit(1).first()
        assertEquals(habitEntity.toHabit(), updatedHabit)
    }
}
