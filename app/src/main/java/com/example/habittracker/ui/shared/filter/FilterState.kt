package com.example.habittracker.ui.shared.filter

import com.example.habittracker.model.CategoryInterpreter
import com.example.habittracker.model.FilterExpression
import com.example.model.domain.HabitCategory
import com.example.model.domain.HabitPriority
import com.example.habittracker.model.PriorityInterpreter
import com.example.habittracker.model.SearchInterpreter
import com.example.habittracker.model.SortInterpreter
import com.example.habittracker.model.SortOption

data class FilterState(
    val searchQuery: String = "",
    val sortOption: SortOption = SortOption.DATE_NEWEST,
    val selectedCategory: com.example.model.domain.HabitCategory? = null,
    val selectedPriority: com.example.model.domain.HabitPriority? = null
)

fun FilterState.toExpressions(): List<FilterExpression> {
    val expressions = mutableListOf<FilterExpression>()

    if (searchQuery.isNotBlank()) {
        expressions.add(SearchInterpreter(searchQuery))
    }
    selectedCategory?.let {
        expressions.add(CategoryInterpreter(it))
    }
    selectedPriority?.let {
        expressions.add(PriorityInterpreter(it))
    }

    expressions.add(SortInterpreter(sortOption))

    return expressions
}
