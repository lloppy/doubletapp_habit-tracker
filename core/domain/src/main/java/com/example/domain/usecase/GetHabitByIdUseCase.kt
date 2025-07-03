package com.example.domain.usecase

import com.example.domain.repository.LocalDataSource
import com.example.model.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class GetHabitByIdUseCase @Inject constructor(
    private val localDataSource: LocalDataSource
) {
    operator fun invoke(id: Int): Flow<Habit> =
        localDataSource.getHabitById(id = id).filterNotNull()
}
