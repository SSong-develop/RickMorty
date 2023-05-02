package com.ssong_develop.core_database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssong_develop.core_database.entity.LocalEntityOrigin
import com.ssong_develop.core_model.Characters

class OriginListConverter {

    @TypeConverter
    fun fromJson(value: String): List<LocalEntityOrigin>? {
        val listType = object : TypeToken<Characters.Origin>() {}.type
        return Gson().fromJson<List<LocalEntityOrigin>>(value, listType)
    }

    @TypeConverter
    fun fromList(origin: List<LocalEntityOrigin>?): String {
        val gson = Gson()
        return gson.toJson(origin)
    }
}