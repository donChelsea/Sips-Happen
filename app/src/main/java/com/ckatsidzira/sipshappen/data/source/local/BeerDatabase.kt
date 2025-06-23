package com.ckatsidzira.sipshappen.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ckatsidzira.sipshappen.data.source.local.converter.HopListConverter
import com.ckatsidzira.sipshappen.data.source.local.converter.IngredientsConverter
import com.ckatsidzira.sipshappen.data.source.local.converter.MaltListConverter
import com.ckatsidzira.sipshappen.data.source.local.dao.CacheDao
import com.ckatsidzira.sipshappen.data.source.local.model.BeerEntity

@Database(
    entities = [BeerEntity::class],
    version = 2
)
@TypeConverters(IngredientsConverter::class, HopListConverter::class, MaltListConverter::class)
abstract class BeerDatabase: RoomDatabase() {
    abstract val cacheDao: CacheDao
}