package com.ckatsidzira.sipshappen.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ckatsidzira.sipshappen.data.source.local.model.BeerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheDao {

    @Upsert
    suspend fun upsertAll(beers: List<BeerEntity>)

    @Query("SELECT * FROM cache")
    fun getAll(): Flow<List<BeerEntity>>

    @Query("DELETE FROM cache")
    suspend fun clearAll()

    @Query("SELECT * FROM cache")
    fun pagingSource(): PagingSource<Int, BeerEntity>
}