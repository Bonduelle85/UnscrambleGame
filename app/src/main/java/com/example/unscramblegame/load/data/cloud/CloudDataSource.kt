package com.example.unscramblegame.load.data.cloud

import retrofit2.Call
import java.net.UnknownHostException

interface CloudDataSource {

    suspend fun data(): List<String>

    class Base(
        private val max: Int,
        private val newService: WordService,
    ) : CloudDataSource {
        override suspend fun data(): List<String> {
            try {
                val data: Call<List<String>> = newService.data(max)
                return data.execute().body()!!
            } catch (e: Exception) {
                if (e is UnknownHostException)
                    throw IllegalStateException("No internet connection")
                else
                    throw IllegalStateException("unknown error")
            }
        }
    }
}