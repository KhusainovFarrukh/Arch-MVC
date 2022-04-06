package kh.farrukh.arch_mvc.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *Created by farrukh_kh on 4/3/22 4:12 PM
 *kh.farrukh.arch_mvc.model
 **/
class TypeConverters {

    @TypeConverter
    fun stringToIntegerList(data: String?): MutableList<Int> {

        val gson = Gson()

        if (data == null || data.isEmpty() || data == "null") return mutableListOf()

        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun integerListToString(someObjects: List<Int>?): String {
        val gson = Gson()

        return gson.toJson(someObjects)
    }
}