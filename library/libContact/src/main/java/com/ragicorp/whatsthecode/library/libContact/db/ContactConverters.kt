package com.ragicorp.whatsthecode.library.libContact.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ContactConverters {
    private val gson = Gson()
    private val codesType = object : TypeToken<Codes>() {}.type

    @TypeConverter
    fun fromString(value: String): Codes? {
        return gson.fromJson(value, codesType)
    }

    @TypeConverter
    fun codesToString(codes: Codes): String {
        return gson.toJson(codes, codesType)
    }
}
