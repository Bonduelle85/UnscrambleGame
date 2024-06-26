package com.example.unscramblegame.load.data

import com.example.unscramblegame.core.data.StringCache
import com.example.unscramblegame.load.presentation.LoadScreen
import java.net.UnknownHostException

interface LoadRepository {

    suspend fun load(): LoadResult
    fun saveLastScreenIsLoad()

    class Base(
        private val lastScreen: StringCache,
        private val cloudDataSource: CloudDataSource,
        private val cacheDataSource: CacheDataSource,
    ) : LoadRepository {
        override suspend fun load(): LoadResult {

            return try {
                val data = cloudDataSource.data()
                cacheDataSource.save(data)
                LoadResult.Success
            } catch (e: Exception) {
                if (e is UnknownHostException || e is java.net.ConnectException) {
                    LoadResult.Error(message = "No internet connection")
                } else {
                    LoadResult.Error(message = e.message ?: "Load Repository Error")
                }
            }
        }

        override fun saveLastScreenIsLoad() {
            lastScreen.save(LoadScreen::class.java.canonicalName!!)
        }
    }
}


interface LoadResult {

    fun isSuccessful(): Boolean

    fun message(): String

    object Success : LoadResult {

        override fun isSuccessful(): Boolean {
            return true
        }

        override fun message(): String {
            throw IllegalStateException()
        }
    }

    data class Error(private val message: String) : LoadResult {

        override fun isSuccessful(): Boolean {
            return false
        }

        override fun message(): String {
            return message
        }
    }
}