package cn.spacexc.wearbili.remake.app.cache.domain.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Created by XC-Qan on 2023/9/10.
 * I'm very cute so please be nice to my code!
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 */

class MapTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): Map<String, String>? {
        if (value == null) {
            return null
        }
        val type = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toString(map: Map<String, String>?): String? {
        if (map == null) {
            return null
        }
        return gson.toJson(map)
    }
}

class MapNullableTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): Map<String, String?>? {
        if (value == null) {
            return null
        }
        val type = object : TypeToken<Map<String, String?>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toString(map: Map<String, String?>?): String? {
        if (map == null) {
            return null
        }
        return gson.toJson(map)
    }
}