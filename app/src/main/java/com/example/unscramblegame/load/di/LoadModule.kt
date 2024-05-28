package com.example.unscramblegame.load.di

import com.example.unscramblegame.core.data.StringCache
import com.example.unscramblegame.core.di.Core
import com.example.unscramblegame.core.di.Module
import com.example.unscramblegame.core.di.ProvideAbstract
import com.example.unscramblegame.core.di.ProvideViewModel
import com.example.unscramblegame.load.data.CacheDataSource
import com.example.unscramblegame.load.data.CloudDataSource
import com.example.unscramblegame.load.data.FakeService
import com.example.unscramblegame.load.data.ListWrapper
import com.example.unscramblegame.load.data.LoadRepository
import com.example.unscramblegame.load.data.WordService
import com.example.unscramblegame.load.presentation.LoadViewModel
import com.example.unscramblegame.load.presentation.UiObservable
import com.example.unscramblegame.main.presentation.RunAsync
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoadModule(
    private val core: Core
) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel = with(core) {
        return LoadViewModel(
            UiObservable.Base(),
            LoadRepository.Base(
                lastScreen,
                cloudDataSource = CloudDataSource.Base(
                    if (core.runUiTest)
                        FakeService()
                    else
                        Retrofit.Builder().baseUrl("https://random-word-api.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(
                                OkHttpClient.Builder()
                                    .retryOnConnectionFailure(true)
                                    .addInterceptor(HttpLoggingInterceptor().apply {
                                        setLevel(HttpLoggingInterceptor.Level.BODY)
                                    })
                                    .build()
                            )
                            .build()
                            .create(WordService::class.java)
                ),
                CacheDataSource.Base(
                    StringCache.Base(
                        "GAME_DATA",
                        sharedPreferences,
                        gson.toJson(ListWrapper(emptyList()))
                    ),
                    gson
                )
            ),
            RunAsync.Base()
        )
    }
}

class ProvideLoadViewModel(
    core: Core,
    provideOther: ProvideViewModel
) : ProvideAbstract(core, provideOther, LoadViewModel::class.java) {

    override fun module() = LoadModule(core)
}