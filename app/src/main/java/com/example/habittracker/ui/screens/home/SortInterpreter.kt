package com.example.habittracker.ui.screens.home

import androidx.annotation.StringRes
import com.example.habittracker.R
import com.example.habittracker.ui.screens.home.SortOption.DATE_NEWEST
import com.example.habittracker.ui.screens.home.SortOption.DATE_OLDEST
import com.example.habittracker.ui.screens.home.SortOption.NAME_ASC
import com.example.habittracker.ui.screens.home.SortOption.NAME_DESC
import com.example.habittracker.ui.screens.home.SortOption.PRIORITY_HIGH
import com.example.habittracker.ui.screens.home.SortOption.PRIORITY_LOW
import com.example.model.Habit
import com.example.model.HabitCategory
import com.example.model.HabitPriority

interface FilterExpression {
    fun interpret(habits: List<Habit>): List<Habit>
}

enum class SortOption(@StringRes val displayName: Int) {
    DATE_NEWEST(R.string.date_newest),
    DATE_OLDEST(R.string.date_oldest),
    NAME_ASC(R.string.name_asc),
    NAME_DESC(R.string.name_desc),
    PRIORITY_HIGH(R.string.priority_high),
    PRIORITY_LOW(R.string.priority_low)
}

class SearchInterpreter(private val query: String) : FilterExpression {
    override fun interpret(habits: List<Habit>): List<Habit> {
        return if (query.isBlank()) habits
        else habits.filter { it.name.contains(query, ignoreCase = true) }
    }
}

class CategoryInterpreter(private val category: HabitCategory?) : FilterExpression {
    override fun interpret(habits: List<Habit>): List<Habit> {
        return category?.let { c -> habits.filter { it.category == c } } ?: habits
    }
}

class PriorityInterpreter(private val priority: HabitPriority?) : FilterExpression {
    override fun interpret(habits: List<Habit>): List<Habit> {
        return priority?.let { p -> habits.filter { it.priority == p } } ?: habits
    }
}

class SortInterpreter(private val sortOption: SortOption) : FilterExpression {
    override fun interpret(habits: List<Habit>): List<Habit> =
        with(habits) {
            when (sortOption) {
                DATE_NEWEST -> sortedByDescending { it.id }
                DATE_OLDEST -> sortedBy { it.id }
                NAME_ASC -> sortedBy { it.name }
                NAME_DESC -> sortedByDescending { it.name }
                PRIORITY_HIGH -> sortedByDescending { it.priority.ordinal }
                PRIORITY_LOW -> sortedBy { it.priority.ordinal }
            }
        }
}

class MultiplicationExpression(
    private val interpreters: List<FilterExpression>
) : FilterExpression {
    override fun interpret(habits: List<Habit>): List<Habit> =
        interpreters.fold(habits) { accumulator, interpreter ->
            interpreter.interpret(accumulator)
        }
}

class AdditionExpression(private val expressions: List<FilterExpression>) : FilterExpression {
    override fun interpret(habits: List<Habit>): List<Habit> =
        expressions
            .flatMap { it.interpret(habits) }
            .distinct()
}