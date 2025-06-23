package com.ckatsidzira.sipshappen.data.repository

import com.ckatsidzira.sipshappen.data.source.local.BeerDatabase
import com.ckatsidzira.sipshappen.data.source.local.mapper.toDomain
import com.ckatsidzira.sipshappen.data.source.remote.mapper.toEntity
import com.ckatsidzira.sipshappen.data.source.remote.BeerApi
import com.ckatsidzira.sipshappen.data.source.remote.mapper.toDomain
import com.ckatsidzira.sipshappen.domain.Resource
import com.ckatsidzira.sipshappen.domain.model.Beer
import com.ckatsidzira.sipshappen.domain.repository.BeerRepository
import com.ckatsidzira.sipshappen.domain.safeCall
import com.ckatsidzira.sipshappen.domain.safeFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeerRepositoryImpl @Inject constructor(
    private val api: BeerApi,
    private val db: BeerDatabase,
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

            println(entities.toString())

            entities.map { it.toDomain() }
        }
    }

    override suspend fun getBeerById(id: Int): Resource<Beer> = safeCall {
        api.getBeerById(id).toDomain()
    }
}