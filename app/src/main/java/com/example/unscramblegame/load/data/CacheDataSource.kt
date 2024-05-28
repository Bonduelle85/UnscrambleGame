package com.example.unscramblegame.load.data

import com.example.unscramblegame.core.data.StringCache
import com.google.gson.Gson
import java.io.Serializable

interface CacheDataSource {

    suspend fun save(data: List<String>)

    fun read(): List<String>

    class Base(
        private val stringCache: StringCache,
        private val gson: Gson
    ) : CacheDataSource {

        override suspend fun save(data: List<String>) {
            val serialisedWrapper = gson.toJson(ListWrapper(data))
            stringCache.save(serialisedWrapper)
        }

        override fun read(): List<String> {
            val serialisedWrapper = stringCache.read()
            return gson.fromJson(serialisedWrapper, ListWrapper::class.java).list
        }
    }
}

data class ListWrapper(
    val list: List<String>
) : Serializable