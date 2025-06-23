package com.ckatsidzira.sipshappen.data.source.local.converter

import androidx.room.TypeConverter
import com.ckatsidzira.sipshappen.data.source.local.model.HopEntity
import com.ckatsidzira.sipshappen.data.source.local.model.MaltEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredientsConverter {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}

class HopListConverter {
    @TypeConverter
    fun fromHopList(hops: List<HopEntity>?): String? {
        if (hops == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<HopEntity>>() {}.type
        return gson.toJson(hops, type)
    }

    @TypeConverter
    fun toHopList(hopsString: String?): List<HopEntity>? {
        if (hopsString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<HopEntity>>() {}.type
        return gson.fromJson(hopsString, type)
    }
}

class MaltListConverter {
    @TypeConverter
    fun fromMaltList(malts: List<MaltEntity>?): String? {
        if (malts == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MaltEntity>>() {}.type
        return gson.toJson(malts, type)
    }

    @TypeConverter
    fun toMaltList(maltsString: String?): List<MaltEntity>? {
        if (maltsString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<MaltEntity>>() {}.type
        return gson.fromJson(maltsString, type)
    }
}