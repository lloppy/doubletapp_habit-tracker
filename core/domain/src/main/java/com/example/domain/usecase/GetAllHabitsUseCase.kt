package com.example.domain.usecase

import com.example.domain.repository.LocalDataSource
import com.example.model.Habit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHabitsUseCase @Inject constructor(
    private val localDataSource: LocalDataSource
) {
    fun invoke(): Flow<List<Habit>> = localDataSource.getAllHabits()
}
