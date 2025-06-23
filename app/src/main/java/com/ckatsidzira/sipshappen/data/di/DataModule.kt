package com.ckatsidzira.sipshappen.data.di

import android.content.Context
import com.ckatsidzira.sipshappen.BuildConfig
import com.ckatsidzira.sipshappen.data.network.ConnectivityModule
import com.ckatsidzira.sipshappen.data.repository.BeerRepositoryImpl
import com.ckatsidzira.sipshappen.data.source.remote.BeerApi
import com.ckatsidzira.sipshappen.domain.repository.BeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun provideBeerApi(
        okHttpClient: OkHttpClient
    ): BeerApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeerApi::class.java)

    @Provides
    @Singleton
    fun provideBeerRepository(api: BeerApi): BeerRepository =
        BeerRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideConnectivityModule(@ApplicationContext context: Context): ConnectivityModule =
        ConnectivityModule(context)
}