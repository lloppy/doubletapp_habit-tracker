package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class FakeRepository {

    private val emptyHabits = emptyList<com.example.model.domain.Habit>()

    private val _habits = MutableStateFlow<List<com.example.model.domain.Habit>>(emptyHabits)
    val habits: StateFlow<List<com.example.model.domain.Habit>> = _habits

    fun addHabit(habit: com.example.model.domain.Habit) {
        _habits.value += habit
    }

    fun getSingleHabit(habitId: Int): Flow<com.example.model.domain.Habit> =
        flow {
            val habit = checkNotNull(habits.value.find { it.id == habitId })
            emit(habit)
        }

    fun updateItem(habit: com.example.model.domain.Habit) {
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

    private fun updateHabitQuantity(habitId: Int, delta: Int) {
        val updatedHabits = _habits.value.toMutableList()
        val index = updatedHabits.indexOfFirst { it.id == habitId }

        if (index != -1) {
            val currentRepeated = updatedHabits[index].quantity
            val newRepeated = currentRepeated + delta

            if ((delta > 0 && newRepeated <= updatedHabits[index].repeatedTimes) ||
                (delta < 0 && newRepeated >= 0)
            ) {
                val updatedHabit = updatedHabits[index].copy(
                    frequency = updatedHabits[index].frequency,
                    repeatedTimes = updatedHabits[index].repeatedTimes,
                    quantity = newRepeated

                )
                updatedHabits[index] = updatedHabit
                _habits.value = updatedHabits
            }
        }
    }

    fun increaseQuantity(habitId: Int) {
        updateHabitQuantity(habitId, 1)
    }

    fun decreaseQuantity(habitId: Int) {
        updateHabitQuantity(habitId, -1)
    }
}
