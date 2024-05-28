package com.example.unscramblegame.load.data

import retrofit2.Call
import java.net.UnknownHostException

interface CloudDataSource {

    suspend fun data(): List<String>

    class Base(private val newService: WordService) : CloudDataSource {
        override suspend fun data(): List<String> {
            try {
                val data: Call<List<String>> = newService.data()
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