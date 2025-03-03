package com.example.habittracker

import androidx.compose.ui.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habittracker.data.FakeRepository
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitPeriodicity
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class HabitRepositoryTest {

    private val habit1 = testHabits[0].copy()
    private val habit2 = testHabits[1].copy()

    @After
    @Throws(IOException::class)
    fun tearDown() {
        FakeRepository.clear()
    }

    @Test
    @Throws(Exception::class)
    fun insertHabit_shouldAddHabitToRepository() {
        addSingleHabitToRepository()
        assertEquals(testHabits.first(), FakeRepository.habits.value.first())
    }

    @Test
    @Throws(Exception::class)
    fun getAllHabits_shouldReturnAllHabitsFromRepository() {
        addTwoHabitsToRepository()
        assertEquals(testHabits[0], FakeRepository.habits.value[0])
        assertEquals(testHabits[1], FakeRepository.habits.value[1])
    }

    @Test
    @Throws(Exception::class)
    fun updateHabit_shouldUpdateHabitInRepository() {
        addTwoHabitsToRepository()
        FakeRepository.updateItem(testHabits[2])
        FakeRepository.updateItem(testHabits[3])

        assertEquals(testHabits[2], FakeRepository.habits.value[0])
        assertEquals(testHabits[3], FakeRepository.habits.value[1])
    }

    private fun addSingleHabitToRepository() {
        FakeRepository.addHabit(habit1)
    }

    private fun addTwoHabitsToRepository() {
        FakeRepository.addHabit(habit1)
        FakeRepository.addHabit(habit2)
    }
}

private val testHabits = mutableListOf(
    Habit(
        id = "1234567890",
        name = "Утренняя зарядка",
        description = "Зарядка для улучшения настроения",
        priority = HabitPriority.HIGH,
        type = HabitType.SPORT,
        periodicity = HabitPeriodicity("Ежедневно"),
        color = Color.Red
    ),
    Habit(
        id = "123456789",
        name = "Чтение перед сном",
        description = "Чтение для расслабления",
        priority = HabitPriority.MEDIUM,
        type = HabitType.RELAXATION,
        periodicity = HabitPeriodicity("Раз в неделю"),
        color = Color.Blue
    ),
    Habit(
        id = "1234567890",
        name = "Просмотр лекций",
        description = "Обучение на платформе Coursera",
        priority = HabitPriority.LOW,
        type = HabitType.STUDY,
        periodicity = HabitPeriodicity("Раз в две недели"),
        color = Color.Green
    ),
    Habit(
        id = "123456789",
        name = "Планирование дня",
        description = "Планирование задач на день",
        priority = HabitPriority.MEDIUM,
        type = HabitType.PRODUCTIVITY,
        periodicity = HabitPeriodicity("Ежедневно"),
        color = Color.Yellow
    )
)