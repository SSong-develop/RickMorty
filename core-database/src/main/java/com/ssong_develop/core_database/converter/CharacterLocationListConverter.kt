package com.ssong_develop.core_database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssong_develop.core_database.model.LocalEntityLocation

class CharacterLocationListConverter {

    @TypeConverter
    fun fromString(value: String): List<LocalEntityLocation>? {
        val listType = object : TypeToken<LocalEntityLocation>() {}.type
        return Gson().fromJson<List<LocalEntityLocation>>(value, listType)
    }

    @TypeConverter
    fun fromList(location: List<LocalEntityLocation>?): String {
        val gson = Gson()
        return gson.toJson(location)
    }
}