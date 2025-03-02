package com.example.habittracker.data

import androidx.compose.ui.graphics.Color
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitPeriodicity
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType

class FakeRepository() {


    private val habits = listOf(
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


    fun getHabits(): List<Habit> = habits

    fun getSingleHabit(habitName: String) = habits
        .find { it.name == habitName }

}
