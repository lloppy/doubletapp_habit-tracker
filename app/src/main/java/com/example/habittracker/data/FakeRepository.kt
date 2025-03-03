package com.example.habittracker.data

import androidx.compose.ui.graphics.Color
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitPeriodicity
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

object FakeRepository {

    private val emptyHabits = testHabits //emptyList<Habit>()

    private val _habits = MutableStateFlow<List<Habit>>(emptyHabits)
    val habits: StateFlow<List<Habit>> = _habits

    fun addHabit(habit: Habit) {
        _habits.value += habit
    }

    fun getSingleHabit(habitId: String): Flow<Habit> =
        flow {
            val habit = checkNotNull(habits.value.find { it.id == habitId })
            emit(habit)
        }

    fun updateItem(habit: Habit) {
        val updatedHabits = _habits.value.toMutableList()
        val index = updatedHabits.indexOfFirst { it.id == habit.id }
        if (index != -1) {
            updatedHabits[index] = habit
            _habits.value = updatedHabits
        }
    }

    fun clear() {
        _habits.value = emptyHabits
    }

    private fun updateHabitQuantity(habitId: String, delta: Int) {
        val updatedHabits = _habits.value.toMutableList()
        val index = updatedHabits.indexOfFirst { it.id == habitId }
        if (index != -1) {
            val currentRepeated = updatedHabits[index].periodicity.currentRepeated
            val newRepeated = currentRepeated + delta

            if ((delta > 0 && newRepeated <= updatedHabits[index].periodicity.repeatedTimes) ||
                (delta < 0 && newRepeated >= 0)) {
                val updatedHabit = updatedHabits[index].copy(
                    periodicity = HabitPeriodicity(
                        frequency = updatedHabits[index].periodicity.frequency,
                        repeatedTimes = updatedHabits[index].periodicity.repeatedTimes,
                        currentRepeated = newRepeated
                    )
                )
                updatedHabits[index] = updatedHabit
                _habits.value = updatedHabits
            }
        }
    }

    fun increaseQuantity(habitId: String) {
        updateHabitQuantity(habitId, 1)
    }

    fun decreaseQuantity(habitId: String) {
        updateHabitQuantity(habitId, -1)
    }

}


private val testHabits = mutableListOf(
    Habit(
        name = "Утренняя зарядка",
        description = "Зарядка для улучшения настроения",
        priority = HabitPriority.HIGH,
        type = HabitType.SPORT,
        periodicity = HabitPeriodicity("Ежедневно"),
        color = Color.Red
    ),
    Habit(
        name = "Чтение перед сном",
        description = "Чтение для расслабления",
        priority = HabitPriority.MEDIUM,
        type = HabitType.RELAXATION,
        periodicity = HabitPeriodicity("Раз в неделю"),
        color = Color.Blue
    ),
    Habit(
        name = "Просмотр лекций",
        description = "Обучение на платформе Coursera",
        priority = HabitPriority.LOW,
        type = HabitType.STUDY,
        periodicity = HabitPeriodicity("Раз в две недели"),
        color = Color.Green
    ),
    Habit(
        name = "Планирование дня",
        description = "Планирование задач на день",
        priority = HabitPriority.MEDIUM,
        type = HabitType.PRODUCTIVITY,
        periodicity = HabitPeriodicity("Ежедневно"),
        color = Color.Yellow
    )
)