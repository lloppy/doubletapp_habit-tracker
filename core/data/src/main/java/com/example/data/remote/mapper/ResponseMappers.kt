package com.example.data.remote.mapper

import com.example.data.remote.model.HabitDoneResponse as RemoteHabitDoneResponse
import com.example.data.remote.model.HabitUid as RemoteHabitUid
import com.example.domain.model.HabitDoneResponse as DomainHabitDoneResponse
import com.example.domain.model.HabitUid as DomainHabitUid

fun RemoteHabitDoneResponse.toDomain(): DomainHabitDoneResponse = DomainHabitDoneResponse(
    date = this.date,
    habitUid = this.habitUid
)

fun DomainHabitDoneResponse.toData(): RemoteHabitDoneResponse = RemoteHabitDoneResponse(
    date = this.date,
    habitUid = this.habitUid
)

fun RemoteHabitUid.toDomain(): DomainHabitUid = DomainHabitUid(
    uid = this.uid
)

fun DomainHabitUid.toData(): RemoteHabitUid = RemoteHabitUid(
    uid = this.uid
)