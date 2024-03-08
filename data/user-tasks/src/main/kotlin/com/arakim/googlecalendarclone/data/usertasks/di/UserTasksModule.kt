package com.arakim.googlecalendarclone.data.usertasks.di

import com.arakim.googlecalendarclone.data.retrofit.googletasks.GoogleTasks
import com.arakim.googlecalendarclone.data.usertasks.TasksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object UserTasksModule {

    @Singleton
    @Provides
    fun providesTasksApi(@GoogleTasks retrofit: Retrofit): TasksApi {
        return retrofit.create(TasksApi::class.java)
    }
}