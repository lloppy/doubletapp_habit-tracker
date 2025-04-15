package com.example.habittracker.data.repository

import com.example.habittracker.data.api.HabitsApiService
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitDone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnlineHabitsRepository(
    private val retrofitService: HabitsApiService
) : HabitsRepository {

    override suspend fun insertHabit(habit: Habit) = retrofitService.updateHabit(habit = habit)

    override suspend fun updateHabit(habit: Habit) = retrofitService.updateHabit(habit = habit)

    override suspend fun deleteHabit(habit: Habit) =
        retrofitService.deleteHabit(habitUid = habit.uid)

    override suspend fun deleteByHabitId(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun increaseHabitQuantity(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun decreaseHabitQuantity(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllHabits(): Flow<List<Habit>> = flow {
        emit(retrofitService.getHabits())
    }

    override fun getHabitById(id: Int): Flow<Habit?> {
        TODO("Not yet implemented")
    }

    override suspend fun markHabitDone(habitUid: String, date: Int) {
        retrofitService.markDoneHabit(habitDone = HabitDone(date = date, habitUid = habitUid))
    }

}