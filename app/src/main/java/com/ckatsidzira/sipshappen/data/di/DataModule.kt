@file:OptIn(ExperimentalPagingApi::class)

package com.ckatsidzira.sipshappen.data.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.ckatsidzira.sipshappen.BuildConfig
import com.ckatsidzira.sipshappen.data.network.ConnectivityObserver
import com.ckatsidzira.sipshappen.data.repository.BeerRepositoryImpl
import com.ckatsidzira.sipshappen.data.source.local.BeerDatabase
import com.ckatsidzira.sipshappen.data.source.local.model.BeerEntity
import com.ckatsidzira.sipshappen.data.source.remote.BeerApi
import com.ckatsidzira.sipshappen.data.source.remote.RemoteMediator
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
    fun provideBeerDatabase(
        @ApplicationContext context: Context,
    ): BeerDatabase =
        Room.databaseBuilder(
            context,
            BeerDatabase::class.java,
            "beers.db"
        )
            .fallbackToDestructiveMigration()
            .build()

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
    fun provideBeerRepository(
        api: BeerApi,
        db: BeerDatabase,
        pager: Pager<Int, BeerEntity>,
    ): BeerRepository =
        BeerRepositoryImpl(api = api, db = db, pager = pager)

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver =
        ConnectivityObserver(context)

    @Provides
    @Singleton
    fun provideBeerPager(
        db: BeerDatabase,
        api: BeerApi,
    ): Pager<Int, BeerEntity> =
        Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = RemoteMediator(
                db = db,
                api = api
            ),
            pagingSourceFactory = {
                db.cacheDao.pagingSource()
            }
        )
}