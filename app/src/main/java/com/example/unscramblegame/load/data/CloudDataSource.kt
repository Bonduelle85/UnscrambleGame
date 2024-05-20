package com.example.unscramblegame.load.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

interface CloudDataSource {

    fun data(): List<String>

    class Base(
        private val service: WordsService,
        private val gson: Gson,
    ) : CloudDataSource {

        override fun data(): List<String> {
            val response: String = service.data()
            return gson.fromJson(response, object : TypeToken<List<String>>() {})
        }
    }
}


interface WordsService {

    fun data(): String

    class Base : WordsService {

        override fun data(): String {

            val urlGetRequest =
                URL("https://random-word-api.herokuapp.com/word?number=10")
            val apiConnexion = urlGetRequest.openConnection() as HttpURLConnection
            apiConnexion.requestMethod = "GET"

            try {
                val responseCode = apiConnexion.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val `in` = BufferedReader(InputStreamReader(apiConnexion.inputStream))
                    val response = StringBuffer()
                    var inputLine: String?
                    while (`in`.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    `in`.close()

                    return response.toString()
                } else {
                    throw IllegalStateException("unknown error")
                }
            } finally {
                apiConnexion.disconnect()
            }
        }
    }

    class Mock(
        private val gson: Gson
    ) : WordsService {

        private var showSuccess = false
        private val response = listOf(
            "animal",
            "auto",
            "anecdote",
            "alphabet",
            "all",
            "awesome",
            "arise",
            "balloon",
            "basket",
            "bench",
        )

        override fun data(): String {
            Thread.sleep(1000)
            if (showSuccess)
                return gson.toJson(response)
            else {
                showSuccess = true
                throw IllegalStateException("No internet connection")
            }
        }
    }
}