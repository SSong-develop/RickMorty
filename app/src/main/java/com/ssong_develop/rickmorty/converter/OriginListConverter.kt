package com.ssong_develop.rickmorty.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssong_develop.rickmorty.entities.Characters

class OriginListConverter {

    @TypeConverter
    fun fromJson(value : String) : List<Characters.Origin>? {
        val listType = object : TypeToken<Characters.Origin>() {}.type
        return Gson().fromJson<List<Characters.Origin>>(value,listType)
    }

    @TypeConverter
    fun fromList(origin : List<Characters.Origin>?) : String {
        val gson = Gson()
        return gson.toJson(origin)
    }
}