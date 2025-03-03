package com.example.habittracker.data

import com.example.habittracker.model.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

object FakeRepository {

    private val emptyHabits = emptyList<Habit>()

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
}
