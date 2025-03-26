package com.example.habittracker.model

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
    override fun interpret(habits: List<Habit>): List<Habit> {
        return when (sortOption) {
            SortOption.DATE_NEWEST -> habits.sortedByDescending { it.id }
            SortOption.DATE_OLDEST -> habits.sortedBy { it.id }
            SortOption.NAME_ASC -> habits.sortedBy { it.name }
            SortOption.NAME_DESC -> habits.sortedByDescending { it.name }
            SortOption.PRIORITY_HIGH -> habits.sortedByDescending { it.priority.ordinal }
            SortOption.PRIORITY_LOW -> habits.sortedBy { it.priority.ordinal }
        }
    }
}

class MultiplicationExpression(
    private val interpreters: List<FilterExpression>
) : FilterExpression {
    override fun interpret(habits: List<Habit>): List<Habit> {
        return interpreters.fold(habits) { accumulator, interpreter ->
            interpreter.interpret(accumulator)
        }
    }
}

class AdditionExpression(private val expressions: List<FilterExpression>) : FilterExpression {
    override fun interpret(habits: List<Habit>): List<Habit> {
        return expressions
            .flatMap { it.interpret(habits) }
            .distinct()
    }
}