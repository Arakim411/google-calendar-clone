package com.arakim.googlecalendarclone.data.calendarsetup.di

import com.arakim.googlecalendarclone.data.calendarsetup.CalendarSetUpRepositoryImpl
import com.arakim.googlecalendarclone.domain.calendarsetup.CalendarSetUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CalendarSetUpModule {

    @Binds
    fun bindsCalendarSetUpRepository(impl: CalendarSetUpRepositoryImpl): CalendarSetUpRepository
}