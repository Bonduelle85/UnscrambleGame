package com.example.unscramblegame.load.data.cloud

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.UnknownHostException

interface WordService {

    // https://random-word-api.herokuapp.com/word?number=10

    @GET("word")
    fun data(
        @Query("number") number: Int,
    ): Call<List<String>>
}

class FakeService : WordService {

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

    private var showSuccess = false

    override fun data(number: Int): Call<List<String>> {
        Thread.sleep(1000)
        if (showSuccess)
            return object : Call<List<String>> {
                override fun clone(): Call<List<String>> {
                    return this
                }

                override fun execute(): Response<List<String>> {
                    return Response.success(response)
                }

                override fun isExecuted(): Boolean {
                    return false
                }

                override fun cancel() {
                }

                override fun isCanceled(): Boolean {
                    return false
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }

                override fun enqueue(callback: Callback<List<String>>) {
                    TODO("Not yet implemented")
                }

            }
        else {
            showSuccess = true
            throw UnknownHostException()
        }
    }

}