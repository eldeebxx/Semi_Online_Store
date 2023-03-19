package com.example.onlinestore.feature_online_store.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.onlinestore.feature_online_store.data.util.JsonParser
import com.example.onlinestore.feature_online_store.domain.model.Rating
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converter(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromRatingJson(json: String): Rating {
        return jsonParser.fromJson<Rating>(
            json = json,
            object : TypeToken<Rating>() {}.type
        ) ?: Rating(count = 0, rate = 0.0)
    }

    @TypeConverter
    fun toRatingJson(rating: Rating) : String {
        return jsonParser.toJson(
            rating,
            object :TypeToken<Rating>() {}.type
        ) ?: "{}"
    }
}