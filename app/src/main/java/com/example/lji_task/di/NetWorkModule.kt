package com.example.lji_task.di

import com.example.lji_task.constants.AppConstants.UserEndUrl.BASE_URL
import com.example.lji_task.network.ApiService
import com.example.lji_task.network.HeaderHttpInterceptor
import com.example.lji_task.sessionManagers.UserSessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetWorkModule {
    @Singleton
    @Provides
    fun getRetrofit(userSessionManager: UserSessionManager) =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(
                            if (true) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                        )
                    )
                    .addInterceptor(HeaderHttpInterceptor(userSessionManager))
                    .build()
            ).build()


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return interceptor
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

}

