package com.example.habittracker.model

import com.example.habittracker.model.SortOption.DATE_NEWEST
import com.example.habittracker.model.SortOption.DATE_OLDEST
import com.example.habittracker.model.SortOption.NAME_ASC
import com.example.habittracker.model.SortOption.NAME_DESC
import com.example.habittracker.model.SortOption.PRIORITY_HIGH
import com.example.habittracker.model.SortOption.PRIORITY_LOW

interface FilterExpression {
    fun interpret(habits: List<Habit>): List<Habit>
}

enum class SortOption(val displayName: String) {
    DATE_NEWEST("Сначала новые"),
    DATE_OLDEST("Сначала старые"),
    NAME_ASC("По имени (А-Я)"),
    NAME_DESC("По имени (Я-А)"),
    PRIORITY_HIGH("По приоритету (сначала высокий)"),
    PRIORITY_LOW("По приоритету (сначала низкий)")
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
    override fun interpret(habits: List<Habit>): List<Habit> = with(habits) {
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