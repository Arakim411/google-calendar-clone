package com.arakim.googlecalendarclone.data.retrofit.googletasks

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object GoogleTasksRetrofitModule {

    @Provides
    @Singleton
    @GoogleTasks
    fun providesAuthenticatedOkHttp(
        authInterceptor: GoogleAuthInterceptor,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    @GoogleTasks
    fun providesMoshi(): Moshi = Moshi
        .Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    @GoogleTasks
    fun providesRetrofit(
        @GoogleTasks okHttpClient: OkHttpClient,
        @GoogleTasks moshi: Moshi,
    ): Retrofit = Retrofit
        .Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Const.GoogleTaskApiUrl)
        .build()
}