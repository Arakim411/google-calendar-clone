package com.arakim.googlecalendarclone.data.calendarsetup

import com.arakim.googlecalendarclone.data.calendarsetup.model.toDomain
import com.arakim.googlecalendarclone.data.calendarsetup.model.toDto
import com.arakim.googlecalendarclone.domain.calendarsetup.CalendarSetUpRepository
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Singleton
internal class CalendarSetUpRepositoryImpl @Inject constructor(
    private val setUpLocalDataSource: CalendarSetUpLocalDataSource,
) : CalendarSetUpRepository {

    private val setUpFlow = MutableStateFlow(getSetUp())

    override fun getSetUp(): TypedResult<CalendarSetUp, CommonError> {
        val result = setUpLocalDataSource.getSetUp()?.toDomain() ?: DefaultCalendarSetUp
        return TypedResult.success(result)
    }

    override fun saveSetUp(calendarSetUp: CalendarSetUp) {
        setUpFlow.value = TypedResult.success(calendarSetUp)
        setUpLocalDataSource.saveSetUp(calendarSetUp.toDto())
    }

    override fun getSetUpFlow(): Flow<TypedResult<CalendarSetUp, CommonError>> = setUpFlow
}