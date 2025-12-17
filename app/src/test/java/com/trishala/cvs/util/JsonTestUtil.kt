package com.trishala.cvs.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader


object JsonTestUtil {

    val gson = Gson()

    /**
     * Loads JSON from test resources and deserializes to specified type.
     * Works for both objects and generic types (List, Map, etc).
     */
    inline fun <reified T> loadJson(fileName: String): T {
        val classLoader = JsonTestUtil::class.java.classLoader
        val inputStream = classLoader?.getResourceAsStream(fileName)
            ?: throw IllegalArgumentException("File not found: $fileName")

        val type = object : TypeToken<T>() {}.type

        return InputStreamReader(inputStream).use { reader ->
            gson.fromJson(reader, type)
        }
    }

    /**
     * Loads raw JSON string from test resources.
     */
    fun loadJsonString(fileName: String): String {
        val classLoader = JsonTestUtil::class.java.classLoader
        val inputStream = classLoader?.getResourceAsStream(fileName)
            ?: throw IllegalArgumentException("File not found: $fileName")

        return inputStream.bufferedReader().use { it.readText() }
    }
}
