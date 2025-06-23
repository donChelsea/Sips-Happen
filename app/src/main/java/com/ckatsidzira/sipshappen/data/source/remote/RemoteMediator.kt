@file:OptIn(ExperimentalPagingApi::class)

package com.ckatsidzira.sipshappen.data.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ckatsidzira.sipshappen.data.source.local.BeerDatabase
import com.ckatsidzira.sipshappen.data.source.local.model.BeerEntity
import com.ckatsidzira.sipshappen.data.source.mapper.toEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteMediator @Inject constructor(
    private val db: BeerDatabase,
    private val api: BeerApi,
): RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null)
                        1
                    else
                        (lastItem.id / state.config.pageSize) + 1
                }
            }

            delay(2000L)
            val beers = api.getBeers(
                page = loadKey,
                perPage = state.config.pageSize
            )

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.cacheDao.clearAll()
                }
                val entities = beers.map { it.toEntity() }
                db.cacheDao.upsertAll(entities)
            }

            MediatorResult.Success(
                endOfPaginationReached = beers.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}