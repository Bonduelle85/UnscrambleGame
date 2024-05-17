package com.example.unscramblegame.main.presentation

import android.os.Handler
import android.os.Looper
import com.example.unscramblegame.main.data.MainRepository

class MainViewModel(
    private val mainRepository: MainRepository
) : MyViewModel {
    fun init(firstRun: Boolean): Screen {
        return if (firstRun)
            mainRepository.lastSavedScreen()
        else
            Screen.Empty
    }
}

interface MyViewModel {

    abstract class Abstract(
        private val runAsync: RunAsync
    ) : MyViewModel, RunAsync {

        override fun <T : Any> runAsync(background: () -> T, ui: (T) -> Unit) {
            runAsync.runAsync(background, ui)
        }
    }
}

interface RunAsync {

    fun <T : Any> runAsync(background: () -> T, ui: (T) -> Unit)

    class Base : RunAsync {

        override fun <T : Any> runAsync(background: () -> T, ui: (T) -> Unit) {
            Thread {
                val result: T = background.invoke()
                Handler(Looper.getMainLooper()).post {
                    ui.invoke(result)
                }
            }.start()
        }
    }
}