package com.example.unscramblegame.load.data

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException

interface LoadRepository {

    fun load(): LoadResult

    class Base() : LoadRepository {
        override fun load(): LoadResult {
            val urlGetRequest =
                URL("https://opentdb.com/api.php?amount=10&difficulty=easy&type=multiple")
            val apiConnexion = urlGetRequest.openConnection() as HttpURLConnection
            apiConnexion.requestMethod = "GET"

            try {
                // Response code
                val responseCode = apiConnexion.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val `in` = BufferedReader(InputStreamReader(apiConnexion.inputStream))
                    val response = StringBuffer()
                    var inputLine: String?
                    while (`in`.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    `in`.close()

                    // Return response
                    val result = response.toString()
                    //todo save data to cache data source
                    return LoadResult.Success
                } else {
                    return LoadResult.Error(responseCode.toString())
                }
            } catch (e: Exception) {
                return if (e is UnknownHostException || e is java.net.ConnectException)
                    (LoadResult.Error("No internet connection"))
                else
                    (LoadResult.Error(e.message ?: "error"))
            } finally {
                apiConnexion.disconnect()
            }

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