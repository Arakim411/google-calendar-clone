package com.arakim.googlecalendarclone.data.calendarsetup

import com.arakim.googlecalendarclone.data.calendarsetup.model.toDomain
import com.arakim.googlecalendarclone.data.calendarsetup.model.toDto
import com.arakim.googlecalendarclone.domain.calendarsetup.CalendarSetUpRepository
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.getOrNull
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class CalendarSetUpRepositoryImpl @Inject constructor(
    private val setUpLocalDataSource: CalendarSetUpLocalDataSource,
) : CalendarSetUpRepository {

    private val setUpFlow = MutableStateFlow(getSetUp().getOrNull() ?: DefaultCalendarSetUp)

    override fun getSetUp(): TypedResult<CalendarSetUp, CommonError> {
        val result = setUpLocalDataSource.getSetUp()?.toDomain() ?: DefaultCalendarSetUp
        return TypedResult.success(result)
    }

    override fun saveSetUp(calendarSetUp: CalendarSetUp) {
        setUpFlow.value = calendarSetUp
        setUpLocalDataSource.saveSetUp(calendarSetUp.toDto())
    }

    override fun setUpFlow(): Flow<CalendarSetUp> = setUpFlow
}