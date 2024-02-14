package com.arakim.googlecalendarclone.data.signin.di

import com.arakim.googlecalendarclone.data.signin.SignInServiceImpl
import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignInServiceModule {
    @Binds
    fun bindsSignInService(signInServiceImpl: SignInServiceImpl): SignInService
}