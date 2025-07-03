package com.example.habittracker.ui.shared.filter

import com.example.habittracker.ui.screens.home.CategoryInterpreter
import com.example.habittracker.ui.screens.home.FilterExpression
import com.example.model.HabitCategory
import com.example.habittracker.ui.screens.home.PriorityInterpreter
import com.example.habittracker.ui.screens.home.SearchInterpreter
import com.example.habittracker.ui.screens.home.SortInterpreter
import com.example.habittracker.ui.screens.home.SortOption
import com.example.model.HabitPriority

data class FilterState(
    val searchQuery: String = "",
    val sortOption: SortOption = SortOption.DATE_NEWEST,
    val selectedCategory: HabitCategory? = null,
    val selectedPriority: HabitPriority? = null
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
