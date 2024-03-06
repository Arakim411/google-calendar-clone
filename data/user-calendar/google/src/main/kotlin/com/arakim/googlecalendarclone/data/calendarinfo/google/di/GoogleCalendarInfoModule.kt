package com.arakim.googlecalendarclone.data.calendarinfo.google.di

import com.arakim.googlecaelndarclone.domain.user.SignInMethodId
import com.arakim.googlecalendarclone.data.calendarinfo.google.GoogleCalendarInfoRepositoryImpl
import com.arakim.googlecalendarclone.domain.calendar.usercalendar.UserCalendarRepository
import com.arakim.googlecalendarclone.domain.user.signin.model.SignedUserTypeKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
sealed interface GoogleCalendarInfoModule {

    @Binds
    @SignedUserTypeKey(SignInMethodId.Google)
    @IntoMap
    fun bindCalendarInfoRepository(impl: GoogleCalendarInfoRepositoryImpl): UserCalendarRepository
}