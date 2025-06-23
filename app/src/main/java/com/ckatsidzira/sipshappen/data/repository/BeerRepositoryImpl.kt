package com.ckatsidzira.sipshappen.data.repository

import com.ckatsidzira.sipshappen.data.source.remote.BeerApi
import com.ckatsidzira.sipshappen.data.source.remote.mapper.toDomain
import com.ckatsidzira.sipshappen.domain.Resource
import com.ckatsidzira.sipshappen.domain.safeCall
import com.ckatsidzira.sipshappen.domain.model.Beer
import com.ckatsidzira.sipshappen.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeerRepositoryImpl @Inject constructor(
    private val api: BeerApi
) : BeerRepository {

    override suspend fun getBeersFlow(): Flow<Resource<List<Beer>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getBeers(1)
            emit(Resource.Success(result.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun getBeerById(id: Int): Resource<Beer> = safeCall {
        api.getBeerById(id).toDomain()
    }
}