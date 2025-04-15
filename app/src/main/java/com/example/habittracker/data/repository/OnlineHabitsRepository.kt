package com.example.habittracker.data.repository

import com.example.habittracker.data.api.HabitsApiService
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitDone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface NetworkHabitsRepository {
    suspend fun insertHabit(habit: Habit)
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
    suspend fun markHabitDone(habitUid: String, date: Int)

    fun getAllHabits(): Flow<List<Habit>>
}

class OnlineHabitsRepository(
    private val retrofitService: HabitsApiService,
) : NetworkHabitsRepository {

    override suspend fun insertHabit(habit: Habit) = retrofitService.updateHabit(habit = habit)

    override suspend fun updateHabit(habit: Habit) = retrofitService.updateHabit(habit = habit)

    override suspend fun deleteHabit(habit: Habit) =
        retrofitService.deleteHabit(habitUid = habit.uid)

    override fun getAllHabits(): Flow<List<Habit>> = flow {
        emit(retrofitService.getHabits())
    }

    override suspend fun markHabitDone(habitUid: String, date: Int) {
        retrofitService.markDoneHabit(habitDone = HabitDone(date = date, habitUid = habitUid))
    }

}