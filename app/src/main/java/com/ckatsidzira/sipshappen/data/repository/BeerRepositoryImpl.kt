package com.ckatsidzira.sipshappen.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.ckatsidzira.sipshappen.data.source.local.BeerDatabase
import com.ckatsidzira.sipshappen.data.source.local.mapper.toDomain
import com.ckatsidzira.sipshappen.data.source.local.model.BeerEntity
import com.ckatsidzira.sipshappen.data.source.remote.BeerApi
import com.ckatsidzira.sipshappen.data.source.remote.mapper.toDomain
import com.ckatsidzira.sipshappen.data.source.remote.mapper.toEntity
import com.ckatsidzira.sipshappen.domain.Resource
import com.ckatsidzira.sipshappen.domain.model.Beer
import com.ckatsidzira.sipshappen.domain.repository.BeerRepository
import com.ckatsidzira.sipshappen.domain.safeCall
import com.ckatsidzira.sipshappen.domain.safeFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeerRepositoryImpl @Inject constructor(
    private val api: BeerApi,
    private val pager: Pager<Int, BeerEntity>,
    db: BeerDatabase,
) : BeerRepository {

    private val cacheDao = db.cacheDao

    override fun getBeersFlow(): Flow<Resource<List<Beer>>> = safeFlow {
        val cachedBeers: List<Beer> = cacheDao.getAll()
            .map { list -> list.map { it.toDomain() } }
            .firstOrNull() ?: emptyList()

        cachedBeers.ifEmpty {
            val response = api.getBeers(1)
            val entities = response.map { it.toEntity() }

            cacheDao.clearAll()
            cacheDao.upsertAll(entities)

            entities.map { it.toDomain() }
        }
    }

    override suspend fun getBeerById(id: Int): Resource<Beer> = safeCall {
        api.getBeerById(id).toDomain()
    }

    override suspend fun getRandomBeer(): Beer = api.getRandomBeer().toDomain()

    override fun getBeersPagingFlow(): Flow<PagingData<Beer>> =
        pager.flow
            .transform { pagingData ->
                val transformedData = pagingData.map { it.toDomain() }
                emit(transformedData)
            }
}