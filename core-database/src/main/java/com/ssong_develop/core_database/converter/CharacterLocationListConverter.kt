package com.ssong_develop.core_database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssong_develop.core_model.Characters

class CharacterLocationListConverter {

    @TypeConverter
    fun fromString(value: String): List<Characters.Location>? {
        val listType = object : TypeToken<Characters.Location>() {}.type
        return Gson().fromJson<List<Characters.Location>>(value, listType)
    }

    @TypeConverter
    fun fromList(location: List<Characters.Location>?): String {
        val gson = Gson()
        return gson.toJson(location)
    }
}