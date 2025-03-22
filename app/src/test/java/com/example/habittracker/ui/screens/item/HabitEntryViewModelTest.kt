package com.example.habittracker.ui.screens.item

import androidx.compose.ui.graphics.Color
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
import org.junit.Assert.assertEquals
import org.junit.Test

class HabitEntryViewModelTest {

    @Test
    fun testHabitEntityToHabit() {
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

        val habit = habitEntity.toHabit()

        assertEquals(1, habit.id)
        assertEquals("Test Habit", habit.name)
        assertEquals("Test Description", habit.description)
        assertEquals(HabitType.SPORT, habit.type)
        assertEquals(HabitPriority.HIGH, habit.priority)
        assertEquals("Ежедневно", habit.frequency)
        assertEquals(5, habit.repeatedTimes)
        assertEquals(2, habit.quantity)
        assertEquals(Color.Red, habit.color)
    }

    @Test
    fun testHabitEntityToHabitWithInvalidRepeatedTimes() {
        val habitEntity = HabitEntity(
            id = 1,
            name = "Test Habit",
            description = "Test Description",
            type = "Спорт",
            priority = "Высокий",
            frequency = "Ежедневно",
            repeatedTimes = "invalid",
            quantity = "2",
            color = Color.Red
        )

        val habit = habitEntity.toHabit()

        assertEquals(1, habit.id)
        assertEquals("Test Habit", habit.name)
        assertEquals("Test Description", habit.description)
        assertEquals(HabitType.SPORT, habit.type)
        assertEquals(HabitPriority.HIGH, habit.priority)
        assertEquals("Ежедневно", habit.frequency)
        assertEquals(1, habit.repeatedTimes) // Default value
        assertEquals(2, habit.quantity)
        assertEquals(Color.Red, habit.color)
    }

    @Test
    fun testHabitEntityToHabitWithInvalidQuantity() {
        val habitEntity = HabitEntity(
            id = 1,
            name = "Test Habit",
            description = "Test Description",
            type = "Спорт",
            priority = "Высокий",
            frequency = "Ежедневно",
            repeatedTimes = "5",
            quantity = "invalid",
            color = Color.Red
        )

        val habit = habitEntity.toHabit()

        assertEquals(1, habit.id)
        assertEquals("Test Habit", habit.name)
        assertEquals("Test Description", habit.description)
        assertEquals(HabitType.SPORT, habit.type)
        assertEquals(HabitPriority.HIGH, habit.priority)
        assertEquals("Ежедневно", habit.frequency)
        assertEquals(5, habit.repeatedTimes)
        assertEquals(0, habit.quantity) // Default value
        assertEquals(Color.Red, habit.color)
    }

    @Test
    fun testHabitToUiState() {
        val habit = Habit(
            id = 1,
            name = "Test Habit",
            description = "Test Description",
            type = HabitType.SPORT,
            priority = HabitPriority.HIGH,
            frequency = "Ежедневно",
            repeatedTimes = 5,
            quantity = 2,
            color = Color.Red
        )

        val habitEntity = habit.toUiState()

        assertEquals(1, habitEntity.id)
        assertEquals("Test Habit", habitEntity.name)
        assertEquals("Test Description", habitEntity.description)
        assertEquals("Спорт", habitEntity.type)
        assertEquals("Высокий", habitEntity.priority)
        assertEquals("Ежедневно", habitEntity.frequency)
        assertEquals("5", habitEntity.repeatedTimes)
        assertEquals("2", habitEntity.quantity)
        assertEquals(Color.Red, habitEntity.color)
    }
}
