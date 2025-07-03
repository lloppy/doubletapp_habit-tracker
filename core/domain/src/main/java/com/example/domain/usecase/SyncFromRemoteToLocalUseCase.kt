package com.example.domain.usecase

import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.map
import com.example.domain.util.onError
import javax.inject.Inject

class SyncFromRemoteToLocalUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(): EmptyResult<DataError> {
        remoteDataSource.getHabits()
            .onError { error ->
                return Result.Error(error)
            }
            .map { habits ->
                localDataSource.deleteAllHabits()
                habits.forEach { localDataSource.insertHabit(it) }
            }

        return Result.Success(Unit)
    }
}