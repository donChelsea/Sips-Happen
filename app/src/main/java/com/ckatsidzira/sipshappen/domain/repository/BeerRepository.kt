package com.ckatsidzira.sipshappen.domain.repository

import com.ckatsidzira.sipshappen.domain.Resource
import com.ckatsidzira.sipshappen.domain.model.Beer
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    fun getBeersFlow(): Flow<Resource<List<Beer>>>
    suspend fun getBeerById(id: Int): Resource<Beer>
    suspend fun getRandomBeer(): Beer
}